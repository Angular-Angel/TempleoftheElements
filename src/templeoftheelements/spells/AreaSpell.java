/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.spells;

import java.util.Collection;
import java.util.HashMap;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.AbilityDefinition;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectContainer;

/**
 *
 * @author angle
 */
public class AreaSpell extends Spell {

    HashMap<String, Effect> effects;

    public AreaSpell(AbilityDefinition abilityDefinition, Renderable sprite, Effect effect) {
        this(abilityDefinition, sprite);
        addEffect(effect);
    }
    
    public AreaSpell(AbilityDefinition abilityDefinition, Renderable sprite) {
        super(abilityDefinition, sprite);
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
        AreaSpell ret = new AreaSpell(abilityDef, sprite);
        ret.stats.addAllStats(stats);
        for (Effect e : effects.values()) ret.addEffect(e);
        return ret;
    }

    @Override
    public final void addEffect(Effect effect) {
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
    public void init(Creature c) {
        initValues(c);
        c.addAction(this);
    }

    @Override
    public void deInit(Creature c) {}

    @Override
    public void addAllEffects(EffectContainer effects) {
        for (Effect e : effects.getAllEffects()) {
            addEffect(e);
        }
    }

    @Override
    public Collection<Effect> getAllEffects() {
        return effects.values();
    }

    @Override
    public void initValues(Creature c) {
        stats.initValues(c.stats);
        for (Effect e : effects.values()) {
            e.stats.initValues(c.stats);
        }
    }
    
}
