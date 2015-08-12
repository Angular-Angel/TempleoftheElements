 
package templeoftheelements;

import java.util.HashSet;
import templeoftheelements.collision.Creature;
import org.jbox2d.common.Vec2;
import templeoftheelements.player.Action;

/**
 *
 * @author angle
 */


public interface Controller extends Actor {
    
    public Creature getCreature();
    public Vec2 getAccel();
    public Controller clone(Creature creature);
    public HashSet<Action> getActions();
    public void addAction(Action a);
    public void refactorActions();
}
