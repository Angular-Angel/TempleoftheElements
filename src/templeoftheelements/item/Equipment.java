
package templeoftheelements.item;

import com.samrj.devil.math.Vec2i;
import java.util.HashMap;
import stat.Stat;
import stat.StatContainer;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public class Equipment extends Item {
    
    private String type;
    public StatContainer playerStats;
    
    public Equipment(String name, String type, Renderable sprite, int level) {
        this(name, type, sprite, new Vec2i(1,1), new HashMap<>(), level);
    }

    public Equipment(String name, String type, Renderable sprite, Vec2i size, HashMap<String, Stat> stats, int level) {
        super(name, sprite, size, stats, level);
        this.type = type;
        playerStats = new StatContainer();
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    
}
