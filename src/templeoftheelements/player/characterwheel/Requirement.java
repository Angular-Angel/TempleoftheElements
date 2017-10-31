
package templeoftheelements.player.characterwheel;

import java.util.ArrayList;

/**
 *
 * @author angle
 */


public interface Requirement {
    public boolean isMet();
    public ArrayList<Requirement> getReqs();
    public void draw(Requirement req);
}
