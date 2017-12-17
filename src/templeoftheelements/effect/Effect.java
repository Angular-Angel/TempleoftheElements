
package templeoftheelements.effect;

import stat.StatContainer;



public abstract class Effect {
    
    public final String name;
    
    public StatContainer stats =  new StatContainer();
    
    public abstract float effect(EffectSource source, Object o);
    
    public abstract String getDescription();
    
    public Effect(String name, boolean bool) {
        stats = new StatContainer(bool);
        this.name = name;
    }
    
    public Effect(String name) {
        super();
        this.name = name;
    }
    
}
