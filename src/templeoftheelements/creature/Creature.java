
package templeoftheelements.creature;

import templeoftheelements.controller.AttackAction;
import templeoftheelements.controller.Action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.lwjgl.opengl.GL11;
import stat.NumericStat;
import stat.StatContainer;
import templeoftheelements.Actor;
import templeoftheelements.item.AttackDefinition;
import templeoftheelements.controller.Controller;
import templeoftheelements.Damager;
import templeoftheelements.GameObject;
import templeoftheelements.Steppable;
import templeoftheelements.item.Equipment;
import templeoftheelements.item.Item;
import templeoftheelements.TempleOfTheElements;
import templeoftheelements.collision.Attack;
import templeoftheelements.collision.Damageable;
import templeoftheelements.collision.Position;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.controller.DoNothingAction;
import templeoftheelements.controller.OngoingAction;
import templeoftheelements.item.Weapon;
import templeoftheelements.display.VectorCircle;
import templeoftheelements.display.Renderable;
import templeoftheelements.item.ItemDrop;
import templeoftheelements.player.*;



public class Creature implements Damageable, Actor, Renderable, Clickable, Damager, GameObject {
    private Renderable sprite;
    private Controller controller;
    private Fixture fixture;
    private float direction;
    private OngoingAction currentAction;
    private HashSet<Action> actions;
    private HashSet<Ability> abilities;
    private Position createPosition; //only used for a createBody method, not for 
    //determining ongoing position. This is for when you want to store a body in 
    //a room without creating it.
    public ArrayList<BodyPart> bodyParts;
    private HashMap<String, Float> resistances;
    public ArrayList<ItemDrop> itemDrops;
    private HashMap<String, StatusEffect> statusEffects;
    private ArrayList<CreatureListener> listeners;
    private ArrayList<Steppable> steppables;
    private String name; //For display and debug purposes.
    public StatContainer stats;

    public Creature() {
        this(new Position(), new CreatureDefinition(""));
    }
    
    public Creature(CreatureDefinition def) {
        this(new Position(), def);
    }
    
    public Creature(Position pos, CreatureDefinition def) {
        direction = 0;
        createPosition = pos;
        actions = new HashSet<>();
        bodyParts = new ArrayList<>();
        resistances = new HashMap<>();
        itemDrops = new ArrayList<>();
        statusEffects = new HashMap<>();
        listeners = new ArrayList<>();
        steppables = new ArrayList<>();
        abilities = new HashSet<>();
        this.stats = new StatContainer(def.stats);
        sprite = new VectorCircle(stats.getScore("Size"));
        currentAction = new DoNothingAction();
    }
    
    public void addAbility(Ability a) {
        abilities.add(a);
        a.init(this);
    }
    
    public void addAction(Action a) {
        actions.add(a);
    }
    
    public void addSteppable(Steppable a) {
        steppables.add(a);
    }
    
    public void addListener(CreatureListener a) {
        listeners.add(a);
    }
    
    public void removeAction(Action a) {
        actions.remove(a);
    }
    
    public HashSet<Action> getActions() {
        return actions;
    }
    
    public void refactor() {
        for (StatusEffect effect : statusEffects.values()) {
            defactorStatusEffect(effect);
        }
        
        stats.refactor();
        
        for (BodyPart part : bodyParts) {
            part.factor();
        }
        for (StatusEffect effect : statusEffects.values()) {
            factorStatusEffect(effect);
        }
    }
    
    public void refactorActions() {
        for (Action a : actions) controller.addAction(a);
        for (BodyPart b : bodyParts) {
            if (b instanceof Hand && ((Hand) b).equipment != null) {
                for (AttackDefinition a : ((Weapon) ((Hand) b).equipment).getAttacks()) 
                    controller.addAction(new AttackAction(a)); 
            }
       }
    }
    
    public void notifyCreatureEvent(CreatureEvent event) {
        for (CreatureListener listener : listeners) listener.handle(event);
    }
    
    public void addBodyPart(String type, float position) {
        bodyParts.add(new BodyPart(type, position));
    }
    
    public void addHand(float position) {
        bodyParts.add(new Hand(position));
    }
    
    public Renderable getSprite() {
        return sprite;
    }
    
    public void setSprite(Renderable r) {
        sprite = r;
    }
    
    public void addResistance(String type, float f) {
        resistances.put(type, f);
    }
    
    @Override
    public void createBody() {
        createBody(createPosition);
    }
    
    @Override
    public void createBody(float x, float y) {
        createBody(new Position(x, y));
    }
    
    @Override
    public void createBody(int x, int y) {
        createBody(new Position(x, y));
    }
    
