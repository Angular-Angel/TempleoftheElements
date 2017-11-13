
package templeoftheelements.player;

import java.util.ArrayList;

/**
 *
 * @author angle
 */


public class SwitchRequirement implements Requirement{
    private Requirement requirement;
    private ArrayList<CharacterNode> branches;

    public SwitchRequirement(Requirement requirement, CharacterNode... branches) {
        this.requirement = requirement;
        this.branches = new ArrayList<>();
        for (CharacterNode node : branches) {
            this.branches.add(node);
        }
    }

    @Override
    public boolean isMet() {
        if (!requirement.isMet()) return false;
        for (CharacterNode node : branches) {
            if (node.isAcquired()) return false;
        }
        return true;
    }

    @Override
    public void draw(Requirement req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isNode() {
        return false;
    }
    
}
