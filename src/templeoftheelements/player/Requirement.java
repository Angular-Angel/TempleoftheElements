
package templeoftheelements.player;

import java.util.ArrayList;

/**
 *
 * @author angle
 */


public interface Requirement {
    public boolean isMet();
    public ArrayList<CharacterNode> getNodes();
    public void draw(Requirement req);
    
}
