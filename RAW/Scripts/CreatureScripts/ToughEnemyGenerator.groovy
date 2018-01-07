import templeoftheelements.creature.CreatureGenerationProcedure;
import templeoftheelements.creature.CreatureDefinition;

/**
 *
 * @author angle
 */
class ToughEnemyGenerator extends CreatureGenerationProcedure {
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script makes an enemy stronger.
        
        definition.stats.getStat("Endurance").modify("Tough", 50);
        
        return definition;
    }
    
}

