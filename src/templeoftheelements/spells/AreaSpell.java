/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.spells;

import java.util.HashMap;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;
import templeoftheelements.creature.Ability;
import templeoftheelements.effect.Effect;

/**
 *
 * @author angle
 */
public class AreaSpell extends Spell {

    HashMap<String, Effect> effects;

    public AreaSpell(String name, Renderable sprite, Effect effect) {
        this(name, sprite);
        addEffect(effect);
    }
    
    public AreaSpell(String name, Renderable sprite) {
        super(name, sprite);
        this.effects = new HashMap<>();
    }
    
    @Override
    public void perform(Creature creature, Position pos) {
        if (!isPossible(creature)) return;
        super.perform(creature, pos);
        cast(creature, pos);
    }
    
    @Override
    public void cast(Creature caster, Position pos) {
        for (Effect e : effects.values())
            e.effect(caster, pos);
    }

    public Ability copy() {
        AreaSpell ret = new AreaSpell(getName(), sprite);
        ret.stats.addAllStats(stats);
        for (Effect e : effects.values()) ret.addEffect(e);
        return ret;
    }

    @Override
    public void addEffect(Effect effect) {
        effects.put(effect.name, effect);
    }

    @Override
    public String getDescription() {
        String ret = "This spell targets an area.";
        
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
        return 0.5f;
    }
    
    @Override
    public void init(Creature c) {
        stats.init(c.stats);
        for (Effect e : effects.values()) {
            e.stats.init(c.stats);
        }
    }

    @Override
    public void deInit(Creature c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
