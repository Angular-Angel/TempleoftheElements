
package templeoftheelements.display;

import com.samrj.devil.math.Vec4;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import stat.NoSuchStatException;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.player.CharacterNode;
import templeoftheelements.player.CharacterWheel;
import templeoftheelements.player.Inventory;

/**
 *
 * @author angle
 */


public class CharacterScreen extends Screen {
    
    private Vec2 view;
    private CharacterWheel wheel;
    private StatScreen screen;
    
    public CharacterScreen(CharacterWheel wheel) {
        view = new Vec2();
        this.wheel = wheel;
        screen = new StatScreen(0, 0, 0, 0);
    }

    @Override
    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glPushMatrix();
        GL11.glTranslated(game.res.width/2, game.res.height/2, 0);
        GL11.glTranslated(view.x, view.y, 0);
        for (CharacterNode r : wheel.nodes) {
            r.draw();
        }
        GL11.glPopMatrix();
        
        screen.draw();
    }

    public void mouseEvent(int button, int action, int mods) {
        if (action != GLFW.GLFW_PRESS || button != GLFW.GLFW_MOUSE_BUTTON_1) return;
        Vec2 pos = new Vec2(game.mouse.getX(), game.mouse.getY());
        pos.x -= game.res.width/2;
        pos.y -= game.res.height/2;
        pos.x -= view.x;
        pos.y -= view.y;
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
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_W)) view.y -= 5;
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_A)) view.x += 5;
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_S)) view.y += 5;
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_D)) view.x -= 5;
    }

    @Override
    public void mouseMoved(float x, float y, float dx, float dy) {
        Vec2 pos = new Vec2(game.mouse.getX(), game.mouse.getY());
        pos.x -= game.res.width/2;
        pos.y -= game.res.height/2;
        pos.x -= view.x;
        pos.y -= view.y;
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
    
    public class StatScreen extends SubScreen {

        private CharacterNode node;
        public boolean display;
        
        public void setNode(CharacterNode node) {
            this.node = node;
            width = 0;
            height = (node.getStatList().size()) * 20;
            for (String s : node.getStatList()) 
                if (width < s.length() * 20 + 100) width = s.length() * 20 + 100;
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
            int i = -10;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor3f(255, 0, 0);
            game.font.getTexture().bind();
            for (String s : node.getStatList()) {
                try {
                    game.font.draw(s + ": " + node.getScore(s), new com.samrj.devil.math.Vec2(x, y + i));
                    i += 20;
                } catch (NoSuchStatException ex) {
                    Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
