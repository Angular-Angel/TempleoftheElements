
package templeoftheelements.controller;

import templeoftheelements.controller.Action;
import templeoftheelements.creature.Ability;
import java.util.HashMap;
import org.jbox2d.common.Vec2;
import stat.StatContainer;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;
import templeoftheelements.display.VectorCircle;

/**
 *
 * @author angle
 */


public class DoNothingAction implements OngoingAction {

    @Override
    public void begin(Creature c) {
    }

    @Override
    public void step(float dt) {
    }
    
}
