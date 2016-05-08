/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.player;

import generation.GenerationProcedure;
import generation.ProceduralGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import stat.NoSuchStatException;
import stat.NumericStat;
import templeoftheelements.Spell;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;
import templeoftheelements.player.CharacterWheel.CharacterTree;

/**
 *
 * @author angle
 */
public class AbilityGenerator implements ProceduralGenerator<Ability> {

    Random random = new Random();
    HashMap<Spell.Detail, GenerationProcedure<Ability>> procedures = new HashMap<>();
    
    @Override
    public Ability generate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Ability generate(Object o) {
        CharacterTree tree = (CharacterTree) o;
        
        Ability ability = null;
        
        AbilityDefinition abilityDef = new AbilityDefinition();
        
        abilityDef.ability = ability;
        
        abilityDef.addStat("Pool", new NumericStat(100));
        abilityDef.addStat("Complexity", new NumericStat( +tree.layers.size()));
        
        abilityDef.tree = tree;
        
        //Important info - the skill tree this ability is for and the abilities that skill tree currently has on it.
        
        //First, pick the usage of the ability.
        int abilities = tree.abilities.size();
        
        Spell.Detail usage;
        
        if (tree.spammables / abilities < 0.4) {
            usage = Spell.Detail.SPAMMABLE;
            abilityDef.details.add(usage);
            abilityDef.addStat("Cost Pool", new NumericStat(10));
            abilityDef.details.add(usage);
            tree.spammables++;
        } else {
            usage = Spell.Detail.SITUATIONAL;
            abilityDef.details.add(usage);
            abilityDef.addStat("Cost Pool", new NumericStat(30));
            abilityDef.addStat("Cost Complexity", new NumericStat(5));
        }
        
        //Then pick the tergeting method 
        
        Spell.Detail targeting;
        
        ArrayList<Spell.Detail> values = new ArrayList<>();
        values.addAll(Arrays.asList(Spell.Detail.values()));
        values = new ArrayList<>(values.subList(values.indexOf(Spell.Detail._TARGETING_) +1, values.indexOf(Spell.Detail._COMMON_)));
        targeting = values.get(random.nextInt(values.size()));
        
        abilityDef.details.add(targeting);
        
        ability = procedures.get(targeting).generate(abilityDef);
        
        //Then, depending on the above, pick what costs this spell will have.
        
        try {
            
            int costcomplex = random.nextInt((int) (abilityDef.getScore("Complexity") * 0.7));
        
            abilityDef.getStat("Complexity").modify(-costcomplex);
            
            abilityDef.addStat("Cost Complexity", new NumericStat(costcomplex));
            
            while (abilityDef.getScore("Cost Pool") > 0) {
                if (abilityDef.getScore("Cost Complexity") > 0) {
                    addNewCost(abilityDef);
                }
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(AbilityGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Then loop through and determine all the effects the spell will have.
        
        return ability;
    }
    
    public boolean addNewCost(AbilityDefinition ability) {
        
        try {
            Spell.Detail cost;
            
            do {
                cost = ability.tree.definition.costDetails.get(random.nextInt(ability.tree.definition.costDetails.size()));
            } while (cost.cost > ability.getScore("Cost Complexity"));
            
            ability.details.add(cost);
            
            
            
            procedures.get(cost).modify(ability.ability);
                    
        } catch (NoSuchStatException ex) {
            Logger.getLogger(AbilityGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
}
