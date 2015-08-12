
package templeoftheelements.player;

/**
 *
 * @author angle
 */


public class CreatureEvent {
    
    public static enum Type {
        TOOK_DAMAGE, HEALED, TOOK_HIT, DEALT_DAMAGE, ATTACKED, USED_SPELL, 
        TOOK_ACTION, MOVED, LOST_LIFE, DIED, COLLIDED, ADDED_STATUS_EFFECT, 
        LOST_STATUS_EFFECT, HIT_ENEMY;
    }
    
    public final float quantity;
    public final Type type;
    public final Object thing;
    
    public CreatureEvent(Type type) {
        this(type, 0, null);
    }
    
    public CreatureEvent(Type type, float quantity) {
        this(type, quantity, null);
    }
    
    public CreatureEvent(Type type, Object thing) {
        this(type, 0, thing);
    }
    
    public CreatureEvent(Type type, float quantity, Object thing) {
        this.quantity = quantity;
        this.type = type;
        this.thing = thing;
    }
    
}
