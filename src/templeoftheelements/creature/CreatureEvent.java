
package templeoftheelements.creature;

/**
 *
 * @author angle
 */


public class CreatureEvent {
    
    public static enum Type {
        TOOK_HIT, TOOK_DAMAGE, SPENT_HP, LOST_HP, GAINED_HP, 
        TOOK_ACTION, USED_SPELL, ATTACKED, HIT_ENEMY, DEALT_DAMAGE, 
        SPENT_STAMINA, LOST_STAMINA, GAINED_STAMINA,
        MOVED, COLLIDED, 
        ADDED_STATUS_EFFECT, LOST_STATUS_EFFECT, DIED;
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
