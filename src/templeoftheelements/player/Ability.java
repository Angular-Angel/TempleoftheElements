
package templeoftheelements.player;

import stat.StatContainer;
import stat.Trait;
import templeoftheelements.collision.Creature;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;

/**
 *
 * @author angle
 */


public abstract class Ability  extends Trait {
    
    public Ability() {
        super("Ability", true);
    }
    
    public Ability(String name) {
        super(name, true);
    }
    
    public Ability(String name, boolean active) {
        super(name, active);
    }
    
    public Ability(String name, boolean active, StatContainer stats) {
        super(name, active, stats);
    }
    
    public abstract String getDescription();
    
    public abstract Ability copy();
    
    public abstract AbilityDefinition getDef();
    
    public abstract void setDef(AbilityDefinition def);
    
    public abstract void init(Creature c);
    
}
