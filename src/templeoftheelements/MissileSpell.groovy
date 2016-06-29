
package templeoftheelements;

import templeoftheelements.item.AttackDefinition;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
import stat.EquationStat;
import stat.NoSuchStatException;
import stat.Stat;
import stat.StatContainer;
import templeoftheelements.collision.Creature;
import templeoftheelements.collision.Position
import templeoftheelements.player.Ability;
import templeoftheelements.effect.Effect;
import templeoftheelements.player.CharacterTreeDef;

/**
 *
 * @author angle
 */


public class MissileSpell extends Spell {

    public AttackDefinition missile;
    
    public MissileSpell(AttackDefinition attack) {
        this(attack.getName(), attack, new StatContainer());
    }
    
    public MissileSpell(String name, AttackDefinition attack) {
        this(name, attack, new StatContainer());
    }
    
    public MissileSpell(String name, AttackDefinition attack, StatContainer stats) {
        super(name, attack.getSprite(), stats);
        missile = attack;
    }

    @Override
    public void perform(Creature creature, Position pos) {
        if (!isPossible(creature)) return;
        super.perform(creature, pos);
        cast(creature, pos);
    }

    @Override
    public void cast(Creature caster, Position pos) {
        caster.attack(getMissile());
    }

    @Override
    public Ability copy() {
        return new MissileSpell(this.getName(), getMissile().copy(), this);
    }

    /**
     * @return the missile
     */
    public AttackDefinition getMissile() {
        return missile;
    }

    @Override
    public void addEffect(Effect effect) {
        missile.addOnHitEffect(effect);
    }

    @Override
    public String getDescription() {
            
        missile.refactor();
        
        String ret = "";
        ret += "This spell shoots a missile.";
        
        ret += showCosts();
        
        try {
            ret += "\nSpeed: " + missile.getScore("Speed") + " (" + ((EquationStat) missile.getStat("Speed")).equation + ")";
            ret += "\nSize: " + missile.getScore("Size");
//            ret += "\nDamage: " + missile.getScore("Damage") + " (" + ((EquationStat) missile.getStat("Damage")).equation + ")";

            for (Effect e : missile.onHitEffects.values()) {
                ret += "\n" + e.getDescription();
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(MissileSpell.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ret;
    }
    
    
    @Override
    public void init(Creature c) {
        super.init(c);
        missile.addReference("Source", c);
        missile.setActive(true);
    }
    
    public boolean containsEffect(String s) {
        return missile.onHitEffects.containsKey(s);
    }
    
    @Override
    public Effect getEffect(String s) {
        return missile.onHitEffects.get(s);
    }    
}
