
package templeoftheelements.item;

import com.samrj.devil.math.Vec2i;
import java.util.HashMap;
import stat.Stat;
import templeoftheelements.collision.Creature;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.Effect;

/**
 *
 * @author angle
 */


public class Consumable extends Item {
    
    private Effect effect;

    public Consumable(String name, Effect effect, Renderable sprite, int level) {
        this(name, effect, sprite, new Vec2i(1, 1), new HashMap<>(), level);
    }
    
    public Consumable(String name, Effect effect, Renderable sprite, Vec2i size, HashMap<String, Stat> stats, int level) {
        super(name, sprite, size, stats, level);
        this.effect = effect;
    }
    
    public void use(Creature c) {
        effect.effect(this, c);
    }
    
}
