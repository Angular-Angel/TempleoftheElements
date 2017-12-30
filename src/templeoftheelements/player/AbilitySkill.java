/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.player;

import java.util.ArrayList;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.AbilityDefinition;
import templeoftheelements.creature.Creature;

/**
 *
 * @author angle
 */
public class AbilitySkill extends Skill {
    
    public CharacterTree tree;
    public AbilityDefinition abilityDef;
    public Ability.Detail usage;
    public Ability.Detail targeting;
    public ArrayList<Ability.Detail> costDetails;
    public ArrayList<Ability.Detail> effectDetails;
    public ArrayList<Ability.Detail> scalingDetails;
    public Ability displayAbility;
    
    public AbilitySkill(CharacterTree tree) {
        super();
        this.tree = tree;
        costDetails = new ArrayList<>();
        effectDetails = new ArrayList<>();
        scalingDetails =  new ArrayList<>();
    }
    
    @Override
    public void beAcquired(Creature creature) {
        super.beAcquired(creature);
        creature.addAbility(abilityDef.getAbility());
    }
    
    @Override
    public void init(Creature c) {
        super.init(c);
        displayAbility = abilityDef.getAbility();
        displayAbility.init(c);
    }

    @Override
    public String getName() {
        return abilityDef.name;
    }

    @Override
    public String getDescription() {
        return abilityDef.getDescription();
    }
    
}
