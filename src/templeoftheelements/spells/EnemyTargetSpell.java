/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.spells;

import java.util.Collection;
import java.util.HashMap;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.AbilityDefinition;
import templeoftheelements.player.Clickable;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectContainer;

/**
 *
 * @author angle
 */
public class EnemyTargetSpell extends Spell {
    
    HashMap<String, Effect> effects;

    public EnemyTargetSpell(AbilityDefinition abilityDefinition, Renderable sprite) {
        super(abilityDefinition, sprite);
        effects = new HashMap<>();
    }
    
    @Override
    public void perform(Creature creature, Position in) {
        if (!isPossible(creature)) return;
        super.perform(creature, in);
        cast(creature, in);
    }

    @Override
    public void cast(Creature caster, Position pos) {
        Clickable c = game.getClickable(pos.x, pos.y);
        if (c instanceof Creature) {
            for (Effect e : effects.values()) e.effect(caster, c);
        }
    }

    public Ability copy() {
        EnemyTargetSpell ret = new EnemyTargetSpell(abilityDef, sprite);
        for (Effect e : effects.values()) ret.addEffect(e);
        return ret;
    }

    @Override
    public void addEffect(Effect effect) {
        effects.put(effect.name, effect);
    }

    @Override
    public String getDescription() {
        String ret = "This spell targets an enemy directly.";
        
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
        stats.init(c.stats);
        for (Effect e : effects.values()) {
            e.stats.init(c.stats);
        }
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
}
