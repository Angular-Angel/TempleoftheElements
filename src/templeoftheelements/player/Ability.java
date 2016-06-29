
package templeoftheelements.player;

import templeoftheelements.collision.Creature;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;

/**
 *
 * @author angle
 */


public interface Ability {
    
    public String getName();
    
    public String getDescription();
    
    public Ability copy();
    
    public AbilityDefinition getDef();
    
    public void setDef(AbilityDefinition def);
    
    public void init(Creature c);
    
}
