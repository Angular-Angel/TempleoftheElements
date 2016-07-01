
package templeoftheelements.effect;

import templeoftheelements.collision.Collidable;

/**
 *
 * @author angle
 */


public interface EffectContainer {
    
    public void addEffect(Effect effect);
    
    public boolean containsEffect(String s);
    
    public Effect getEffect(String s);
    
}
