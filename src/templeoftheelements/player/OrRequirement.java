
package templeoftheelements.player;

import java.util.ArrayList;

/**
 *
 * @author angle
 */


public class OrRequirement implements Requirement{
    private ArrayList<Requirement> requirements;

    public OrRequirement(Requirement... reqs) {
        requirements = new ArrayList<>();
        for (Requirement r : reqs) {
            requirements.add(r);
        }
    }
    
    @Override
    public boolean isMet() {
        for (Requirement req : requirements) {
            if (req.isMet()) return true;
        }
        return false;
    }
    
}
