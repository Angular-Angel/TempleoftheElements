
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

    @Override
    public ArrayList<Requirement> getReqs() {
        ArrayList<Requirement> ret = new ArrayList<>();
        ret.add(requirements);
        return ret;
    }

    @Override
    public void draw(Requirement req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
