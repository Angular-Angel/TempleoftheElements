
package templeoftheelements.effect;

import java.util.Collection;



public interface EffectContainer {
    
    public void addEffect(Effect effect);
    
    public void addAllEffects(EffectContainer effects);
    
    public boolean containsEffect(String s);
    
    public Effect getEffect(String s);
    
    public Collection<Effect> getAllEffects();
    
}
