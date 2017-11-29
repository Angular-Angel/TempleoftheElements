/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package templeoftheelements.spells;

import java.util.HashMap;
import stat.StatContainer;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;
import templeoftheelements.creature.Ability;
import templeoftheelements.effect.Effect;
/**
 *
 * @author angle
 */
class SelfTargetSpell extends Spell {
    
    HashMap<String, Effect> effects;

    public SelfTargetSpell(String name, Renderable sprite) {
        super(name, sprite);
        effects = new HashMap<>();
    }
    
    @Override
    public void perform(Creature creature, Position pos) {
        if (!isPossible(creature)) return;
        super.perform(creature, pos);
        cast(creature, pos);
    }

    @Override
    public void cast(Creature caster, Position pos) {
        for (Effect e : effects.values()) e.effect(caster, caster);
    }

    @Override
    public Ability copy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addEffect(Effect effect) {
        effects.put(effect.name, effect);
    }

    @Override
    public String getDescription() {
        String ret = "This spell targets your own character.";
        
        ret += showCosts();
        
        for (Effect e : effects.values()) {
                ret += "\n" + e.getDescription();
            }
        return ret;
    }

    @Override
    public boolean containsEffect(String s) {
        return effects.containsKey(s);
    }

    @Override
    public Effect getEffect(String s) {
        return effects.get(s);
    }

    @Override
    public float damageValueMultiplier() {
        return 1;
    }
    
    @Override
    public void init(StatContainer c) {
        stats.init(c);
        for (Effect e : effects.values()) {
            e.init(c);
        }
    }
}