    @Override
    public void createBody(Position position) {
        BodyDef bodydef = new BodyDef();
        bodydef.position.set(position);
        bodydef.type = BodyType.DYNAMIC;
        CircleShape bodyShape = new CircleShape();
        bodyShape.setRadius(stats.getScore("Size"));
        Body body = TempleOfTheElements.game.world.createBody(bodydef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.shape = bodyShape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        body.setUserData(this);
    }
    
    //this is for when you want to destroy a creature's body, becuase it's not 
    //in the same room as the player. This means that it will reappear in the same position.
    public void setCreatePosition() {
        createPosition = getPosition();
    }
    
    public void setCreatePosition(int x, int y) {
        setCreatePosition(new Position(x, y));
    }
    
    public void setCreatePosition(Position pos) {
        createPosition = pos;
    }
    
    /**
     * @return the position
     */
    @Override
    public Position getPosition() {
        return new Position( getBody().getPosition());
    }
    
    private void regenStamina(float dt) {
        float curStamina = stats.getScore("Max Stamina");
        if (stats.getScore("Stamina") < curStamina) {
            ((NumericStat) stats.getStat("Stamina")).modifyBase(stats.getScore("Stamina Regen") * dt);
            if (stats.getScore("Stamina") > stats.getScore("Max Stamina")) stats.getStat("Stamina").set(stats.getScore("Max Stamina"));
            notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.GAINED_STAMINA, stats.getScore("Stamina") - curStamina));
        }
    }
    
    private void accelerateBody(float dt) {
        Vec2 speed = getBody().getLinearVelocity().mul(1 - stats.getScore("Acceleration")/stats.getScore("Max Speed"));
        getBody().setLinearVelocity(speed);
        getBody().applyForceToCenter(controller.getAccel().mul(dt).mul(150).mul(stats.getScore("Acceleration")));
        notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.MOVED, getBody().getLinearVelocity().length()));
    }
    
    private void checkFatigue() {
        float staminaPercentage = stats.getScore("Stamina") / stats.getScore("Max Stamina");
        if (staminaPercentage < 0.9) {
            if (statusEffects.containsKey("Fatigue") && statusEffects.get("Fatigue").severity < (int) (10 -10 * staminaPercentage)) {
                StatusEffect effect = game.registry.statusEffects.get("Fatigue").clone();
                effect.severity = (int) (10 -10 * staminaPercentage);
                statusEffects.get("Fatigue").update(effect);
            } else {
                StatusEffect effect = game.registry.statusEffects.get("Fatigue").clone();
                addStatusEffect(effect);
            }
        }
    }

    @Override
    public void step(float dt) {
        for (Steppable steppable : steppables) steppable.step(dt);
        
        for (StatusEffect status : statusEffects.values()) {
            status.step(dt);
        }
        
        currentAction.step(dt);
        
        //if (controller == null) return;
        controller.step(dt);
        
        regenStamina(dt);
        
        accelerateBody(dt);
        
        checkFatigue();
    }

    /**
     * @return the controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(Controller controller) {
        this.controller = controller;
        controller.init();
    }

    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glTranslated(getPosition().x, getPosition().y, 0);
        GL11.glRotated(-getDirection(), 0, 0, 1);
        sprite.draw();
        GL11.glPopMatrix();
    }

    /**
     * @return the body
     */
    @Override
    public Body getBody() {
        return fixture.getBody();
    }
    
    /**
     *
     * @param damage
     * @param type
     * @return
     */
    @Override
    public float takeDamage(float damage, String type) {
        damage *= stats.getScore("Damage Resistance Multiplier");
        notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.TOOK_DAMAGE, damage, type));
        if (resistances.containsKey(type)) damage *= (1 - resistances.get(type));
        ((NumericStat) stats.getStat("HP")).modifyBase(-damage);
        notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.LOST_HP, damage, type));
        return damage;
    }

    /**
     * @return the direction
     */
    public float getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(float direction) {
        this.direction = direction;
    }
    
    public void modifyDirection(int direction) {
        this.direction += direction * stats.getScore("Turning Speed");
        while (getDirection() > 360) {
            this.direction -= 360;
        }
        while (getDirection() < 0) {
            this.direction += 360;
        }
    }

    @Override
    public boolean isDead() {
        return !(stats.getScore("HP") > 0);
    }

    @Override
    public void destroy() {
        
        notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.DIED));
        
        for (ItemDrop itemDrop : itemDrops) {
            Item item = itemDrop.getItem();
            item.setPosition(getPosition());
            TempleOfTheElements.game.addClickable(item);
            TempleOfTheElements.game.addSprite(item);
            TempleOfTheElements.game.room.add(item);
        }
        TempleOfTheElements.game.room.remove(this);
        TempleOfTheElements.game.world.destroyBody(fixture.getBody());
        TempleOfTheElements.game.removeSprite(this);
        
        TempleOfTheElements.game.player.gainExperience((int) stats.getScore("XP"));
    }

    @Override
    public void setPosition(Position position) {
        fixture.getBody().setTransform(position, 0);
    }

    @Override
    public boolean isEnemy() {
        return controller.isEnemy();
    }

    @Override
    public void collisionLogic(Object o) {
        notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.COLLIDED, o));
        if (o instanceof Attack) notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.TOOK_HIT, o));
    }

    @Override
    public boolean isImpassable() {
        return true;
    }

    @Override
    public boolean isDamaging() {
        return false;
    }

    @Override
    public float hit(Object o) {
        return 0;
    }

    @Override
    public float getDrawWidth() {
        return sprite.getDrawWidth();
    }

    @Override
    public float getDrawHeight() {
        return sprite.getDrawHeight();
    }
    
    public void drop(Item i) {
        game.addSprite(i);
        game.addClickable(i);
        game.room.add(i);
    }

    public void addStatusEffect(StatusEffect statusEffect) {
        if (!statusEffects.containsKey(statusEffect.name)) {
            statusEffect.creature = this;
            statusEffects.put(statusEffect.name, statusEffect);
            statusEffect.init(this);
            factorStatusEffect(statusEffect);
            notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.ADDED_STATUS_EFFECT, statusEffect));
        } else {
            statusEffects.get(statusEffect.name).update(statusEffect);
        }
    }
    
    private void factorStatusEffect(StatusEffect statusEffect) {
        for (String s : statusEffect.stats.getStatList()) {
            stats.getStat(s).modify(statusEffect.name, statusEffect.stats.getStat(s));
        }
    }

    public void removeStatusEffect(StatusEffect statusEffect) {
        if (statusEffects.containsKey(statusEffect.name)) {
            statusEffects.remove(statusEffect.name);
            defactorStatusEffect(statusEffect);
            notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.LOST_STATUS_EFFECT, statusEffect));
        }
    }
    
    private void defactorStatusEffect(StatusEffect statusEffect) {
        for (String s : statusEffect.stats.getStatList()) {
            stats.getStat(s).removeMod(statusEffect.name);
        }
    }

    @Override
    public boolean isClicked(float x, float y) {
        return (x > getPosition().x - stats.getScore("Size")/2 && x < getPosition().x + stats.getScore("Size")/2
                    && y > getPosition().y - stats.getScore("Size")/2 && y < getPosition().y + stats.getScore("Size")/2);
    }

    @Override
    public void mouseEvent(int button, int action, int mods) {}

    @Override
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Damager getOwner() {
        return this;
    }
    
    public void performAction(OngoingAction ongoingAction) {
        currentAction = ongoingAction;
        ongoingAction.begin(this);
    }
    
    public class BodyPart {
        protected Equipment equipment;
        protected String type;
        protected float position; //This is the degree on the body 
                              //where the bodypart can be found.
        
        public BodyPart(String type, float position) {
            this.type = type;
            this.position = position;
        }
        
        public void unequip() {
            for (String s : equipment.playerStats.getStatList()) {
                stats.getStat(s).removeMod(equipment.getName());
            }
            equipment = null;
        }
        
        public boolean canEquip(Equipment e) {
            return e.getType().equals(type);
        }
        
        public void equip(Equipment e) {
            if (canEquip(e)) {
                equipment = e;
                factor();
            }
        }
        
        private void factor() {
            if (equipment == null) return;
            for (String s : equipment.playerStats.getStatList()) {
                stats.getStat(s).modify(equipment.getName(), equipment.playerStats.getStat(s));
            }
        }
        
        /**
         * @return the position
         */
        public float getPosition() {
            return position;
        }
    }
    
    public class Hand extends BodyPart {

        public Hand(float position) {
            super("Weapon", position);
        }
        
        @Override
        public boolean canEquip(Equipment e) {
            return (e instanceof Weapon);
        }
        
        @Override
        public void equip(Equipment e) {
            if (canEquip(e)) {
                equipment = e;
                factor();
            }
        }
        
        @Override
        public void unequip() {
            super.unequip();
            controller.refactorActions();
        }
        
        private void factor() {
            super.factor();
            for (AttackDefinition a : ((Weapon) equipment).getAttacks()) 
                controller.addAction(new AttackAction(a)); 
        }
    }
}
