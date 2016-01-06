
package templeoftheelements.collision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.lwjgl.opengl.GL11;
import stat.NoSuchStatException;
import stat.StatContainer;
import templeoftheelements.Actor;
import templeoftheelements.item.AttackDefinition;
import templeoftheelements.Controller;
import templeoftheelements.item.Equipment;
import templeoftheelements.item.Item;
import templeoftheelements.TempleOfTheElements;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.item.Weapon;
import templeoftheelements.display.VectorCircle;
import templeoftheelements.display.Renderable;
import templeoftheelements.item.ItemDrop;
import templeoftheelements.player.*;



public class Creature extends StatContainer implements Collidable, Actor, Renderable {
    private Renderable sprite;
    private Controller controller;
    private Fixture fixture;
    private float direction, timer;
    private MeleeAttack curAttack;
    private HashSet<Action> abilities;
    private Vec2 createPosition; //only used for a createBody method, not for 
    //determining ongoing position. This is for when you want to store a body in 
    //a room without creating it.
    public ArrayList<BodyPart> bodyParts;
    private HashMap<String, Float> resistances;
    public ArrayList<ItemDrop> itemDrops;
    private HashMap<String, StatusEffect> statusEffects;
    private ArrayList<CreatureListener> listeners;
    private ArrayList<PassiveAbility> passives;
    private boolean winded;

    public Creature() {
        this(new Vec2(), new StatContainer() {});
    }
    
    public Creature(StatContainer stats) {
        this(new Vec2(), stats);
    }
    
