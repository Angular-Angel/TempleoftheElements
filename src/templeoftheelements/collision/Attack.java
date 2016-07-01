
package templeoftheelements.collision;

import java.util.ArrayList;
import java.util.HashMap;
import stat.StatContainer;
import templeoftheelements.Actor;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.CreatureEvent;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectContainer;

/**
 *
 * @author angle
 */


public abstract class Attack extends StatContainer implements Actor, Renderable, Collidable {
    
    private HashMap<String, Effect> onHitEffects;
    protected Creature origin;
    
    public Attack(Creature c) {
        onHitEffects = new HashMap<>();
        origin = c;
        addReference("Source", c);
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
