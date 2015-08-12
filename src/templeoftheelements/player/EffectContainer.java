
package templeoftheelements.player;

import templeoftheelements.collision.Collidable;

/**
 *
 * @author angle
 */


public class EffectContainer {
    private Effect effect;
    private EffectSource origin;
    
    public EffectContainer(Effect effect, EffectSource source) {
        this.effect = effect;
        origin = source;
    }
    
    public float apply(Collidable c) {
        return effect.effect(origin, c);
    }
}
