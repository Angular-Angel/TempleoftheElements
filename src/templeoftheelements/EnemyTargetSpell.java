/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.collision.Creature;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.Ability;
import templeoftheelements.player.Clickable;
import templeoftheelements.effect.Effect;

/**
 *
 * @author angle
 */
public class EnemyTargetSpell extends Spell {
    
    ArrayList<Effect> effects;

    public EnemyTargetSpell(String name, Renderable sprite) {
        super(name, sprite);
        effects = new ArrayList<>();
    }
    
    @Override
    public void perform(Creature creature, Vec2 in) {
        if (!isPossible(creature)) return;
        super.perform(creature, in);
        cast(creature, in);
    }

    @Override
    public void cast(Creature caster, Vec2 in) {
        Clickable c = game.getClickable(in.x, in.y);
        if (c instanceof Creature) {
            for (Effect e : effects) e.effect(caster, c);
        }
    }

    @Override
    public Ability clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDescription() {
        return "Enemy Target Spell.";
    }

    @Override
    public void addEffect(Effect effect) {
        effects.add(effect);
    }
    
}
