
package templeoftheelements.display;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.collision.Creature;
import templeoftheelements.player.Action;

/**
 *
 * @author angle
 */


public class SelectIcon extends Icon {

    public Option selected;
    private ArrayList<Option> options;
    private boolean selecting;
    
    public SelectIcon(Vec2 pos, float width, float height) {
        super(pos, null, width, height);
        options = new ArrayList<>();
        selecting = false;
    }
    
    public void performAction(Creature creature, int button, int action, int mods) {
        if (selected != null)
        selected.action.perform(creature, new Vec2(game.mouse.getX(), game.mouse.getY()));
    }
    
    public void clearOptions() {
        selected = null;
        options.clear();
    }
    
    public void addOption(Renderable sprite, Action action) {
        options.add(new Option(position.clone().add(new Vec2(0, (options.size()+1)*height)), sprite, action, width, height));
        if (selected == null) selected = options.get(0);
    }
    
    public void addOption(Action action) {
        options.add(new Option(position.clone().add(new Vec2(0, (options.size()+1)*height)), 
                new VectorCircle(10 + (20 * options.size())), action, width, height));
        if (selected == null) selected = options.get(0);
    }
    
    @Override
    public boolean isClicked(float x, float y) {
        if (selecting) {
            if(super.isClicked(x, y))
                return true;
            else for(Option o : options) {
                if (o.isClicked(x, y)) return true;
            }
            return false;
        } else return super.isClicked(x, y);
    }
    
    @Override
    public void mouseEvent(int button, int action, int mods) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS) {
            if(super.isClicked(game.mouse.getX(), game.mouse.getY())) selecting = !selecting;
            else for(Option o : options) {
                if (o.isClicked(game.mouse.getX(), game.mouse.getY())) {
                    selected = o;
                    selecting = false;
                }
            }
        }
    }
    
    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glTranslated(getPosition().x+width/2, getPosition().y+height/2, 0);
        if (selected != null) selected.getSprite().draw();
        GL11.glPopMatrix();
        if (selecting) {
            options.forEach((Option o) -> o.draw());
        }
    }
    
    public class Option extends Icon {
        
        public Action action;
        
        public Option(Vec2 pos, Renderable r, Action a, float width, float height) {
            super(pos, r, width, height);
            action = a;
        }
    }
    
}
