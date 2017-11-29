
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


public class AttackAction extends Action {

    
    private AttackDefinition attack;
    public String description;
    
    public AttackAction(AttackDefinition attack) {
        super(attack.getName(), new StatContainer());
        this.attack = attack;
    }
    
    public AttackAction(String name, AttackDefinition attack) {
        super(name, new StatContainer());
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
    public Ability copy() {
        return new AttackAction(this.getName(), attack);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void init(StatContainer c) {
        attack.init(c);
    }
    
}
