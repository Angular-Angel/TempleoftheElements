/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.creature;

import generation.GenerationProcedure;
import templeoftheelements.player.AbilitySkill;

/**
 *
 * @author angle
 */
public abstract class AbilityGenerationProcedure implements GenerationProcedure<AbilitySkill> {

    @Override
    public AbilitySkill generate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbilitySkill generate(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbilitySkill modify(AbilitySkill t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isApplicable(AbilitySkill t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
