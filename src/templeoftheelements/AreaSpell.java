/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Override
    public Ability copy() {
        AreaSpell ret = new AreaSpell(name, sprite);
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
        super.init(c);
        for (Effect e : effects.values()) {
            e.addReference("Source", c);
            e.active = true;
        }
    }
    
}
