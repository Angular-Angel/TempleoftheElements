
package templeoftheelements.controller;

import templeoftheelements.creature.Ability;
import stat.StatContainer;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public abstract class Action extends Ability {

    private int cooldown;
    public Creature creature;
    
    public Action(String name, StatContainer stats) {
        super(name, true, stats);
        cooldown = 0;
    }
    
    public void step() {
        if (cooldown > 0) cooldown--;
    }
    
    public boolean isPossible(Creature c) {
        return (cooldown == 0);
    }
    
    public abstract Renderable getSprite(); 
    
    /**
     *
     * @param in
     */
    public abstract void perform(Creature creature, Position in);

    /**
     * @return the cooldown
     */
    public int getCooldown() {
        return cooldown;
    }

    /**
     * @param cooldown the cooldown to set
     */
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    
    public abstract Ability copy();
    
    @Override
    public void init(Creature c) {
        creature = c;
        addReference("Source", c);
        this.active = true;
    }
}