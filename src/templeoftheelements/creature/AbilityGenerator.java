/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.creature;

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
import templeoftheelements.player.AbilitySkill;
import templeoftheelements.player.CharacterTree;

/**
 *
 * @author angle
 */
public class AbilityGenerator implements ProceduralGenerator<AbilitySkill> {

    Random random = new Random();
    HashMap<Ability.Detail, AbilityGenerationProcedure> procedures = new HashMap<>();
    
    public void addProcedure(Ability.Detail detail, AbilityGenerationProcedure procedure) {
        procedures.put(detail, procedure);
    }
    
    @Override
    public AbilitySkill generate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AbilitySkill generate(Object o) {
        CharacterTree tree = (CharacterTree) o;
        
        AbilitySkill abilitySkill = new AbilitySkill();
        
        //Important info - the skill tree this ability is for and the abilities that skill tree currently has on it.
        
        //First, pick the usage of the ability.
        
        Ability.Detail usage;
        Ability.Detail targeting;
        ArrayList<Ability.Detail> costDetails;
        ArrayList<Ability.Detail> effectDetails;
        ArrayList<Ability.Detail> scalingDetails;
        
        if (tree.spammables / tree.abilities.size() < 0.4) {
            usage = Ability.Detail.SPAMMABLE;
            abilitySkill.addStat("Cost Pool", new NumericStat(10));
            abilitySkill.addStat("Cost Complexity", new NumericStat(5 + tree.numLayers));
            tree.spammables++;
        } else {
            usage = Ability.Detail.SITUATIONAL;
            abilitySkill.addStat("Cost Pool", new NumericStat(30));
            abilitySkill.addStat("Cost Complexity", new NumericStat(10 + tree.numLayers * 2));
        }
        
        //Then pick the targeting method 
        
        ArrayList<Ability.Detail> targetingMethods = new ArrayList<>();
        targetingMethods.addAll(Arrays.asList(Ability.Detail.values()));
        targetingMethods = new ArrayList<>(targetingMethods.subList(targetingMethods.indexOf(Ability.Detail._TARGETING_) +1, targetingMethods.indexOf(Ability.Detail._COMMON_)));
        
        do {
            
            targeting = targetingMethods.get(random.nextInt(targetingMethods.size()));

            abilitySkill.targeting = targeting;
        } while (!procedures.containsKey(targeting));
        
        abilitySkill = procedures.get(targeting).modify(abilitySkill);
        
        //Then, depending on the above, pick what costs this spell will have.
        
        try {
            
            int costcomplex = (int) (abilitySkill.getScore("Complexity") * (0.2 + (0.4 * random.nextFloat())));
        
            abilitySkill.getStat("Complexity").modify(-costcomplex);
            
            abilitySkill.addStat("Cost Complexity", new NumericStat(costcomplex));
            
            int i = 0;
            while (abilitySkill.getScore("Cost Complexity") > 0) {
                Ability.Detail cost;

                do {
                    cost = tree.costDetails.get(random.nextInt(tree.costDetails.size()));
                } while (!abilitySkill.costDetails.contains(cost) && cost.cost > abilitySkill.getScore("Cost Complexity"));

                abilitySkill.costDetails.add(cost);
                abilitySkill.getStat("Cost Complexity").modify(-cost.cost);
                
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(AbilityGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Then loop through and determine all the effects the spell will have.
        
        
        try {
            
            while (abilitySkill.getScore("Complexity") > 0) {
                Ability.Detail effect;

                do {
                    effect = tree.effectDetails.get(random.nextInt(tree.effectDetails.size()));
                } while (!abilitySkill.effectDetails.contains(effect) && effect.cost > abilitySkill.getScore("Complexity"));

                abilitySkill.effectDetails.add(effect);
                abilitySkill.getStat("Complexity").modify(-effect.cost);
                
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(AbilityGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Finally, run all the scripts that will generate the spell.
        
        try {
            
            Ability.Detail cost;
            int i = 0;
            while (abilitySkill.getScore("Cost Pool") > 0) {
                
                cost = abilitySkill.costDetails.get(i++);
                
                if (i >= abilitySkill.costDetails.size())
                    i = 0;
                
                procedures.get(cost).modify(abilitySkill);
                
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(AbilityGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            
            Ability.Detail effect;
            int i = 0;
            while (abilitySkill.getScore("Pool") > 0) {
                
                effect = abilitySkill.effectDetails.get(i++);
                
                if (i >= abilitySkill.effectDetails.size())
                    i = 0;
                
                if (abilitySkill.getScore("Pool") > effect.cost) {
                    procedures.get(effect).modify(abilitySkill);
                }
                else abilitySkill.getStat("Pool").modify(-1);
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(AbilityGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return abilitySkill;
    }
    
}
