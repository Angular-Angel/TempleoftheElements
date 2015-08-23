
package templeoftheelements.collision;

import com.samrj.devil.graphics.GLTexture2D;
import com.samrj.devil.graphics.GraphicsUtil;
import com.samrj.devil.math.Vec4;
import java.util.ArrayList;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.opengl.GL11;
import templeoftheelements.Actor;
import templeoftheelements.TempleOfTheElements;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.display.Renderable;
import templeoftheelements.display.Texture;
import templeoftheelements.player.Clickable;



public class Room implements Renderable, Collidable, Actor {
    
    int width, height;
    private Vec4 color;
    Body[] walls;
    private ArrayList<Object> stuff;
    private boolean cleared;
    private Texture floor;
    
    public Room(int width, int height, Texture texture) {
        cleared = false;
        stuff = new ArrayList<>();
        color = new Vec4(0.0f, 0.0f, 1.0f, 1.0f);
        
        walls = new Body[4];
        
        this.width = width;
        this.height = height;
        floor = texture;
    }
    
    public Room (int width, int height, GLTexture2D texture) {
        this(width, height, new Texture(texture));
    }

    @Override
    public void draw() {
        GraphicsUtil.glColor(color);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(width/2, height/2);
        GL11.glVertex2f(-(width/2), (height/2));
        GL11.glVertex2f(-(width/2), -(height/2));
        GL11.glVertex2f((width/2), -(height/2));
        GL11.glVertex2f(width/2, height/2);
        GL11.glEnd();
        
        floor.draw(width, height);
    }
    
    public void add(Object o) {
        stuff.add(o);
    }
    
    public void remove(Object o) {
        stuff.remove(o);
    }
    
    public void checkCleared() {
        for (Actor a : game.actors) {
            if (a.isEnemy()) {
                cleared = false;
                return;
            }
        }
        cleared = true;
        return;
    }
    
    public boolean isCleared() { return cleared; }
    
    public void enter() {
        createBody();
        game.addActor(this);
        for (Object o : stuff) {
            if (o instanceof Collidable) ((Collidable) o).createBody();
            if (o instanceof Actor) game.addActor((Actor) o);
            if (o instanceof Renderable) game.addSprite((Renderable) o);
            if (o instanceof Clickable) game.addClickable((Clickable) o);
            
        }
        game.player.getCreature().createBody();
        game.addActor(game.player.getCreature());
        game.addSprite(game.player.getCreature());
    }

    @Override
    public Body getBody() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void collisionLogic(Object o) {
        
    }

