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
        
        AbilitySkill abilitySkill = new AbilitySkill(tree);
        
        //Important info - the skill tree this ability is for and the abilities that skill tree currently has on it.
        
        //First, pick the usage of the ability.
        
        abilitySkill.addStat("Pool", new NumericStat(100)); 
        abilitySkill.addStat("Complexity", new NumericStat(5 + tree.layers.size())); 
        
        if (tree.spammables == 0 || tree.spammables / tree.abilities < 0.4) {
            abilitySkill.usage = Ability.Detail.SPAMMABLE;
            abilitySkill.addStat("Cost Pool", new NumericStat(10));
            abilitySkill.addStat("Cost Complexity", new NumericStat(5 + tree.layers.size()));
            tree.spammables++;
            tree.abilities++;
        } else {
            abilitySkill.usage = Ability.Detail.SITUATIONAL;
            abilitySkill.addStat("Cost Pool", new NumericStat(30));
            abilitySkill.addStat("Cost Complexity", new NumericStat(10 + tree.layers.size() * 2));
            tree.situationals++;
            tree.abilities++;
        }
        
        //Then pick the targeting method 
        
        ArrayList<Ability.Detail> targetingMethods = new ArrayList<>();
        targetingMethods.addAll(Arrays.asList(Ability.Detail.values()));
        targetingMethods = new ArrayList<>(targetingMethods.subList(targetingMethods.indexOf(Ability.Detail._TARGETING_) +1, targetingMethods.indexOf(Ability.Detail._COMMON_)));
        
        do {
            
            abilitySkill.targeting = targetingMethods.get(random.nextInt(targetingMethods.size()));

        } while (!procedures.containsKey(abilitySkill.targeting));
        
        abilitySkill = procedures.get(abilitySkill.targeting).modify(abilitySkill);
        
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
            
            while (abilitySkill.getScore("Complexity") > 0) {
                Ability.Detail effect;

                do {
                    effect = tree.effectDetails.get(random.nextInt(tree.effectDetails.size()));
                } while (!abilitySkill.effectDetails.contains(effect) && effect.cost > abilitySkill.getScore("Complexity"));

                abilitySkill.effectDetails.add(effect);
                abilitySkill.getStat("Complexity").modify(-effect.cost);
                
            }
        
            Ability.Detail cost;
            i = 0;
            while (abilitySkill.getScore("Cost Pool") > 0) {
                
                cost = abilitySkill.costDetails.get(i++);
                
                if (i >= abilitySkill.costDetails.size())
                    i = 0;
                
                procedures.get(cost).modify(abilitySkill);
                
            }
            
            Ability.Detail effect;
            i = 0;
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
