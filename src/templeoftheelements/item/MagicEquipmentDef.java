
package templeoftheelements.item;

import stat.StatContainer;

/**
 *
 * @author angle
 */


public class MagicEquipmentDef extends MagicItemDef {
    public StatContainer playerBonuses;

    public MagicEquipmentDef(String name, int level, int rarity) {
        super(name, level, rarity);
        playerBonuses = new StatContainer();
    }
    
    public void apply(Equipment e) {
        super.apply(e);
        e.playerStats.addAllStats(playerBonuses);
    }
}
