/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import templeoftheelements.collision.Creature;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.Ability;
import templeoftheelements.player.Effect;

/**
 *
 * @author angle
 */
public class AreaSpell extends Spell{

    Effect effect;
    float radius;

    public AreaSpell(String name, Renderable sprite, Effect effect) {
        super(name, sprite);
        this.effect = effect;
    }
    
    @Override
    public void perform(Creature creature, Vec2 in) {
        if (!isPossible(creature)) return;
        super.perform(creature, in);
        cast(creature, in);
    }
    
    @Override
    public void cast(Creature caster, Vec2 in) {
        effect.effect(caster, in);
    }

    @Override
    public Ability clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDescription() {
        return "This is an aea Spell.";
    }
    
}
