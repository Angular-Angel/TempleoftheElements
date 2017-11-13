
package templeoftheelements.player;

import java.util.ArrayList;

/**
 *
 * @author angle
 */


public interface Requirement {
    public boolean isMet();
    public boolean isNode();
    public void draw(Requirement req);
}
