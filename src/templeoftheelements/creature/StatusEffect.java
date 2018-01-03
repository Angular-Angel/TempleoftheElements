
package templeoftheelements.creature;

import java.util.logging.Level;
import java.util.logging.Logger;
import stat.NoSuchStatException;
import stat.NumericStat;
import stat.Stat;
import stat.StatContainer;
import templeoftheelements.Actor;

/**
 *
 * @author angle
 */

public abstract class StatusEffect implements Actor {
    
    public final String name;
    public Creature creature;
    public int severity;
    public StatContainer stats;
    
    public StatusEffect(String name) {
        this.name = name;
        stats = new StatContainer();
    }
    
    
    public void updateStat(String name, Stat stat) {
        if (stats.hasStat(name)) {
            creature.stats.getStat(name).removeMod(this.name);
            stats.removeStat(name);
        }

        stats.addStat(name, stat);
        creature.stats.getStat(name).modify(this.name, new NumericStat(stats.getScore(name)));
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
    
    @Override
    public abstract StatusEffect clone();
}
