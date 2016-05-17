
package templeoftheelements.player;

import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;

/**
 *
 * @author angle
 */


public interface Ability {
    
    public String getName();
    
    public String getDescription();
    
    public Ability clone();
    
    public AbilityDefinition getDef();
    
    public void setDef(AbilityDefinition def);
    
}
