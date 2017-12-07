/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.player;

import stat.StatContainer;
import templeoftheelements.creature.Creature;

/**
 *
 * @author angle
 */
public abstract class Skill {
    private Player player;
    private int level;
    public StatContainer stats;
    
    public Skill() {
        stats = new StatContainer(false);
    }
    
    public void init(Creature c) {
        stats.init(c.stats);
    }
    
    public void beAcquired(Creature creature) {
        creature.stats.addAllStats(this.stats);
    }
    
    public abstract String getName();
    
    public abstract String getDescription();
    
}
