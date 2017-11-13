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
public class Skill extends StatContainer {
    private Player player;
    private int level;
    private String name;
    
    public void beAcquired(Creature creature) {
        creature.addAllStats(this);
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return "Nothing to see here.";
    }
    
}
