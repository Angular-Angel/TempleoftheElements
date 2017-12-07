/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.player;

import java.util.ArrayList;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.Creature;

/**
 *
 * @author angle
 */
public class AbilitySkill extends Skill {
    
    public CharacterTree tree;
    public Ability ability;
    public Ability.Detail usage;
    public Ability.Detail targeting;
    public ArrayList<Ability.Detail> costDetails;
    public ArrayList<Ability.Detail> effectDetails;
    public ArrayList<Ability.Detail> scalingDetails;
    
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
        creature.addAbility(ability);
    }
    
    @Override
    public void init(Creature c) {
        super.init(c);
        ability.init(c.stats);
    }

    @Override
    public String getName() {
        return ability.getName();
    }

    @Override
    public String getDescription() {
        return ability.getDescription();
    }
    
}
