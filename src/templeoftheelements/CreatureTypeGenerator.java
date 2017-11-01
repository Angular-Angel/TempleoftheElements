
package templeoftheelements;

import generation.GenerationProcedure;
import generation.ProceduralGenerator;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author angle
 */


public class CreatureTypeGenerator implements ProceduralGenerator<CreatureDefinition> {

    Random random = new Random();
    public HashMap<CreatureDefinition.Detail, GenerationProcedure<CreatureDefinition>> procedures = new HashMap<>();
    
    public void addProcedure(CreatureDefinition.Detail detail, GenerationProcedure<CreatureDefinition> procedure) {
        procedures.put(detail, procedure);
    }
    
    @Override
    public CreatureDefinition generate() {
        CreatureDefinition ret = procedures.get(CreatureDefinition.Detail.MONSTROUS_HUMANOID).generate();
        
        for (CreatureDefinition.Detail detail : ret.details) {
            ret = procedures.get(detail).modify(ret);
        }
        
        return ret;
    }

    @Override
    public CreatureDefinition generate(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
