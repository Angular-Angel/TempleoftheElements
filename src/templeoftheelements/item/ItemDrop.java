
package templeoftheelements.item;

/**
 *
 * @author angle
 */


public class ItemDrop {
    private final ItemGenerator pool;
    private final int level, variance;
    
    public ItemDrop(ItemGenerator pool, int level, int variance) {
        this.pool = pool;
        this.level = level;
        this.variance = variance;
    }
    
    public Item getItem() {
        return pool.generate(level, variance);
    }
}
