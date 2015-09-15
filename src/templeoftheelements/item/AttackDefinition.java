
package templeoftheelements.item;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import stat.NoSuchStatException;
import stat.NumericStat;
import stat.Trait;
import templeoftheelements.collision.Attack;
import templeoftheelements.collision.Creature;
import templeoftheelements.collision.MeleeAttack;
import templeoftheelements.collision.RangedAttack;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.Effect;
import templeoftheelements.player.EffectContainer;

/**
 *
 * @author angle
 */

public class AttackDefinition extends Trait{

    private ArrayList<Effect> onHitEffects;
    private Shape shape;
    private Renderable sprite;
    private String type;
    
    public AttackDefinition(String name, Renderable sprite, String type) {
        this(name, sprite, new CircleShape(), type);
    }
    
    public AttackDefinition(String name, Renderable sprite, Shape shape, String type) {
        super(name);
        this.shape = shape;
        this.sprite = sprite;
        this.type = type;
        onHitEffects = new ArrayList<>();
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
                vector.x += 2 * getScore("Speed") * (float) Math.sin(Math.toRadians(attacker.getDirection()));
                vector.y += 2 * getScore("Speed") * (float) Math.cos(Math.toRadians(attacker.getDirection()));
                attack.getBody().setTransform(pos, 0);
                attack.getBody().applyForceToCenter(vector);
            } else {
                int dir = -(int) (getScore("Angular Travel")/2);
                float reach = getScore("Reach") + attacker.getScore("Size")/2;
                attack = new MeleeAttack(attacker, this, shape, dir, reach);
                ((MeleeAttack) attack).move(attacker.getPosition(), 0);
                attack.addStat("Damage", new NumericStat(attacker.getScore("Damage") 
                        * getScore("Damage Multiplier")));
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(AttackDefinition.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Effect e : onHitEffects) attack.addOnHitEffect(e);
        return attack;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    
    public void addOnHitEffect(Effect e) {
        onHitEffects.add(e);
    }
    
}
