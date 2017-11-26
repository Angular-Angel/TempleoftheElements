 
package templeoftheelements.item;

import stat.StatContainer;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public class EquipmentDefinition extends ItemDefinition {

    protected String type;
    protected StatContainer playerStats;

    public EquipmentDefinition(String name, String type, Renderable sprite) {
        super(name, sprite);
        this.type = type;
        playerStats = new StatContainer();
    }
    
    public EquipmentDefinition(String name, String type, Renderable sprite, int level, int rarity) {
        super(name, sprite, level, rarity);
        this.type = type;
        playerStats = new StatContainer();
    }
    
    @Override
    public Item generate() {
        Equipment ret = new Equipment(name, type, sprite, level);
        ret.stats.addAllStats(viewStats());
        return ret;
    }
    
}
