 
package templeoftheelements.controller;

import templeoftheelements.creature.Creature;
import org.jbox2d.common.Vec2;
import templeoftheelements.Actor;

/**
 *
 * @author angle
 */


public interface Controller extends Actor {
    
    public Creature getCreature();
    public Vec2 getAccel();
    public Controller clone(Creature creature);
    public void addAction(Action a);
    public void refactorActions();
    public void init();
}
