/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements;

import java.util.ArrayList;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.opengl.GL11;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.collision.Collidable;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;
import templeoftheelements.display.VectorCircle;
import templeoftheelements.effect.Effect;

/**
 *
 * @author angle
 */
public class AreaEffect implements Collidable, Actor, Renderable {
    
    private Fixture fixture;
    private int timer;
    private boolean begun, expired;
    private String name;
    private Renderable sprite;
    public ArrayList<Effect> startEffects;
    public ArrayList<Effect> stopEffects;
    public ArrayList<Effect> ongoingEffects;
    private Creature origin;
    
    public AreaEffect(Creature creator, int time, int radius, Vec2 position) {
        
        origin = creator;
        timer = time;
        begun = true;
        startEffects = new ArrayList<>();
        stopEffects = new ArrayList<>();
        ongoingEffects = new ArrayList<>();
        sprite = new VectorCircle(radius);
        game.addSprite(this);
        game.addActor(this);
        
        BodyDef bodydef = new BodyDef();
        bodydef.position.set(position);
        bodydef.type = BodyType.DYNAMIC;
        Body body = TempleOfTheElements.game.world.createBody(bodydef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        Shape bodyShape = new CircleShape();
        bodyShape.setRadius(radius);
        fixtureDef.shape = bodyShape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        fixture.setSensor(true);
        body.setUserData(this);
    }

    @Override
    public Body getBody() {
        return fixture.getBody();
    }

    @Override
    public void collisionLogic(Object o) {
        for (Effect e: ongoingEffects)
            e.effect(origin, o);
        if (!begun) 
            for (Effect e: startEffects)
                e.effect(origin, o);
        else if (timer == 0)
            for (Effect e: stopEffects)
                e.effect(origin, o);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isDamaging() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float hit(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Position getPosition() {
        return new Position(fixture.getBody().getPosition());
    }

    @Override
    public void setPosition(Position position) {
        fixture.getBody().setTransform(position, 0);
    }

    @Override
    public void step(float dt) {
        begun = true;
        if (timer > 0) {
            timer--;
        } else expired = true;
            
    }

    @Override
    public boolean isEnemy() {
        return false;
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
    public void draw() {
        if (sprite != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(getPosition().x, getPosition().y, 0);
            sprite.draw();
            GL11.glPopMatrix();
        }
    }

    @Override
    public float getDrawWidth() {
        if (sprite != null)
            return sprite.getDrawWidth();
        else return 0;
    }

    @Override
    public float getDrawHeight() {
        if (sprite != null)
            return sprite.getDrawHeight();
        else return 0;
    }
    
}
