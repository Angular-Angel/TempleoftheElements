
package templeoftheelements;

import com.samrj.devil.config.CfgResolution;
import com.samrj.devil.game.Game;
import com.samrj.devil.graphics.GLTexture2D;
import com.samrj.devil.graphics.Texture2DData;
import com.samrj.devil.math.Mat2;
import templeoftheelements.collision.*;
import com.samrj.devil.ui.Font;
import com.samrj.devil.util.IdentitySet;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.lwjgl.opengl.*;
import org.lwjgl.glfw.GLFW;
import stat.NoSuchStatException;
import templeoftheelements.collision.Room.Door;
import templeoftheelements.display.MenuScreen;
import templeoftheelements.display.Renderable;
import templeoftheelements.display.Screen;
import templeoftheelements.player.Clickable;
import templeoftheelements.player.Player;



public class TempleOfTheElements extends Game {
    private boolean running = false;
    
    public Set<Renderable> sprites;
    public Set<Actor> actors;
    public Set<Clickable> clickables;
    public Set<Collidable> collidables;
    public Registry registry;
    public Player player;
    public World world;
    public Font font;
    public static TempleOfTheElements game;
    public static final int PIXELS_PER_METER = 30;
    public final CfgResolution res;
    public MenuScreen menu;
    public Room room;
    public Screen screen;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Game.init();
        game = new TempleOfTheElements();
        
        game.start();
        game.menu = new MenuScreen();
        game.screen = game.menu;
        
        
        game.run();
        
