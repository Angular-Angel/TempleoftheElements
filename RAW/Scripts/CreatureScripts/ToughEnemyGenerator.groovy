import templeoftheelements.CreatureGenerationProcedure;
import templeoftheelements.CreatureDefinition;

/**
 *
 * @author angle
 */
class ToughEnemyGenerator extends CreatureGenerationProcedure {
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script makes an enemy stronger.
        
        definition.getStat("Constitution").modifyBase(50);
        
        return definition;
    }
    
}

