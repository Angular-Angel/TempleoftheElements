 
package templeoftheelements.item;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public class WeaponDefinition extends ItemDefinition {

    private Set<AttackDefinition> attacks;
    
    public WeaponDefinition(String name, Renderable sprite) {
        super(name, sprite);
        attacks = Collections.newSetFromMap(new IdentityHashMap<>());
    }
    
    public WeaponDefinition(String name, Renderable sprite, int level, int rarity) {
        super(name, sprite, level, rarity);
        attacks = Collections.newSetFromMap(new IdentityHashMap<>());
    }
    
    public void addAttack(AttackDefinition a) {
        attacks.add(a);
    }
    
    @Override
    public Weapon generate() {
        Weapon ret = new Weapon(name, sprite, level);
        ret.stats.addAllStats(stats.viewStats());
        for (AttackDefinition a : attacks) ret.addAttack(a);
        return ret;
    }
    
}
