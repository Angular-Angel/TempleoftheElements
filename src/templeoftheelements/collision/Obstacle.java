
package templeoftheelements.collision;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.opengl.GL11;
import templeoftheelements.TempleOfTheElements;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public class Obstacle implements Damageable, Renderable{

    private Fixture fixture;
    private Shape shape;
    private Renderable sprite;
    private float size;
    private Position createPosition; //only used for a createBody method, not for 
    //determining ongoing position. This is for when you want to store an obstacle
    // in a room without creating it.
    
    public Obstacle(float x, float y, Shape shape, Renderable sprite, float size) {
        this(new Position(x, y), shape, sprite, size);
    }
    
    public Obstacle(Position pos, Shape shape, Renderable sprite, float size) {
        this.size = size;
        createPosition = pos;
        this.shape = shape;
        this.sprite = sprite;
    }
    
    @Override
    public Body getBody() {
        return fixture.getBody();
    }

    @Override
    public void collisionLogic(Object o) {
        
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
        bodydef.type = BodyType.STATIC;
        shape.setRadius(size);
        Body body = TempleOfTheElements.game.world.createBody(bodydef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        body.setUserData(this);
    }

    @Override
    public boolean isImpassable() {
        return true;
    }

    @Override
    public Position getPosition() {
        return new Position(getBody().getPosition());
    }

    @Override
    public void setPosition(Position position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isDamaging() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float hit(Object o) {
        return 0;
    }

    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glTranslated(getPosition().x, getPosition().y, 0);
        sprite.draw();
        GL11.glPopMatrix();
    }

    @Override
    public float getDrawWidth() {
        return sprite.getDrawWidth();
    }

    @Override
    public float getDrawHeight() {
        return sprite.getDrawHeight();
    }

    @Override
    public float takeDamage(float damage, String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEnemy() {
        return false;
    }
    
}
