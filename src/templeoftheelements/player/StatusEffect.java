
package templeoftheelements.player;

import java.util.logging.Level;
import java.util.logging.Logger;
import stat.NoSuchStatException;
import stat.Stat;
import stat.StatContainer;
import templeoftheelements.collision.Creature;

/**
 *
 * @author angle
 */

public class StatusEffect extends StatContainer {
    
    public final String name;
    public Creature creature;
    
    public StatusEffect(String name) {
        this.name = name;
    }
    
    public void updateStat(String name, float score) {
        try {
            if (hasStat(name)) {
                creature.getStat(name).modifyBase(-getScore(name));
                getStat(name).set(score);
                creature.getStat(name).modifyBase(getScore(name));
            }
            
        } catch (NoSuchStatException ex) {
            Logger.getLogger(StatusEffect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
