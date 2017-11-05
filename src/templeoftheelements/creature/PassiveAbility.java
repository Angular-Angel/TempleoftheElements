
package templeoftheelements.creature;

import templeoftheelements.creature.Ability;
import stat.StatContainer;

/**
 *
 * @author angle
 */


public abstract class PassiveAbility extends Ability {

    public PassiveAbility() {
        super("PassiveAbility");
    }
    
    public PassiveAbility(String name) {
        super(name);
    }
    
    public PassiveAbility(String name, boolean active) {
        super(name, active);
    }
    
    public PassiveAbility(String name, boolean active, StatContainer stats) {
        super(name, active, stats);
    }
    
    public abstract void step(float dt);
}