    public Creature(Vec2 pos, StatContainer stats) {
        timer = 0; direction = 0;
        createPosition = pos;
        winded = false;
        abilities = new HashSet<>();
        bodyParts = new ArrayList<>();
        resistances = new HashMap<>();
        itemDrops = new ArrayList<>();
        statusEffects = new HashMap<>();
        listeners = new ArrayList<>();
        passives = new ArrayList<>();
        addAllStats(stats);
        try {
            sprite = new VectorCircle(getScore("Size"));
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Creature.class.getName()).log(Level.SEVERE, null, ex);
            sprite = new VectorCircle(1);
        }
    }
    
    public void addAbility(Object a) {
        if (a instanceof Action) abilities.add((Action) a);
        if (a instanceof PassiveAbility) passives.add((PassiveAbility) a);
        if (a instanceof CreatureListener) listeners.add((CreatureListener) a);
        if (a instanceof CreatureAbility) ((CreatureAbility) a).init(this);
    }
    
    public void removeAbility(Object a) {
        if (a instanceof Action) abilities.remove((Action) a);
        if (a instanceof PassiveAbility) passives.remove((PassiveAbility) a);
        if (a instanceof CreatureListener) listeners.remove((CreatureListener) a);
    }
    
    public HashSet<Action> getActions() {
        return abilities;
    }
    
    @Override
    public void refactor() {
        for (StatusEffect effect : statusEffects.values()) {
            defactorStatusEffect(effect);
        }
        super.refactor();
        for (BodyPart part : bodyParts) {
            part.factor();
        }
        for (StatusEffect effect : statusEffects.values()) {
            factorStatusEffect(effect);
        }
    }
    
    public void refactorActions() {
        for (Action a : abilities) controller.addAction(a);
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
        createBody(new Vec2(x, y));
    }
    
    @Override
    public void createBody(int x, int y) {
        createBody(new Vec2(x, y));
    }
    
    @Override
    public void createBody(Vec2 position) {
        BodyDef bodydef = new BodyDef();
        bodydef.position.set(position);
        bodydef.type = BodyType.DYNAMIC;
        CircleShape bodyShape = new CircleShape();
        try {
            bodyShape.setRadius(getScore("Size"));
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Creature.class.getName()).log(Level.SEVERE, null, ex);
            bodyShape.setRadius(1);
        }
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
        setCreatePosition(new Vec2(x, y));
    }
    
    public void setCreatePosition(Vec2 pos) {
        createPosition = pos;
    }
    
    /**
     * @return the position
     */
    @Override
    public Vec2 getPosition() {
        return getBody().getPosition().clone();
    }

    @Override
    public void step(float dt) {
        for (PassiveAbility passive : passives) passive.step(dt);
        if (controller == null) return;
        controller.step(dt);
        try {
            if (!winded && getScore("Stamina") < getScore("Max Stamina")) {
                getStat("Stamina").modifyBase(getScore("Stamina Regen") * dt * 150);
                if (getScore("Stamina") > getScore("Max Stamina")) getStat("Stamina").set(getScore("Max Stamina"));
            }
            Vec2 speed = getBody().getLinearVelocity().mul(1 - getScore("Acceleration")/getScore("Max Speed"));
            getBody().setLinearVelocity(speed);
            getBody().applyForceToCenter(controller.getAccel().mul(dt).mul(150).mul(getScore("Acceleration")));
            notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.MOVED, getBody().getLinearVelocity().length()));
            if (curAttack != null) {
                winded = true;
                if (!curAttack.isDead()) {
                    curAttack.move(getPosition(), getDirection());
                } else {
                    timer = (int) curAttack.getScore("Recovery Time");
                    curAttack = null;
                }
            }
            float staminaPercentage = getScore("Stamina") / getScore("Max Stamina");
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
            
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Creature.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (timer > 0) {
            timer -= dt*150;
            winded = false;
        }
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
    public Body getBody() {
        return fixture.getBody();
    }
    
    public float takeDamage(float damage, String type) {
        notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.TOOK_DAMAGE, damage, type));
        if (resistances.containsKey(type)) damage *= (1 - resistances.get(type));
        try {
            getStat("HP").modifyBase(-damage);
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Creature.class.getName()).log(Level.SEVERE, null, ex);
        }
        notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.LOST_LIFE, damage, type));
        return damage;
    }
    
    public void attack(AttackDefinition attack) {
        try {
            if (curAttack != null || timer > 0) return;
            if (attack.hasStat("Stamina Cost") && attack.getScore("Stamina Cost") > getScore("Stamina")) return;
            Attack a = attack.generate(this);
            TempleOfTheElements.game.addSprite(a);
            TempleOfTheElements.game.addActor(a);
            if (a instanceof MeleeAttack) curAttack = (MeleeAttack) a;
            notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.ATTACKED, a));
            if (attack.hasStat("Stamina Cost")) getStat("Stamina").modifyBase(-a.getScore("Stamina Cost"));
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Creature.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            this.direction += direction * getScore("Turning Speed");
            while (getDirection() > 360) {
                this.direction -= 360;
            }
            while (getDirection() < 0) {
                this.direction += 360;
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Creature.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isDead() {
        try {
            return !(getScore("HP") > 0);
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Creature.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
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
        
        try {
            TempleOfTheElements.game.player.gainExperience((int) getScore("XP"));
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Creature.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setPosition(Vec2 position) {
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
            game.addActor(statusEffect);
            notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.ADDED_STATUS_EFFECT, statusEffect));
        } else {
            statusEffects.get(statusEffect.name).update(statusEffect);
        }
    }
    
    private void factorStatusEffect(StatusEffect statusEffect) {
        addAllStats(statusEffect);
    }

    public void removeStatusEffect(StatusEffect statusEffect) {
        if (statusEffects.containsKey(statusEffect.name)) {
            statusEffects.remove(statusEffect.name);
            defactorStatusEffect(statusEffect);
            statusEffect.creature = null;
            notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.LOST_STATUS_EFFECT, statusEffect));
        }
    }
    
    private void defactorStatusEffect(StatusEffect statusEffect) {
        removeAllStats(statusEffect);
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
            removeAllStats(equipment.playerStats);
            equipment = null;
        }
        
        public void refactor() {
            Creature.super.refactor();
            
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
            addAllStats(equipment.playerStats);
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
                super.factor();
                factor();
            }
        }
        
        public void unequip() {
            removeAllStats(equipment.playerStats);
            equipment = null;
            controller.refactorActions();
        }
        
        private void factor() {
            for (AttackDefinition a : ((Weapon) equipment).getAttacks()) 
                controller.addAction(new AttackAction(a)); 
        }
        
        public void refactor() {
            super.refactor();
        }
        
    }
}
