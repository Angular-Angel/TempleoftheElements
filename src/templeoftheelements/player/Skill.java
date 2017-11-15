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
public abstract class Skill extends StatContainer {
    private Player player;
    private int level;
    
    
    public void beAcquired(Creature creature) {
        creature.addAllStats(this);
    }
    
    public abstract String getName();
    
    public abstract String getDescription();
    
}
