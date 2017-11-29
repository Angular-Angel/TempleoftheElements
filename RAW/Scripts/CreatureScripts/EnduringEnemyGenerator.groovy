import templeoftheelements.creature.CreatureGenerationProcedure;
import templeoftheelements.creature.CreatureDefinition;

/**
 *
 * @author angle
 */
class EnduringEnemyGenerator extends CreatureGenerationProcedure {
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script makes an enemy stronger.
        
        definition.stats.getStat("Max Stamina").modifyBase(50);
        
        return definition;
    }
    
}

