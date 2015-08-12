
package templeoftheelements.display;

import org.jbox2d.common.Vec2;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.player.CharacterNode;
import templeoftheelements.player.CharacterWheel;

/**
 *
 * @author angle
 */


public class CharacterScreen extends Screen {
    
    private Vec2 view;
    private CharacterWheel wheel;
    
    public CharacterScreen(CharacterWheel wheel) {
        view = new Vec2();
        this.wheel = wheel;
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
    }

    public void mouseEvent(int button, int action, int mods) {
        if (action != GLFW.GLFW_PRESS || button != GLFW.GLFW_MOUSE_BUTTON_1) return;
        Vec2 pos = new Vec2(game.mouse.getX(), game.mouse.getY());
        pos.x -= game.res.width/2;
        pos.y -= game.res.height/2;
        pos.x -= view.x;
        pos.y -= view.y;
        for (CharacterNode node : wheel.nodes) {
            if (node.isClicked(pos.x, pos.y)) node.mouseEvent(button, action, mods);
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
    public void mouseMoved(float x, float y, float dx, float dy) {}
    
}
