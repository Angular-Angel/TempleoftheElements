
package templeoftheelements.display;

import com.samrj.devil.math.Vec4;
import org.jbox2d.common.Vec2;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.player.characterwheel.AbilityNode;
import templeoftheelements.player.characterwheel.CharacterNode;
import templeoftheelements.player.characterwheel.CharacterWheel;
import static templeoftheelements.TempleOfTheElements.rotate;

/**
 *
 * @author angle
 */


public class CharacterScreen extends Screen {
    
    private Vec2 view;
    private float angle;
    private CharacterWheel wheel;
    private StatScreen screen;
    
    public CharacterScreen(CharacterWheel wheel) {
        view = new Vec2();
        this.wheel = wheel;
        angle  = 0;
        screen = new StatScreen(0, 0, 0, 0);
    }

    @Override
    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glPushMatrix();
        GL11.glTranslated(game.config.resolution.x/2, game.config.resolution.y/2, 0);
        GL11.glTranslated(view.x, view.y, 0);
        GL11.glRotatef(angle, 0, 0, 1);
        for (CharacterNode r : wheel.nodes) {
            r.draw();
        }
        GL11.glPopMatrix();
        
        screen.draw();
    }

    public void mouseEvent(int button, int action, int mods) {
        if (action != GLFW.GLFW_PRESS || button != GLFW.GLFW_MOUSE_BUTTON_1) return;
        Vec2 pos = new Vec2(game.mouse.getX(), game.mouse.getY());
        pos.x -= game.config.resolution.x/2;
        pos.y -= game.config.resolution.y/2;
        pos.x -= view.x;
        pos.y -= view.y;
        pos = rotate(new Vec2(0,0), pos, -angle);
        for (CharacterNode node : wheel.nodes) {
            if (node.isClicked(pos.x, pos.y)) {
                node.mouseEvent(button, action, mods);
                break;
            }
        }
    }

    public void keyEvent(int key, int action, int mods)  {
        if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) stop();
    }

    @Override
    public void step() {
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_Q)) angle -= 1;
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_E)) angle += 1;
        
        if (angle > 360) angle -= 360;
        if (angle < 0) angle += 360;
        
        float dir = angle + 90;
        
        Vec2 accel = new Vec2();
        
        //figure out what direction the player WANTS to go.
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_W)) accel.y = -1;
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_A)) accel.x = 1;
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_S)) accel.y = 1;
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_D)) accel.x = -1;
        
        
        //Figue out the actual direction we're sending them.
        if (accel.x == 0 && accel.y == 0) return;
        else if (accel.x == 1 && accel.y == 0);
        else if (accel.x == 1 && accel.y == 1) dir -= 45;
        else if (accel.x == 0 && accel.y == 1) dir -= 90;
        else if (accel.x == -1 && accel.y == 1) dir -= 135;
        else if (accel.x == -1 && accel.y == 0) dir -= 180;
        else if (accel.x == -1 && accel.y == -1) dir -= 225;
        else if (accel.x == 0 && accel.y == -1) dir -= 270;
        else if (accel.x == 1 && accel.y == -1) dir -= 315;
        
        //make sure our direction makes sense.
        while (dir >= 360) {
            dir -= 360;
        }
        while (dir < 0) {
            dir += 360;
        }
        //figure out exactly what vector we need to send them on. 
        //Why are we making it negative? I dunno that either, but it works.
//        view.x += -4*(float) Math.sin(Math.toRadians(dir));
//        view.y += -4*(float) Math.cos(Math.toRadians(dir));
        view.x += accel.x * 4;
        view.y += accel.y * 4;
    }

    @Override
    public void mouseMoved(float x, float y, float dx, float dy) {
        Vec2 pos = new Vec2(game.mouse.getX(), game.mouse.getY());
        pos.x -= game.config.resolution.x/2;
        pos.y -= game.config.resolution.y/2;
        pos.x -= view.x;
        pos.y -= view.y;
        pos = rotate(new Vec2(0,0), pos, -angle);
        screen.display = false;
        for (CharacterNode node : wheel.nodes) {
            if (node.isClicked(pos.x, pos.y)) {
                screen.setNode(node);
                screen.x = game.mouse.getX();
                screen.y = game.mouse.getY();
                screen.display = true;
                break;
            }
        }
    }
    
    public class StatScreen extends Screen.SubScreen {

        private CharacterNode node;
        public boolean display;
        
        public void setNode(CharacterNode node) {
            this.node = node;
            width = 0;
            height = (node.getStatList().size()) * 20;
            for (String s : node.getStatList()) 
                if (width < s.length() * 20 + 100) width = s.length() * 20 + 100;
            if (node instanceof AbilityNode) {
                height += 20;
                String[] split = ((AbilityNode) node).ability.getDescription().split("\n");
                height += split.length * 20;
                if (width < ((AbilityNode) node).ability.getName().length() * 13) 
                    width = ((AbilityNode) node).ability.getName().length() * 13;
                for (String s : split)
                if (width < s.length() * 13) 
                    width = s.length() * 13;
            }
        }
        
        public StatScreen(float x, float y, float width, float height, Vec4 background, Vec4 border) {
            super(x, y, width, height, background, border);
        }

        private StatScreen(int x, int y, int width, int height) {
             super(x, y, width, height);
        }
        @Override
        public void draw() {
            if (!display) return;
            super.draw();
            node.showDescription(this);
        }
    }
    
}
