 
package templeoftheelements.display;

import com.samrj.devil.math.Vec2;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import static templeoftheelements.TempleOfTheElements.game;

/**
 *
 * @author angle
 */


public class MenuScreen extends Screen {

    private boolean controlView = false;
    
    @Override
    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glColor3f(255, 0, 0);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(game.res.width/6,game.res.height/6);
        GL11.glVertex2f(game.res.width*5/6, game.res.height/6);
        GL11.glVertex2f(game.res.width*5/6, game.res.height*5/6);
        GL11.glVertex2f(game.res.width/6, game.res.height*5/6);
        GL11.glVertex2f(game.res.width/6, game.res.height/6);
        GL11.glEnd();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        if (!controlView) {
            Vec2 position = new Vec2((game.res.width*1f/6) + 5, (game.res.height*4.5f/6) + 20);
            game.font.getTexture().glBind(0);

            game.font.draw("Start", position);
            position.y -= 20;
            game.font.draw("Controls", position);
        } else {
            Vec2 position = new Vec2((game.res.width*1f/6) + 5, (game.res.height*4.5f/6) + 20);
            game.font.getTexture().glBind(0);

            game.font.draw("Controls:", position);
            position.y -= 20;
            game.font.draw("Esc - Exit current screen", position);
            position.y -= 20;
            game.font.draw("W - Move forward", position);
            position.y -= 20;
            game.font.draw("S - Move backward", position);
            position.y -= 20;
            game.font.draw("A - Move left", position);
            position.y -= 20;
            game.font.draw("D - Move right", position);
            position.y -= 20;
            game.font.draw("Q - Turn left", position);
            position.y -= 20;
            game.font.draw("E - Turn right", position);
            position.y -= 20;
            game.font.draw("Leftclick - Activate button, attack or pick up item.", position);
            position.y -= 20;
            game.font.draw("C - View Character Screen", position);
            position.y -= 20;
            game.font.draw("I - View Inventory", position);
            position.y -= 20;
            game.font.draw("0-9 - Generate random monster. Each number has it's", position);
            position.y -= 20;
            game.font.draw("      own monster type, generated on game start.", position);
            
            
        }
    }

    @Override
    public void step() {
        
    }

    public void mouseEvent(int button, int action, int mods) {
        if (button != GLFW.GLFW_MOUSE_BUTTON_1) return;
        if (game.mouse.getX() >= game.res.width*1f/6 && game.mouse.getX() <= game.res.width*5f/6) {
            int i = (int) (Math.abs(game.mouse.getY() - game.res.height) - game.res.height/6 -10)/20;
            if (i == 0) {
                game.screen = null;
            } else if (i == 1) {
                controlView = true;
            }
        }
    }

    public void keyEvent(int key, int action, int mods) {
        if (action != GLFW.GLFW_PRESS) return;
        switch (key) {
            case GLFW.GLFW_KEY_ESCAPE: 
                if (!controlView)
                    game.stop();
                else controlView = false;
                break;
        }
    }

    @Override
    public void mouseMoved(float x, float y, float dx, float dy) {}
    
}
