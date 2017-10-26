
package templeoftheelements.collision;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import templeoftheelements.effect.EffectSource;

/**
 *
 * @author angle
 */


public interface Collidable extends Positionable, EffectSource {
    
    public Body getBody();
    public void collisionLogic(Object o);
    public void createBody();
    public void createBody(float x, float y);
    public void createBody(int x, int y);
    public void createBody(Position position);
    public boolean isImpassable();
    public boolean isDamaging();
    public float hit(Object o);
}