    @Override
    public void createBody() {
        BodyDef bodydef = new BodyDef();
        bodydef.position.set(new Vec2(-width/2, 0));
        bodydef.type = BodyType.STATIC;
        PolygonShape bodyshape = new PolygonShape();
        bodyshape.setAsBox(0, height/2);
        walls[0] = TempleOfTheElements.game.world.createBody(bodydef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.shape = bodyshape;
        Fixture fixture = walls[0].createFixture(fixtureDef);
        fixture.setUserData(this);
        walls[0].setUserData(this);
        
        bodydef = new BodyDef();
        bodydef.position.set(new Vec2(width/2, 0));
        bodydef.type = BodyType.STATIC;
        bodyshape = new PolygonShape();
        bodyshape.setAsBox(0, height/2);
        walls[1] = TempleOfTheElements.game.world.createBody(bodydef);
        fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.shape = bodyshape;
        fixture = walls[1].createFixture(fixtureDef);
        fixture.setUserData(this);
        walls[1].setUserData(this);
        
        bodydef = new BodyDef();
        bodydef.position.set(new Vec2(0, -height/2));
        bodydef.type = BodyType.STATIC;
        bodyshape = new PolygonShape();
        bodyshape.setAsBox(width/2, 0);
        walls[2] = TempleOfTheElements.game.world.createBody(bodydef);
        fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.shape = bodyshape;
        fixture = walls[2].createFixture(fixtureDef);
        fixture.setUserData(this);
        walls[2].setUserData(this);
        
        bodydef = new BodyDef();
        bodydef.position.set(new Vec2(0, height/2));
        bodydef.type = BodyType.STATIC;
        bodyshape = new PolygonShape();
        bodyshape.setAsBox(width/2, 0);
        walls[3] = TempleOfTheElements.game.world.createBody(bodydef);
        fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.shape = bodyshape;
        fixture = walls[3].createFixture(fixtureDef);
        fixture.setUserData(this);
        walls[3].setUserData(this);
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
    public Vec2 getPosition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPosition(Vec2 position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void step(float dt) {
        if (!isCleared()) checkCleared();
    }

    @Override
    public boolean isEnemy() {
        return false;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void destroy() {
        
    }
    
    public Door createDoor(float x1, float y1, float x2, float y2) {
        return createDoor(new Vec2(x1, y1), new Vec2(x2, y2));
    }
    
    public Door createDoor(Vec2 exitPos, Vec2 entrance) {
        Door door= new Door(exitPos);
        door.setEntrance(entrance);
        add(door);
        return door;
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
        return  width;
    }

    @Override
    public float getDrawHeight() {
        return height;
    }
    
    public class Door implements Collidable, Renderable {
        private Door destination;
        private Vec2 exitPosition, entrancePosition, point1, point2; //the last two are for drawing.
        private Fixture fixture;

        public Door(Vec2 exitPos) {
            exitPosition = exitPos;
        }
        
        public Door getDestination() {
            return destination;
        }
        
        public void setDestination(Door dest) {
            destination = dest;
        }
        
        public Vec2 getExit() {
            return exitPosition;
        }
        
        public Vec2 getEntrance() {
            return entrancePosition.clone();
        }
        
        public void setEntrance(Vec2 entrance) {
            entrancePosition = entrance;
        }
        
        public Room getRoom() {
            return Room.this;
        }
        
        @Override
        public Vec2 getPosition() {
            return fixture.getBody().getPosition().clone();
        }

        @Override
        public void setPosition(Vec2 position) {
            fixture.getBody().setTransform(position, 0);
            entrancePosition = position;
        }

        @Override
        public Body getBody() {
            return fixture.getBody();
        }

        @Override
        public void collisionLogic(Object o) {
            if (isCleared() && o instanceof Creature && ((Creature) o).getController() == game.player) game.enterDoor(this);
        }

        @Override
        public void createBody() {
            createBody(entrancePosition);
        }

        @Override
        public void createBody(float x, float y) {
            createBody(new Vec2 (x, y));
        }

        @Override
        public void createBody(int x, int y) {
            createBody(new Vec2 (x, y));
        }

        @Override
        public void createBody(Vec2 position) {
            entrancePosition = position;
            BodyDef bodydef = new BodyDef();
            bodydef.position.set(position);
            bodydef.type = BodyType.STATIC;
            PolygonShape bodyshape = new PolygonShape();
            if (position.x == -width/2 || position.x == width/2) {
                bodyshape.setAsBox(.05f, 1);
                point1 = new Vec2(0, 1f);
                point2 = new Vec2(0, -1f);
            } else {
                bodyshape.setAsBox(1, .05f);
                point1 = new Vec2(1f, 0);
                point2 = new Vec2(-1f, 0);
            }
            Body body = TempleOfTheElements.game.world.createBody(bodydef);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.density = 1;
            fixtureDef.shape = bodyshape;
            fixture = body.createFixture(fixtureDef);
            fixture.setUserData(this);
            body.setUserData(this);
        }

        @Override
        public void draw() {
            GL11.glPushMatrix();
            GL11.glTranslated(entrancePosition.x, entrancePosition.y, 0);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex2d(point1.x, point1.y);
            GL11.glVertex2d(point2.x, point2.y);
            GL11.glEnd();
            GL11.glPopMatrix();
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
            return Math.abs(point1.x - point2.x);
        }

        @Override
        public float getDrawHeight() {
            return Math.abs(point1.y - point2.y);
        }
    }
    
}
