import templeoftheelements.CreatureGenerationProcedure;
import templeoftheelements.CreatureDefinition;

/**
 *
 * @author angle
 */
class FastEnemyGenerator extends CreatureGenerationProcedure {
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script makes an enemy stronger.
        
        definition.getStat("Strength").modifyBase(50);
        
        return definition;
    }
}

