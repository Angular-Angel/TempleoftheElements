 
package templeoftheelements.item;

import com.samrj.devil.math.Vec2i;
import java.util.ArrayList;
import java.util.HashMap;
import stat.Stat;
import templeoftheelements.display.Renderable;
import templeoftheelements.effect.Effect;

/**
 *
 * @author angle
 */


public class Weapon extends Equipment {

    private ArrayList<AttackDefinition> attacks;
    
    public Weapon(String name, Renderable sprite, int level) {
        super(name, "Weapon", sprite, level);
        attacks = new ArrayList<>();
    }
    
    
    public Weapon(AttackDefinition attack, String name, Renderable sprite, int level) {
        this(attack, name, sprite, new HashMap<>(), level);
    }
    
    public Weapon(AttackDefinition attack, String name, Renderable sprite, HashMap<String, Stat> stats, int level) {
        super(name, "Weapon", sprite, new Vec2i(1, 1), stats, level);
        attacks = new ArrayList<>();
        attacks.add(attack);
    }
    
    public void addAttack(AttackDefinition attack) {
        attacks.add(attack);
    }

    /**
     * @return the attack
     */
    public ArrayList<AttackDefinition> getAttacks() {
        return attacks;
    }
    
    public void addOnHitEffect(Effect e) {
        for (AttackDefinition a : attacks) a.addEffect(e);
    }
    
    
    
}
