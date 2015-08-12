
package templeoftheelements.item;

import stat.StatContainer;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public abstract class ItemDefinition extends StatContainer {
    
    protected String name;
    protected Renderable sprite;
    protected int level;
    private int rarity;
    
    public ItemDefinition(String name, Renderable sprite) {
        this(name, sprite, 1, 1);
    }
        
        
    public ItemDefinition(String name, Renderable sprite, int level, int rarity) {
        this.name = name;
        this.sprite = sprite; 
        this.level = level;
        this.rarity = rarity;
    }
     
    public abstract Item generate();

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
