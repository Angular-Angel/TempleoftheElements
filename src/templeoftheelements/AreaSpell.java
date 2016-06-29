/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import templeoftheelements.collision.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.Ability;
import templeoftheelements.effect.Effect;

/**
 *
 * @author angle
 */
public class AreaSpell extends Spell {

    ArrayList<Effect> effects;

    public AreaSpell(String name, Renderable sprite, Effect effect) {
        super(name, sprite);
        this.effects = new ArrayList<>();
        effects.add(effect);
    }
    
    public AreaSpell(String name, Renderable sprite) {
        super(name, sprite);
        this.effects = new ArrayList<>();
    }
    
    @Override
    public void perform(Creature creature, Position pos) {
        if (!isPossible(creature)) return;
        super.perform(creature, pos);
        cast(creature, pos);
    }
    
    @Override
    public void cast(Creature caster, Position pos) {
        for (Effect e : effects)
            e.effect(caster, pos);
    }

    @Override
    public Ability copy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    @Override
    public String getDescription() {
        return "";
    }
    
}
