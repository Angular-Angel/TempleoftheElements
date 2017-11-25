
package templeoftheelements.collision;

import templeoftheelements.creature.Creature;
import java.util.HashMap;
import stat.StatContainer;
import templeoftheelements.Actor;
import templeoftheelements.display.Renderable;
import templeoftheelements.creature.CreatureEvent;
import templeoftheelements.effect.Effect;



public abstract class Attack extends StatContainer implements Actor, Renderable, Collidable {
    
    private final HashMap<String, Effect> onHitEffects;
    protected Creature origin;
    
    public Attack(Creature c) {
        onHitEffects = new HashMap<>();
        origin = c;
        addReference("Source", c.stats);
    }
    
    public void addOnHitEffect(Effect effect) {
        onHitEffects.put(effect.name, effect);
    }
    
    
    @Override
    public float hit(Object o) {
        float damage = 0;
        if (o instanceof Creature) {
            origin.notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.HIT_ENEMY, o));
            for(Effect e : onHitEffects.values()) {
                damage += e.effect(origin, o);
            }
        }
        return damage;
    } 
    
}
