
package templeoftheelements.player;

import java.util.HashMap;
import org.jbox2d.common.Vec2;
import templeoftheelements.collision.Creature;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public class DoNothingAction extends Action {

    private Renderable sprite;
    
    public DoNothingAction(Renderable sprite) {
        super("Do Nothing", new HashMap<>());
        this.sprite = sprite;
    }

    @Override
    public void perform(Creature creature, Vec2 in) {
        
    }

    @Override
    public Renderable getSprite() {
        return sprite;
    }
    
}
