
package templeoftheelements.controller;

import templeoftheelements.controller.Action;
import templeoftheelements.creature.Ability;
import java.util.HashMap;
import org.jbox2d.common.Vec2;
import stat.StatContainer;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public class DoNothingAction implements Action {

    private Renderable sprite;
    
    public DoNothingAction(Renderable sprite) {
        this.sprite = sprite;
    }

    @Override
    public void perform(Creature creature, Position in) {
        
    }

    @Override
    public Renderable getSprite() {
        return sprite;
    }

    @Override
    public String getDescription() {
        return "Do Nothing.";
    }

    @Override
    public void initValues(Creature c) {}

    @Override
    public boolean isPossible(Creature c) {
        return true;
    }
    
}
