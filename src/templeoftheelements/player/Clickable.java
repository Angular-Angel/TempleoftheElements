 
package templeoftheelements.player;

import org.jbox2d.common.Vec2;


/**
 *
 * @author angle
 */


public interface Clickable {
    public boolean isClicked(float x, float y);
    public void mouseEvent(int button, int action, int mods);
}
