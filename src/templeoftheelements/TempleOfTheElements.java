package templeoftheelements;

import templeoftheelements.creature.Creature;
import com.samrj.devil.game.Game;
import com.samrj.devil.game.GameConfig;
import com.samrj.devil.gl.DGL;
import com.samrj.devil.gl.Texture2D;
import com.samrj.devil.math.Mat2;
import com.samrj.devil.ui.Font;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.*;
import stat.NoSuchStatException;
import templeoftheelements.collision.*;
import templeoftheelements.collision.Room.Door;
import templeoftheelements.display.MenuScreen;
import templeoftheelements.display.Renderable;
import templeoftheelements.display.Screen;
import templeoftheelements.player.Clickable;
import templeoftheelements.player.Player;

public final class TempleOfTheElements extends Game {
    
    public static TempleOfTheElements game;
    public static final int PIXELS_PER_METER = 30;
    
    public final Font font;
    public final GameConfig config;
    public final MenuScreen menu;
    
    private Set<Renderable> sprites;
    private Set<Actor> actors;
    private Set<Actor> newActors;
    private Set<Clickable> clickables;
    private Set<Collidable> collidables;
    public Registry registry;
    public Player player;
    public World world;
    public Room room;
    public Random random;
    
    public Screen screen;
    
    public static void main(String[] args) throws Exception {
        Game.init();
        try {
            game = new TempleOfTheElements();
            game.start();
            game.run();
            game.destroy();
        }
        finally {
            game = null;
            Game.terminate();
        }
    }
    
    public static com.samrj.devil.math.Vec2 JBoxtoDevil(Vec2 v) {
        v.mul(PIXELS_PER_METER);
        return new com.samrj.devil.math.Vec2(v.x, v.y);
    }
    
    public int getResolutionWidth() {
        return config.resolution.x;
    }
    
    public int getResolutionHeight() {
        return config.resolution.y;
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

    private TempleOfTheElements() throws IOException{
        super();
        
        config = new GameConfig();
        
        DGL.init();
        
        // init OpenGL
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, config.resolution.x, 0, config.resolution.y, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        Texture2D glTexture2D = DGL.loadTex2D("mono_20.png");
        font = new Font(glTexture2D, "mono_20.csv", 32);
        menu = new MenuScreen();
        screen = menu;
    }
    
    public void start() {
        sprites = Collections.newSetFromMap(new IdentityHashMap<>());;
        actors = Collections.newSetFromMap(new IdentityHashMap<>());;
        newActors = Collections.newSetFromMap(new IdentityHashMap<>());
        collidables = Collections.newSetFromMap(new IdentityHashMap<>());;
        clickables = Collections.newSetFromMap(new IdentityHashMap<>());;
        world = new World(new Vec2());
        world.setContactListener(new CollisionManager());
        registry = new Registry();
        random = new Random();
        ((InitScript) registry.readGroovyScript(new File("RAW/Scripts/Init.groovy"))).Init();
    }
    
    public void addActor(Actor a) {
        newActors.add(a);
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
    
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }
    
    public void addSprite(Renderable r) {
        sprites.add(r);
    }
    
    public void removeSprite(Renderable r) {
        sprites.remove(r);
    }

    @Override
    public void step(float dt) {
        if (screen == null) {
            for (Actor a : newActors) {
                actors.add(a);
            }
            newActors.clear();
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

    @Override
    public void render() {
        if (screen == null) {
            // Clear the screen and depth buffer
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 

            GL11.glPushMatrix();
            GL11.glTranslatef(config.resolution.x/2, config.resolution.y/7, 0);
            GL11.glRotatef(player.getCreature().getDirection(), 0, 0, 1);
            GL11.glScalef(TempleOfTheElements.PIXELS_PER_METER, TempleOfTheElements.PIXELS_PER_METER, 1f);
            Vec2 pos = player.getCreature().getPosition();
            GL11.glTranslatef(-pos.x, -pos.y, 0);

            room.draw();

            sprites.forEach((Renderable renderable) -> renderable.draw());
            GL11.glPopMatrix();

            player.hud.draw();
        } else screen.render();
    }
    
    public void enterDoor(Door door) {
        if (door.getDestination() == null) return;
        sprites.clear();
        actors.clear();
        collidables.clear();
        clickables.clear();
        world = new World(new Vec2());
        world.setContactListener(new CollisionManager());
        room = door.getDestination().getRoom();
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
                String creatureName;
                switch (key) {
//                    case GLFW.GLFW_KEY_0: creatureName = "Wanderer 0"; break;
//                    case GLFW.GLFW_KEY_1: creatureName = "Wanderer 1"; break;
//                    case GLFW.GLFW_KEY_2: creatureName = "Wanderer 2"; break;
//                    case GLFW.GLFW_KEY_3: creatureName = "Wanderer 3"; break;
//                    case GLFW.GLFW_KEY_4: creatureName = "Wanderer 4"; break;
//                    case GLFW.GLFW_KEY_5: creatureName = "Wanderer 5"; break;
//                    case GLFW.GLFW_KEY_6: creatureName = "Wanderer 6"; break;
//                    case GLFW.GLFW_KEY_7: creatureName = "Wanderer 7"; break;
//                    case GLFW.GLFW_KEY_8: creatureName = "Wanderer 8"; break;
//                    case GLFW.GLFW_KEY_9: creatureName = "Wanderer 9"; break;
                    default:
                        creatureName = null;
                        player.keyEvent(key, action, mods);
                }
                
                if (creatureName != null) {
                        Creature creature = registry.creatureDefs.get(creatureName).genCreature();
                        creature.createBody(new Position(-10,-10));
                        addActor(creature);
                        addSprite(creature);
                        room.add(creature);
                }
                
            } catch (NoSuchStatException ex) {
                Logger.getLogger(TempleOfTheElements.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else screen.keyEvent(key, action, mods);
    }

    @Override
    public void onDestroy() {
        DGL.destroy();
    }
}
