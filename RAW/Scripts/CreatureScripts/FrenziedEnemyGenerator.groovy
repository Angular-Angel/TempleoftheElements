import templeoftheelements.CreatureGenerationProcedure;
import templeoftheelements.CreatureDefinition;

/**
 *
 * @author angle
 */
class FrenziedEnemyGenerator extends CreatureGenerationProcedure {
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script makes an enemy stronger.
        
        definition.getStat("Attack Speed Multiplier").modifyBase(0.2);
        
        return definition;
    }
    
}

