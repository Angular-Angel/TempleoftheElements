
import templeoftheelements.CreatureGenerationProcedure;
import java.util.Random;

/**
 *
 * @author angle
 */
class ArmouredEnemyGenerator extends CreatureGenerationProcedure {
	
    int count = 0;
    Random random = new Random();
    
    @Override
    public CreatureDefinition modify(CreatureDefinition definition) {
        
        //definition.getStat("").modifyBase(0.2);
        
        return definition;
    }
    
}

