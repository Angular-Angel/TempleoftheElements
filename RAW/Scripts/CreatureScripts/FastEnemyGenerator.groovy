import templeoftheelements.creature.CreatureGenerationProcedure;
import templeoftheelements.creature.CreatureDefinition;

/**
 *
 * @author angle
 */
class FastEnemyGenerator extends CreatureGenerationProcedure {
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script makes an enemy stronger.
        
        definition.stats.getStat("Agility").modify("Fast", 50);
        
        return definition;
    }
}

