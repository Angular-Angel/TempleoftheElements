
package templeoftheelements.player;

import java.util.ArrayList;

/**
 *
 * @author angle
 */


public class SwitchRequirement implements Requirement{
    private Requirement requirements;
    private ArrayList<CharacterNode> branches;

    public SwitchRequirement(Requirement... reqs) {
        requirements = new AndRequirement(reqs);
        branches = new ArrayList<>();
    }

    @Override
    public boolean isMet() {
        if (!requirements.isMet()) return false;
        for (CharacterNode node : branches) {
            if (node.isAcquired()) return false;
        }
        return true;
    }
    
}
