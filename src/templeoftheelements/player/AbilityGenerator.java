/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.player;

import generation.GenerationProcedure;
import generation.ProceduralGenerator;
import java.util.HashMap;
import java.util.Random;
import templeoftheelements.Spell;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;

/**
 *
 * @author angle
 */
public class AbilityGenerator implements ProceduralGenerator<Ability> {

    Random random = new Random();
    HashMap<Spell.Detail, GenerationProcedure<Ability>> procedures = new HashMap<>();
    
    @Override
    public Ability generate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ability generate(Object o) {
        AbilityDefinition abilityDef = (AbilityDefinition) o;
        
        Ability ability = null;
        
        for (Spell.Detail detail : abilityDef.details) {
                if (procedures.containsKey(detail)) {
                    if (ability == null) {
                        ability = procedures.get(detail).generate(o);
                    } else
                        ability = procedures.get(detail).modify(ability);
                }
            }
        
        return ability;
    }
    
}
