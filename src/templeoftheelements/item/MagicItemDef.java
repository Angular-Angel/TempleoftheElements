
package templeoftheelements.item;

import stat.StatContainer;

/**
 *
 * @author angle
 */


public class MagicItemDef {
    
    public String name;
    public StatContainer bonuses;
    private int level;
    private int rarity;
    
    public MagicItemDef(String name, int level, int rarity) {
        this.name = name;
        bonuses = new StatContainer();
        this.level = level;
        this.rarity = rarity;
    }
    
    public void apply(Item i) {
        i.stats.addAllStats(bonuses);
        i.setName(name + " " + i.getName());
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the rarity
     */
    public int getRarity() {
        return rarity;
    }
}
