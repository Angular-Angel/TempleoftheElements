/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.creature;

/**
 *
 * @author angle
 */
public abstract class TriggeredAbility extends Ability implements CreatureListener{
    
    public TriggeredAbility(AbilityDefinition abilityDef) {
        super(abilityDef);
    }
    
}
