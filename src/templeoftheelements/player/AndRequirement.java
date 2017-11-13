
package templeoftheelements.player;

import java.util.ArrayList;

/**
 *
 * @author angle
 */


public class AndRequirement implements Requirement{
    private ArrayList<Requirement> requirements;
    
    public AndRequirement(Requirement... reqs) {
        requirements = new ArrayList<>();
        for (Requirement r : reqs) {
            requirements.add(r);
        }
    }
    
    @Override
    public boolean isMet() {
        for (Requirement req : requirements) {
            if (!req.isMet()) return false;
        }
        return true;
    }

    @Override
    public void draw(Requirement req) {
        
    }

    @Override
    public boolean isNode() {
        return false;
    }
    
}
