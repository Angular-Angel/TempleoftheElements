/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.creature;

import java.util.Collection;
import templeoftheelements.controller.Action;
import templeoftheelements.controller.AttackAction;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectContainer;
import templeoftheelements.item.AttackDefinition;

/**
 *
 * @author angle
 */
public class NaturalAttack extends AbilityDefinition {
    
    public AttackDefinition attack;
    
    public NaturalAttack(AttackDefinition attack) {
        super(attack.getName());
        this.attack = attack;
    }

    @Override
    public String getDescription() {
        return attack.getDescription();
    }
    
    @Override
    public Ability getAbility() {
        return new Ability(this) {
            
            AttackDefinition naturalAttack = attack.copy();
            Action action = new AttackAction(naturalAttack);
            
            @Override
            public void init(Creature c) {
                initValues(c);
                c.addAction(action);
            }

            @Override
            public void deInit(Creature c) {
            c.removeAction(action);}

            @Override
            public void initValues(Creature c) {
                naturalAttack.initValues(c);
            }
            
        };
    }

    @Override
    public void addAllEffects(EffectContainer effects) {
        attack.addAllEffects(effects);
    }

    @Override
    public Collection<Effect> getAllEffects() {
        return attack.getAllEffects();
    }
    
}
