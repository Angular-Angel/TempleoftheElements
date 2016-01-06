
package templeoftheelements.player;

import java.util.logging.Level;
import java.util.logging.Logger;
import stat.NoSuchStatException;
import stat.Stat;
import stat.StatContainer;
import templeoftheelements.Actor;
import templeoftheelements.collision.Creature;

/**
 *
 * @author angle
 */

public abstract class StatusEffect extends StatContainer implements Actor {
    
    public final String name;
    public Creature creature;
    public int severity;
    
    public StatusEffect(String name) {
        this.name = name;
    }
    
    
    public void updateStat(String name, Stat stat) {
        try {
            if (hasStat(name)) {
                creature.getStat(name).modify(-getScore(name));
                removeStat(name);
                addStat(name, stat);
                creature.getStat(name).modify(getScore(name));
            }
            else addStat(name, stat);
            
        } catch (NoSuchStatException ex) {
            Logger.getLogger(StatusEffect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public abstract void init(Creature c);
    
    public abstract void update(StatusEffect effect);

    @Override
    public abstract void step(float dt);

    @Override
    public abstract boolean isEnemy();

    @Override
    public abstract boolean isDead();

    @Override
    public abstract void destroy();
    
    public abstract StatusEffect clone();
}
