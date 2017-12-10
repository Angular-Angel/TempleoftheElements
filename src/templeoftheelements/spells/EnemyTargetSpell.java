/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.spells;

import java.util.HashMap;
import stat.StatContainer;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;
import templeoftheelements.creature.Ability;
import templeoftheelements.player.Clickable;
import templeoftheelements.effect.Effect;

/**
 *
 * @author angle
 */
public class EnemyTargetSpell extends Spell {
    
    HashMap<String, Effect> effects;

    public EnemyTargetSpell(String name, Renderable sprite) {
        super(name, sprite);
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
        EnemyTargetSpell ret = new EnemyTargetSpell(getName(), sprite);
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
    public float damageValueMultiplier() {
        return 1;
    }
    
    @Override
    public void init(Creature c) {
        stats.init(c.stats);
        for (Effect e : effects.values()) {
            e.init(c.stats);
        }
    }

    @Override
    public void deInit(Creature c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
