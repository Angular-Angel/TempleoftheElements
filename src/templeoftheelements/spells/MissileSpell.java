
package templeoftheelements.spells;

import templeoftheelements.item.AttackDefinition;
import java.util.logging.Level;
import java.util.logging.Logger;
import stat.EquationStat;
import stat.NoSuchStatException;
import stat.StatContainer;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.creature.Ability;
import templeoftheelements.effect.Effect;

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
        return new MissileSpell(this.getName(), getMissile().copy(), this.stats);
    }

    /**
     * @return the missile
     */
    public AttackDefinition getMissile() {
        return missile;
    }

    @Override
    public void addEffect(Effect effect) {
        missile.addEffect(effect);
    }

    @Override
    public String getDescription() {
            
        missile.stats.refactor();
        
        String ret = "";
        ret += "This spell shoots a missile.";
        
        ret += showCosts();
        
        try {
            ret += "\nSpeed: " + missile.stats.getScore("Speed") + " (" + ((EquationStat) missile.stats.getStat("Speed")).equation + ")";
            ret += "\nSize: " + missile.stats.getScore("Size");
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
    public void init(StatContainer c) {
        missile.init(c);
    }
    
    @Override
    public boolean containsEffect(String s) {
        return missile.containsEffect(s);
    }
    
    @Override
    public Effect getEffect(String s) {
        return missile.getEffect(s);
    }    
    
    @Override
    public float damageValueMultiplier() {
        return 3f;
    }
}
