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
public class AbilityGenerator implements ProceduralGenerator<AbilityDefinition> {

    Random random = new Random();
    HashMap<Spell.Detail, GenerationProcedure<AbilityDefinition>> procedures = new HashMap<>();
    
    public void addProcedure(Spell.Detail detail, GenerationProcedure<AbilityDefinition> procedure) {
        procedures.put(detail, procedure);
    }
    
    @Override
    public AbilityDefinition generate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AbilityDefinition generate(Object o) {
        CharacterTree tree = (CharacterTree) o;
        
        AbilityDefinition abilityDef = new AbilityDefinition();
        
        
        abilityDef.addStat("Pool", new NumericStat(100));
        abilityDef.addStat("Complexity", new NumericStat(5 + tree.layers.size()));
        
        abilityDef.tree = tree;
        
        //Important info - the skill tree this ability is for and the abilities that skill tree currently has on it.
        
        //First, pick the usage of the ability.
        
        
        if (tree.spammables / tree.abilities.size() < 0.4) {
            abilityDef.usage = Spell.Detail.SPAMMABLE;
            abilityDef.addStat("Cost Pool", new NumericStat(10));
            abilityDef.addStat("Cost Complexity", new NumericStat(5 + tree.layers.size()));
            tree.spammables++;
        } else {
            abilityDef.usage = Spell.Detail.SITUATIONAL;
            abilityDef.addStat("Cost Pool", new NumericStat(30));
            abilityDef.addStat("Cost Complexity", new NumericStat(10 + tree.layers.size() * 2));
        }
        
        //Then pick the tergeting method 
        
        Spell.Detail targeting;
        ArrayList<Spell.Detail> values = new ArrayList<>();
        values.addAll(Arrays.asList(Spell.Detail.values()));
        values = new ArrayList<>(values.subList(values.indexOf(Spell.Detail._TARGETING_) +1, values.indexOf(Spell.Detail._COMMON_)));
        
        do {
            
            targeting = values.get(random.nextInt(values.size()));

            abilityDef.targeting = targeting;
        } while (!procedures.containsKey(targeting));
        
        abilityDef = procedures.get(targeting).generate(abilityDef);
        
        //Then, depending on the above, pick what costs this spell will have.
        
        try {
            
            int costcomplex = (int) (abilityDef.getScore("Complexity") * (0.2 + (0.4 * random.nextFloat())));
        
            abilityDef.getStat("Complexity").modify(-costcomplex);
            
            abilityDef.addStat("Cost Complexity", new NumericStat(costcomplex));
            
            int i = 0;
            while (abilityDef.getScore("Cost Complexity") > 0) {
                Spell.Detail cost;

                do {
                    cost = abilityDef.tree.definition.costDetails.get(random.nextInt(abilityDef.tree.definition.costDetails.size()));
                } while (!abilityDef.costDetails.contains(cost) && cost.cost > abilityDef.getScore("Cost Complexity"));

                abilityDef.costDetails.add(cost);
                abilityDef.getStat("Cost Complexity").modify(-cost.cost);
                
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(AbilityGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Then loop through and determine all the effects the spell will have.
        
        
        try {
            
            while (abilityDef.getScore("Complexity") > 0) {
                Spell.Detail effect;

                do {
                    effect = abilityDef.tree.definition.effectDetails.get(random.nextInt(abilityDef.tree.definition.effectDetails.size()));
                } while (!abilityDef.effectDetails.contains(effect) && effect.cost > abilityDef.getScore("Complexity"));

                abilityDef.effectDetails.add(effect);
                abilityDef.getStat("Complexity").modify(-effect.cost);
                
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(AbilityGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Finally, run all the scripts that will generate the spell.
        
        try {
            
            Spell.Detail cost;
            int i = 0;
            while (abilityDef.getScore("Cost Pool") > 0) {
                
                cost = abilityDef.costDetails.get(i++);
                
                if (i >= abilityDef.costDetails.size())
                    i = 0;
                
                System.out.println("" + i + ", " + cost + ", " + abilityDef.getScore("Cost Pool"));
                procedures.get(cost).generate(abilityDef);
                
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(AbilityGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            
            Spell.Detail effect;
            int i = 0;
            while (abilityDef.getScore("Pool") > 0) {
                
                effect = abilityDef.effectDetails.get(i++);
                
                if (i >= abilityDef.effectDetails.size())
                    i = 0;
                System.out.println("" + i + ", " + effect + ", " + abilityDef.getScore("Pool"));
                if (abilityDef.getScore("Pool") > effect.cost) abilityDef = procedures.get(effect).generate(abilityDef);
                else abilityDef.getStat("Pool").modify(-1);
                
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(AbilityGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
                System.out.println("Done");
        
        return abilityDef;
    }
    
}
