/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements;

import java.util.ArrayList;
import java.util.HashMap;
import org.jbox2d.common.Vec2;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.collision.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.Ability;
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
        return "";
    }

    @Override
    public boolean containsEffect(String s) {
        return effects.containsKey(s);
    }

    @Override
    public Effect getEffect(String s) {
        return effects.get(s);
    }
    
}
