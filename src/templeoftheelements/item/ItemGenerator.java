 
package templeoftheelements.item;

/**
 *
 * @author angle
 */


public interface ItemGenerator {
    
    public void init();
    public Item generate(int level, int variance);
}
