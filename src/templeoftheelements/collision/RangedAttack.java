
package templeoftheelements.collision;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.opengl.GL11;
import stat.NoSuchStatException;
import templeoftheelements.item.AttackDefinition;
import templeoftheelements.TempleOfTheElements;
import templeoftheelements.display.Renderable;
import templeoftheelements.display.VectorCircle;
import templeoftheelements.player.CreatureEvent;

/**
 *
 * @author angle
 */


public class RangedAttack extends Attack {
    
    private Renderable sprite;
    private Fixture fixture;
    private boolean dead;
    private String type;

    public RangedAttack(Creature c, AttackDefinition attack, Shape bodyShape) {
        super(c);
        addAllStats(attack.viewStats());
        dead = false;
        this.type = attack.getType();
        
        try {
            sprite = new VectorCircle(attack.getScore("Size"));
        } catch (NoSuchStatException ex) {
            Logger.getLogger(RangedAttack.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyType.DYNAMIC;
        Body body = TempleOfTheElements.game.world.createBody(bodydef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0;
        fixtureDef.shape = bodyShape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        fixture.setSensor(true);
        body.setUserData(this);
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
        } dead = true;
        origin.notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.DEALT_DAMAGE, damage, o));
        return damage;
    }

    @Override
    public void step(float dt) {  
    }

    @Override
    public boolean isEnemy() {
        return false;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void destroy() {
        TempleOfTheElements.game.world.destroyBody(fixture.getBody());
        TempleOfTheElements.game.removeSprite(this);
    }

    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glTranslated(getPosition().x, getPosition().y, 0);
        sprite.draw();
        GL11.glPopMatrix();
    }

    @Override
    public Body getBody() {
        return fixture.getBody();
    }

    @Override
    public void collisionLogic(Object o) {
        if (o == origin) return;
        if (o instanceof Collidable && ((Collidable) o).isImpassable()) dead = true;
        if (o instanceof Creature) hit((Creature) o);
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
    public void createBody(Vec2 position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void createBody() {
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
    public Vec2 getPosition() {
        return getBody().getPosition();
    }

    @Override
    public void setPosition(Vec2 position) {
        getBody().setTransform(position, 0);
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
