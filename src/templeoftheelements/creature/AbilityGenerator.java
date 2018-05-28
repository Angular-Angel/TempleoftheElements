/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.creature;

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
        
        abilitySkill.stats.addStat("Pool", new NumericStat(100)); 
        abilitySkill.stats.addStat("Complexity", new NumericStat(5 + tree.layers.size())); 
        
        if (tree.spammables == 0 || tree.spammables / tree.abilities < 0.4) {
            abilitySkill.usage = Ability.Detail.SPAMMABLE;
            abilitySkill.stats.addStat("Cost Pool", new NumericStat(10));
            abilitySkill.stats.addStat("Cost Complexity", new NumericStat(5 + tree.layers.size()));
            tree.spammables++;
            tree.abilities++;
        } else {
            abilitySkill.usage = Ability.Detail.SITUATIONAL;
            abilitySkill.stats.addStat("Cost Pool", new NumericStat(30));
            abilitySkill.stats.addStat("Cost Complexity", new NumericStat(10 + tree.layers.size() * 2));
            tree.situationals++;
            tree.abilities++;
        }
        
        //Then pick the targeting method 
        
        ArrayList<Ability.Detail> targetingMethods = new ArrayList<>();
        targetingMethods.addAll(Arrays.asList(Ability.Detail.values()));
        targetingMethods = new ArrayList<>(targetingMethods.subList(targetingMethods.indexOf(Ability.Detail._TARGETING_) +1, targetingMethods.indexOf(Ability.Detail._COMMON_)));
        
        abilitySkill.targeting = targetingMethods.get(random.nextInt(targetingMethods.size()));
        abilitySkill = procedures.get(abilitySkill.targeting).modify(abilitySkill);
        
        //Then, depending on the above, pick what costs this spell will have.
        
        try {
            
//            int costcomplexity = (int) (abilitySkill.stats.getScore("Complexity") * (0.2 + (0.4 * random.nextFloat())));
//        
//            abilitySkill.stats.getStat("Complexity").modify("Cost Complexity", -costcomplexity);
//            
//            abilitySkill.stats.addStat("Cost Complexity", new NumericStat(costcomplexity));
            
            int i = 0;
            while (abilitySkill.stats.getScore("Cost Complexity") > 0) {
                Ability.Detail cost;

                do {
                    cost = tree.costDetails.get(random.nextInt(tree.costDetails.size()));
                } while (!abilitySkill.costDetails.contains(cost) && cost.cost > abilitySkill.stats.getScore("Cost Complexity"));

                abilitySkill.costDetails.add(cost);
                abilitySkill.stats.getStat("Cost Complexity").modify(cost.toString(), -cost.cost);
                i++;
                if (i > 10) break;
                
            }
            i = 0;
            
            while (abilitySkill.stats.getScore("Complexity") > 0) {
                Ability.Detail effect;

                do {
                    effect = tree.effectDetails.get(random.nextInt(tree.effectDetails.size()));
                } while (!abilitySkill.effectDetails.contains(effect) && effect.cost > abilitySkill.stats.getScore("Complexity"));

                abilitySkill.effectDetails.add(effect);
                abilitySkill.stats.getStat("Complexity").modify(effect.toString(), -effect.cost);
                i++;
                if (i > 10) break;
            }
        
            Ability.Detail cost;
            i = 0;
            while (abilitySkill.stats.getScore("Cost Pool") > 0) {
                
                cost = abilitySkill.costDetails.get(i++);
                
                if (i >= abilitySkill.costDetails.size())
                    i = 0;
                
                procedures.get(cost).modify(abilitySkill);
            }
            
            Ability.Detail effect;
            i = 0;
            while (abilitySkill.stats.getScore("Pool") > 0) {
                
                effect = abilitySkill.effectDetails.get(i++);
                
                if (i >= abilitySkill.effectDetails.size())
                    i = 0;
                
                if (abilitySkill.stats.getScore("Pool") > effect.cost) {
                    procedures.get(effect).modify(abilitySkill);
                }
                else abilitySkill.stats.getStat("Pool").modify("Effect too expensive", -1);
            }
            
            abilitySkill.stats.removeStat("Pool");
            abilitySkill.stats.removeStat("Cost Pool");
            abilitySkill.stats.removeStat("Complexity");
            abilitySkill.stats.removeStat("Cost Complexity");
        } catch (NoSuchStatException ex) {
            Logger.getLogger(AbilityGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return abilitySkill;
    }
    
}
