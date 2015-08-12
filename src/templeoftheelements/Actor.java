 
package templeoftheelements;

/**
 *
 * @author angle
 */


public interface Actor extends Destroyable {
    public void step(float dt);
    public boolean isEnemy();
}
