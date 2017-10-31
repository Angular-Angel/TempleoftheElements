
package templeoftheelements.item;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import stat.NoSuchStatException;
import stat.NumericStat;
import stat.StatContainer;
import stat.Trait;
import templeoftheelements.collision.Attack;
import templeoftheelements.collision.Creature;
import templeoftheelements.collision.MeleeAttack;
import templeoftheelements.collision.RangedAttack;
import templeoftheelements.display.Renderable;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectContainer;

/**
 *
 * @author angle
 */

public class AttackDefinition extends Trait implements EffectContainer{

    public HashMap<String, Effect> onHitEffects;
    private Shape shape;
    private Renderable sprite;
    private String type;
    
    public AttackDefinition(String name, Renderable sprite, String type) {
        this(name, sprite, new CircleShape(), type);
    }
    
    public AttackDefinition(String name, Renderable sprite, Shape shape, String type) {
        super(name, false);
        this.shape = shape;
        this.sprite = sprite;
        this.type = type;
        onHitEffects = new HashMap<>();
    }
    
    public Renderable getSprite() {
        return sprite;
    }
    
    public Attack generate(Creature attacker) {
        Attack attack = null;
        try {
            if (shape instanceof CircleShape && shape.m_radius == 0) shape.setRadius(getScore("Size"));
            if (hasStat("Ranged Attack")) {
                attack = new RangedAttack(attacker, this, shape);
                Vec2 pos = attacker.getPosition();
                pos.x += (attacker.getScore("Size") + 1) * (float) Math.sin(Math.toRadians(attacker.getDirection()));
                pos.y += (attacker.getScore("Size") + 1) * (float) Math.cos(Math.toRadians(attacker.getDirection()));
                Vec2 vector = new Vec2();
                vector.x += 2 * attack.getScore("Speed") * (float) Math.sin(Math.toRadians(attacker.getDirection()));
                vector.y += 2 * attack.getScore("Speed") * (float) Math.cos(Math.toRadians(attacker.getDirection()));
                attack.getBody().setTransform(pos, 0);
                attack.getBody().applyForceToCenter(vector);
            } else {
                int dir = -(int) (getScore("Angular Travel")/2);
                float reach = getScore("Reach") + attacker.getScore("Size")/2;
                attack = new MeleeAttack(attacker, this, shape, dir, reach);
                ((MeleeAttack) attack).move(attacker.getPosition(), 0);
                attack.addStat("Damage", new NumericStat(attacker.getScore("Physical Damage") 
                        * attack.getScore("Damage Multiplier")));
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(AttackDefinition.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Effect e : onHitEffects.values()) attack.addOnHitEffect(e);
        return attack;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    
    @Override
    public void addEffect(Effect e) {
        onHitEffects.put(e.name, e);
    }
    
    public AttackDefinition copy() {
        AttackDefinition ret = new AttackDefinition(type, sprite, shape, type);
        ret.addAllStats(this);
        return ret;
    }
    
    @Override
    public void addReference(String s, StatContainer container) {
        super.addReference(s, container);
        for (Effect e : onHitEffects.values()) {
            e.addReference(s, container);
        }
    }
    
    public void setActive(boolean active) {
        this.active = active;
        for (Effect e : onHitEffects.values()) {
            e.active = active;
        }
    }

    @Override
    public boolean containsEffect(String s) {
        return onHitEffects.containsKey(s);
    }

    @Override
    public Effect getEffect(String s) {
        return onHitEffects.get(s);
    }
    
}
