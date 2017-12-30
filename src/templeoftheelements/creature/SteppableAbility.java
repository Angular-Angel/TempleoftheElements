/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.creature;

import templeoftheelements.Steppable;

/**
 *
 * @author angle
 */
public abstract class SteppableAbility extends Ability implements Steppable{
    
    public SteppableAbility(AbilityDefinition abilityDef) {
        super(abilityDef);
    }
    
}