        game.destroy();
    }
    
    public static com.samrj.devil.math.Vec2 JBoxtoDevil(Vec2 v) {
        v.mul(PIXELS_PER_METER);
        return new com.samrj.devil.math.Vec2(v.x, v.y);
    }
    
    public static Vec2 rotate(Vec2 center, com.samrj.devil.math.Vec2 point, float angle) {
        com.samrj.devil.math.Vec2 v = new com.samrj.devil.math.Vec2(point.x - center.x, point.y - center.y);
        rotate(v, (float) Math.toRadians(angle));
        v.x += center.x; 
        v.y += center.y;
        Vec2 ret = new Vec2(v.x, v.y);
        return ret;
    }
    
    public static Vec2 rotate(Vec2 center, Vec2 point, float angle) {
        com.samrj.devil.math.Vec2 v = new com.samrj.devil.math.Vec2(point.x - center.x, point.y - center.y);
        rotate(v, (float) Math.toRadians(angle));
        v.x += center.x; 
        v.y += center.y;
        Vec2 ret = new Vec2(v.x, v.y);
        return ret;
    }
    
    public static com.samrj.devil.math.Vec2 rotate(com.samrj.devil.math.Vec2 v, float ang)
    {
        Mat2 m = Mat2.rotation(ang);
        return v.mult(m);
    }

    public TempleOfTheElements() {
        super();
        
        res = config.getField("res");
        
        // init OpenGL
        
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, res.width, 0, res.height, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        
        try {
            Texture2DData texture2DData = new Texture2DData(
                    new File("mono_20.png"));
            GLTexture2D glTexture2D = new GLTexture2D(texture2DData);
            font = new Font(glTexture2D, "mono_20.csv", 32);
        } catch (IOException ex) {
            Logger.getLogger(TempleOfTheElements.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
    
    public void addActor(Actor a) {
        actors.add(a);
    }
    
    public Clickable getClickable(float x, float y) {
        for (Clickable c : clickables) {
            if (c.isClicked(x, y)) return c;
        }
        return null;
    }
    
    public void addClickable(Clickable c) {
        clickables.add(c);
    }
    
    public void removeClickable(Clickable c) {
        clickables.remove(c);
    }
    
//    public void addCollidable(Collidable c) {
//        collidables.add(c);
//    }
    
    public void addSprite(Renderable r) {
        sprites.add(r);
    }
    
    public void removeSprite(Renderable r) {
        sprites.remove(r);
    }

    public void start() {
        sprites = new IdentitySet<>();
        actors = new IdentitySet<>();
        collidables = new IdentitySet<>();
        clickables = new IdentitySet<>();
        world = new World(new Vec2());
        world.setContactListener(new CollisionManager());
        registry = new Registry();
        ((InitScript) registry.readGroovyScript(new File("Init.groovy"))).Init();
    }

    public void step(float dt) {
        if (screen == null) {
            world.step(dt, 8, 3);
            Iterator<Actor> iter = actors.iterator();
            Actor next;

            while(iter.hasNext()) {
                next = iter.next();
                next.step(dt);
                if (next.isDead()) {
                    next.destroy();
                    iter.remove();
                }
            }
        } else screen.step();
    }

    public void render() {
        if (screen == null) {
            // Clear the screen and depth buffer
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 

            GL11.glPushMatrix();
            GL11.glTranslated(res.width/2, res.height/7, 0);
            GL11.glRotatef(player.getCreature().getDirection(), 0, 0, 1);
            GL11.glScalef(TempleOfTheElements.PIXELS_PER_METER, TempleOfTheElements.PIXELS_PER_METER, 1f);
            Vec2 pos = player.getCreature().getPosition();
            GL11.glTranslated(-pos.x, -pos.y, 0);

            room.draw();

            sprites.forEach((Renderable renderable) -> renderable.draw());
            GL11.glPopMatrix();

            player.hud.draw();
        } else screen.render();
    }
    
//    public final void run() throws LWJGLException
//    {
//        
//        try
//        {
//            init();
//        }
//        catch (Exception e)
//        {
//            throw new RuntimeException(e);
//        }
//        
//        running = true;
//        while (running)
//        {
//            Display.processMessages();
//            if (Display.isCloseRequested())
//            {
//                stop();
//                break;
//            }
//            
//            Input.step(this);
//            step(1f/60f);
//            render();
//            
//            Display.update(false);
//            Display.sync(60);
//        }
//    }
    
    public void enterDoor(Door door) {
        if (door.getDestination() == null) return;
        sprites.clear();
        actors.clear();
        collidables.clear();
        clickables.clear();
        world = new World(new Vec2());
        world.setContactListener(new CollisionManager());
        room = door.getDestination().getRoom();
        player.getCreature().setDirection(0);
        player.getCreature().setCreatePosition(door.getDestination().getExit());
        room.enter();
    }

    @Override
    public void onMouseMoved(float x, float y, float dx, float dy) {
        if (screen != null) screen.mouseMoved(x, y, dx, dy);
    }

    @Override
    public void onMouseButton(int button, int action, int mods) {
        if (screen == null) player.mouseEvent(button, action, mods);
        else screen.mouseEvent(button, action, mods);
    }

    @Override
    public void onMouseScroll(float dx, float dy) {}

    @Override
    public void onKey(int key, int action, int mods) {
        if (screen == null) {
            try {
                if (action != GLFW.GLFW_PRESS) return;
                Creature creature;
                switch (key) {
                    case GLFW.GLFW_KEY_0:
                        creature = registry.creatureDefs.get("Wanderer 0").genCreature();
                        creature.createBody(new Vec2(-10,-10));
                        addActor(creature);
                        addSprite(creature);
                        room.add(creature);
                        break;
                    case GLFW.GLFW_KEY_1:
                        creature = registry.creatureDefs.get("Wanderer 1").genCreature();
                        creature.createBody(new Vec2(-10,-10));
                        addActor(creature);
                        addSprite(creature);
                        room.add(creature);
                        break;
                    case GLFW.GLFW_KEY_2:
                        creature = registry.creatureDefs.get("Wanderer 2").genCreature();
                        creature.createBody(new Vec2(-10,-10));
                        addActor(creature);
                        addSprite(creature);
                        room.add(creature);
                        break;
                    case GLFW.GLFW_KEY_3:
                        creature = registry.creatureDefs.get("Wanderer 3").genCreature();
                        creature.createBody(new Vec2(-10,-10));
                        addActor(creature);
                        addSprite(creature);
                        room.add(creature);
                        break;
                    case GLFW.GLFW_KEY_4:
                        creature = registry.creatureDefs.get("Wanderer 4").genCreature();
                        creature.createBody(new Vec2(-10,-10));
                        addActor(creature);
                        addSprite(creature);
                        room.add(creature);
                        break;
                    case GLFW.GLFW_KEY_5:
                        creature = registry.creatureDefs.get("Wanderer 5").genCreature();
                        creature.createBody(new Vec2(-10,-10));
                        addActor(creature);
                        addSprite(creature);
                        room.add(creature);
                        break;
                    case GLFW.GLFW_KEY_6:
                        creature = registry.creatureDefs.get("Wanderer 6").genCreature();
                        creature.createBody(new Vec2(-10,-10));
                        addActor(creature);
                        addSprite(creature);
                        room.add(creature);
                        break;
                    case GLFW.GLFW_KEY_7:
                        creature = registry.creatureDefs.get("Wanderer 7").genCreature();
                        creature.createBody(new Vec2(-10,-10));
                        addActor(creature);
                        addSprite(creature);
                        room.add(creature);
                        break;
                    case GLFW.GLFW_KEY_8:
                        creature = registry.creatureDefs.get("Wanderer 8").genCreature();
                        creature.createBody(new Vec2(-10,-10));
                        addActor(creature);
                        addSprite(creature);
                        room.add(creature);
                        break;
                    case GLFW.GLFW_KEY_9:
                        creature = registry.creatureDefs.get("Wanderer 9").genCreature();
                        creature.createBody(new Vec2(-10,-10));
                        addActor(creature);
                        addSprite(creature);
                        room.add(creature);
                        break;
                    default: player.keyEvent(key, action, mods);
                }
            } catch (NoSuchStatException ex) {
                Logger.getLogger(TempleOfTheElements.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else screen.keyEvent(key, action, mods);
    }

    @Override
    public void onDestroy() {}
}
