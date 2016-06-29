
package templeoftheelements.effect;

import stat.StatContainer;



public abstract class Effect extends StatContainer{
    
    public final String name;
    
    public abstract float effect(EffectSource source, Object o);
    
    public abstract String getDescription();
    
    public Effect(String name, boolean bool) {
        super(bool);
        this.name = name;
    }
    
    public Effect(String name) {
        super();
        this.name = name;
    }
    
}
