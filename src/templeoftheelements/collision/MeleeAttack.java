 
package templeoftheelements.collision;


import templeoftheelements.creature.Creature;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.opengl.GL11;
import stat.NoSuchStatException;
import templeoftheelements.item.AttackDefinition;
import templeoftheelements.TempleOfTheElements;
import templeoftheelements.display.VectorCircle;
import templeoftheelements.display.Renderable;
import templeoftheelements.creature.CreatureEvent;

/**
 *
 * @author angle
 */


public class MeleeAttack extends Attack {
    
    private Renderable sprite;
    private Fixture fixture;
    private int timer, angle;
    private float dist;
    private boolean expired;
    private String type;
    
    
    public MeleeAttack(Creature c, AttackDefinition attack, Shape bodyShape, int angle, float distance) {
        super(c);
        addAllStats(attack.viewStats());
        expired = false;
        try {
            this.timer = (int) attack.getScore("Duration");
            sprite = new VectorCircle(attack.getScore("Size"));
        } catch (NoSuchStatException ex) {
            Logger.getLogger(MeleeAttack.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.angle = angle;
        this.type = attack.getType();
        dist = distance;
        
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyType.DYNAMIC;
        Body body = TempleOfTheElements.game.world.createBody(bodydef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.shape = bodyShape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        fixture.setSensor(true);
        body.setUserData(this);
    }

    @Override
    public void step(float dt) {
        if (timer > 0) {
            try {
                timer--;
                if (getScore("Angular Travel") > 0)
                    angle += getScore("Angular Travel")/getScore("Duration");
                if (getScore("Distance Travel") > 0)
                    dist += getScore("Distance Travel")/getScore("Duration");
            } catch (NoSuchStatException ex) {
                Logger.getLogger(MeleeAttack.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else expired = true;
    }

    @Override
    public float hit(Object o) {
        if (!(o instanceof Creature)) return 0;
        Creature creature = (Creature) o;
        float damage = super.hit(o);
        try {
            damage += creature.takeDamage(getScore("Damage"), type);
        } catch (NoSuchStatException ex) {
            Logger.getLogger(MeleeAttack.class.getName()).log(Level.SEVERE, null, ex);
        } expired = true;
        origin.notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.DEALT_DAMAGE, damage, o));
        return damage;
    }

    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glTranslated(getPosition().x, getPosition().y, 0);
        sprite.draw();
        GL11.glPopMatrix();
    }

    @Override
    public Position getPosition() {
        return new Position (fixture.getBody().getPosition());
    }

    @Override
    public boolean isDead() {
        return expired;
    }

    @Override
    public void destroy() {
        TempleOfTheElements.game.world.destroyBody(fixture.getBody());
        TempleOfTheElements.game.removeSprite(this);
    }

    @Override
    public Body getBody() {
        return fixture.getBody();
    }
    
    public void move(Position pos, float direction) {
        pos.x += 2 * dist * (float) Math.sin(Math.toRadians(angle + direction));
        pos.y += 2 * dist * (float) Math.cos(Math.toRadians(angle + direction));
        setPosition(pos);
    }

    @Override
    public void setPosition(Position position) {
        fixture.getBody().setTransform(position, 0);
    }

    @Override
    public boolean isEnemy() {
        return false;
    }

    @Override
    public void collisionLogic(Object o) {
        if (o instanceof Collidable && ((Collidable) o).isImpassable()) expired = true;
        if (o instanceof Creature) hit((Creature) o);
    }

    @Override
    public void createBody() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createBody(float x, float y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createBody(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createBody(Position position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isImpassable() {
        return false;
    }

    @Override
    public boolean isDamaging() {
        return true;
    }

    @Override
    public float getDrawWidth() {
        return sprite.getDrawWidth();
    }

    @Override
    public float getDrawHeight() {
        return sprite.getDrawHeight();
    }
    
}
