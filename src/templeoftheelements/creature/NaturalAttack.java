/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.creature;

import templeoftheelements.controller.AttackAction;
import templeoftheelements.item.AttackDefinition;

/**
 *
 * @author angle
 */
public class NaturalAttack extends Ability {
    
    public AttackDefinition attack;
    
    public NaturalAttack(AttackDefinition attack) {
        this.attack = attack;
    }

    @Override
    public String getDescription() {
        return attack.getDescription();
    }

    @Override
    public void init(Creature c) {
        attack.init(c);
        c.addAction(new AttackAction(attack));
    }

    @Override
    public void deInit(Creature c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
