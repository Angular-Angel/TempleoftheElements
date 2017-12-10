
package templeoftheelements.item;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import stat.EquationStat;
import stat.NoSuchStatException;
import stat.NumericStat;
import stat.StatContainer;
import templeoftheelements.collision.Attack;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.MeleeAttack;
import templeoftheelements.collision.RangedAttack;
import templeoftheelements.display.Renderable;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectContainer;

/**
 *
 * @author angle
 */

public class AttackDefinition implements EffectContainer{

    public HashMap<String, Effect> onHitEffects;
    private Shape shape;
    private Renderable sprite;
    private final String name, type;
    public StatContainer stats;
    
    public AttackDefinition(String name, Renderable sprite, String type) {
        this(name, sprite, new CircleShape(), type);
    }
    
    public AttackDefinition(String name, Renderable sprite, Shape shape, String type) {
        this.shape = shape;
        this.sprite = sprite;
        this.type = type;
        this.name = name;
        onHitEffects = new HashMap<>();
        this.stats = new StatContainer(false);
    }
    
    public Renderable getSprite() {
        return sprite;
    }
    
    public Attack generate(Creature attacker) {
        Attack attack = null;
        try {
            if (shape instanceof CircleShape && shape.m_radius == 0) shape.setRadius(stats.getScore("Size"));
            if (stats.hasStat("Ranged Attack")) {
                attack = new RangedAttack(attacker, this, shape);
                Vec2 pos = attacker.getPosition();
                pos.x += (attacker.stats.getScore("Size") + 1) * (float) Math.sin(Math.toRadians(attacker.getDirection()));
                pos.y += (attacker.stats.getScore("Size") + 1) * (float) Math.cos(Math.toRadians(attacker.getDirection()));
                Vec2 vector = new Vec2();
                vector.x += 2 * attack.stats.getScore("Speed") * (float) Math.sin(Math.toRadians(attacker.getDirection()));
                vector.y += 2 * attack.stats.getScore("Speed") * (float) Math.cos(Math.toRadians(attacker.getDirection()));
                attack.getBody().setTransform(pos, 0);
                attack.getBody().applyForceToCenter(vector);
            } else {
                int dir = -(int) (stats.getScore("Angular Travel")/2);
                float reach = stats.getScore("Reach") + attacker.stats.getScore("Size")/2;
                attack = new MeleeAttack(attacker, this, shape, dir, reach);
                ((MeleeAttack) attack).move(attacker.getPosition(), 0);
                attack.stats.addStat("Damage", new NumericStat(attacker.stats.getScore("Physical Damage") 
                        * attack.stats.getScore("Damage Multiplier")));
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
        ret.stats.addAllStats(this.stats);
        return ret;
    }
    
    public void addReference(String s, StatContainer container) {
        stats.addReference(s, container);
        for (Effect e : onHitEffects.values()) {
            e.addReference(s, container);
        }
    }
    
    public void setActive(boolean active) {
        stats.setActive(active);
        for (Effect e : onHitEffects.values()) {
            e.setActive(active);
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        stats.refactor();
        
        String ret = "";
        ret += "This is an attack.";
        
        
        
        ret += "\nSize: " + stats.getScore("Size");
//            ret += "\nDamage: " + missile.getScore("Damage") + " (" + ((EquationStat) missile.getStat("Damage")).equation + ")";

        for (Effect e : onHitEffects.values()) {
            ret += "\n" + e.getDescription();
        }
        
        return ret;
    }
    
    public void init(Creature c) {
        stats.init(c.stats);
        for (Effect e : onHitEffects.values()) {
            e.init(c.stats);
        }
    }
    
}
