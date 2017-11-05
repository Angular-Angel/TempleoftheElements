
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


public class DoNothingAction extends Action {

    private Renderable sprite;
    
    public DoNothingAction(Renderable sprite) {
        super("Do Nothing", new StatContainer());
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
    public Ability copy() {
        return new DoNothingAction(sprite);
    }

    @Override
    public String getDescription() {
        return "Do Nothing.";
    }
    
}
