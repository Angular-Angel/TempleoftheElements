import templeoftheelements.CreatureGenerationProcedure;
import templeoftheelements.CreatureDefinition;

/**
 *
 * @author angle
 */
class StrongEnemyGenerator extends CreatureGenerationProcedure {
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script makes an enemy stronger.
        
        definition.getStat("Strength").modifyBase(50);
        
        return definition;
    }
    
}

