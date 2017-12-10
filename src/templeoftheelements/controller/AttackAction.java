
package templeoftheelements.controller;

import templeoftheelements.creature.Ability;
import stat.StatContainer;
import templeoftheelements.item.AttackDefinition;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public class AttackAction implements Action {

    
    private AttackDefinition attack;
    private String description;
    
    public AttackAction(AttackDefinition attack) {
        this.attack = attack;
    }
    
    @Override
    public void perform(Creature creature, Position in) {
        creature.attack(attack);
    }

    @Override
    public Renderable getSprite() {
        return attack.getSprite();
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void init(Creature c) {
        attack.init(c);
    }

    @Override
    public boolean isPossible(Creature c) {
        return true;
    }

    /**
     * @return the name
     */
    public String getName() {
        return attack.getName();
    }
    
}
