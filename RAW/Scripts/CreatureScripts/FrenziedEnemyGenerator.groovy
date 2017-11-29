import templeoftheelements.creature.CreatureGenerationProcedure;
import templeoftheelements.creature.CreatureDefinition;

/**
 *
 * @author angle
 */
class FrenziedEnemyGenerator extends CreatureGenerationProcedure {
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script makes an enemy stronger.
        
        definition.stats.getStat("Attack Speed").modifyBase(0.2);
        
        return definition;
    }
    
}

