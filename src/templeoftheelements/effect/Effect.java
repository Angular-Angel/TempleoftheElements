
package templeoftheelements.effect;

import stat.StatContainer;



public abstract class Effect extends StatContainer{
    
    public abstract float effect(EffectSource source, Object o);
    
    public Effect(boolean bool) {
        super(bool);
    }
    
    public Effect() {
        super();
    }
    
}
