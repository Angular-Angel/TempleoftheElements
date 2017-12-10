
package templeoftheelements.controller;

import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public interface Action {

    public boolean isPossible(Creature c);
    
    public Renderable getSprite(); 
    
    /**
     *
     * @param creature
     * @param in
     */
    public void perform(Creature creature, Position in);
    
    public String getDescription();
    
    public void init(Creature c);
}
