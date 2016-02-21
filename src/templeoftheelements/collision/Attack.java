
package templeoftheelements.collision;

import java.util.ArrayList;
import stat.StatContainer;
import templeoftheelements.Actor;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.CreatureEvent;
import templeoftheelements.player.Effect;
import templeoftheelements.player.EffectContainer;

/**
 *
 * @author angle
 */


public abstract class Attack extends StatContainer implements Actor, Renderable, Collidable {
    
    private ArrayList<EffectContainer> onHitEffects;
    protected Creature origin;
    
    public Attack(Creature c) {
        onHitEffects = new ArrayList<>();
        origin = c;
        addAllStats(c.viewStats());
    }
    
    public void addOnHitEffect(Effect effect) {
        onHitEffects.add(new EffectContainer(effect, this));
    }
    
    public void addOnHitEffect(EffectContainer effect) {
        onHitEffects.add(effect);
    }
    
    @Override
    public float hit(Object o) {
        float damage = 0;
        if (o instanceof Creature) {
            origin.notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.HIT_ENEMY, o));
            for(EffectContainer e : onHitEffects) {
                damage += e.apply((Creature) o);
            }
        }
        return damage;
    } 
    
}
