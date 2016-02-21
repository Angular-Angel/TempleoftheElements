
package templeoftheelements.player;

import java.util.HashMap;
import org.jbox2d.common.Vec2;
import stat.Stat;
import stat.StatContainer;
import stat.Trait;
import templeoftheelements.collision.Creature;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public abstract class Action extends Trait implements Ability {

    private int cooldown;
    
    public Action(String name, StatContainer stats) {
        super(name, true, stats);
        cooldown = 0;
    }
    
    public void step() {
        if (cooldown > 0) cooldown--;
    }
    
    public boolean isPossible(Creature c) {
        return (cooldown == 0);
    }
    
    public abstract Renderable getSprite(); 
    
    /**
     *
     * @param in
     */
    public abstract void perform(Creature creature, Vec2 in);

    /**
     * @return the cooldown
     */
    public int getCooldown() {
        return cooldown;
    }

    /**
     * @param cooldown the cooldown to set
     */
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    
    public abstract Ability clone();
    
}
