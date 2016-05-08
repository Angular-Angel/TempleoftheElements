
package templeoftheelements.item;

import java.util.ArrayList;
import templeoftheelements.effect.Effect;

/**
 *
 * @author angle
 */


public class MagicWeaponDef extends MagicEquipmentDef {
    
    public ArrayList<Effect> onHitEffects;

    public MagicWeaponDef(String name, int level, int rarity) {
        super(name, level, rarity);
        onHitEffects =  new ArrayList<>();
    }
    
    public void apply(Weapon w) {
        super.apply(w);
        for (Effect e : onHitEffects) w.addOnHitEffect(e);
    }
    
}
