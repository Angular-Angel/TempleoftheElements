/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.creature;

import templeoftheelements.creature.Ability;
import templeoftheelements.creature.CreatureListener;
import stat.StatContainer;

/**
 *
 * @author angle
 */
public abstract class TriggeredAbility extends Ability implements CreatureListener {
    
    public TriggeredAbility() {
        super("Triggered Ability", false);
    }
    
    public TriggeredAbility(String name, boolean active) {
        super(name, active);
    }
    
    public TriggeredAbility(String name, boolean active, StatContainer stats) {
        super(name, active, stats);
    }
    
}
