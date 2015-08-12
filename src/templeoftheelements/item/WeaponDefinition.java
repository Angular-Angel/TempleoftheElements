 
package templeoftheelements.item;

import com.samrj.devil.util.IdentitySet;
import java.util.ArrayList;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public class WeaponDefinition extends ItemDefinition {

    private IdentitySet<AttackDefinition> attacks;
    
    public WeaponDefinition(String name, Renderable sprite) {
        super(name, sprite);
        attacks = new IdentitySet<>();
    }
    
    public WeaponDefinition(String name, Renderable sprite, int level, int rarity) {
        super(name, sprite, level, rarity);
        attacks = new IdentitySet<>();
    }
    
    public void addAttack(AttackDefinition a) {
        attacks.add(a);
    }
    
    @Override
    public Weapon generate() {
        Weapon ret = new Weapon(name, sprite, level);
        ret.addAllStats(viewStats());
        for (AttackDefinition a : attacks) ret.addAttack(a);
        return ret;
    }
    
}
