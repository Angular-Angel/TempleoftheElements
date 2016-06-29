
package templeoftheelements.effect;

import templeoftheelements.collision.Collidable;

/**
 *
 * @author angle
 */


public class EffectContainer {
    public final Effect effect;
    public final EffectSource origin;
    
    public EffectContainer(Effect effect, EffectSource source) {
        this.effect = effect;
        origin = source;
    }
    
    public float apply(Collidable c) {
        return effect.effect(origin, c);
    }
}
