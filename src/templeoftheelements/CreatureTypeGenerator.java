
package templeoftheelements;

import generation.GenerationProcedure;
import generation.ProceduralGenerator;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author angle
 */


public class CreatureTypeGenerator implements ProceduralGenerator<CreatureDefinition> {

    Random random = new Random();
    ArrayList<GenerationProcedure<CreatureDefinition>> baseProcedures = new ArrayList<>();
    
    @Override
    public CreatureDefinition generate() {
        CreatureDefinition ret = baseProcedures.get(random.nextInt(baseProcedures.size())).generate();
        
        return ret;
    }
    
    public void addBaseProcedure(GenerationProcedure<CreatureDefinition> procedure) {
        baseProcedures.add(procedure);
    }

    @Override
    public CreatureDefinition generate(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
