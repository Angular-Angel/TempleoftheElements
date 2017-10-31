import templeoftheelements.CreatureGenerationProcedure;
import templeoftheelements.CreatureDefinition;

/**
 *
 * @author angle
 */
class EnduringEnemyGenerator extends CreatureGenerationProcedure {
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script makes an enemy stronger.
        
        definition.getStat("Max Stamina").modifyBase(50);
        
        return definition;
    }
    
}

