
package templeoftheelements.spells;

import java.util.Collection;
import templeoftheelements.item.AttackDefinition;
import java.util.logging.Level;
import java.util.logging.Logger;
import stat.EquationStat;
import stat.NoSuchStatException;
import stat.StatContainer;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.AbilityDefinition;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectContainer;

/**
 *
 * @author angle
 */


public class MissileSpell extends Spell {

    public AttackDefinition missile;
    
    public MissileSpell(AbilityDefinition abilityDefinition, AttackDefinition attack) {
        this(abilityDefinition, attack, new StatContainer());
    }
    
    public MissileSpell(AbilityDefinition abilityDefinition, AttackDefinition attack, StatContainer stats) {
        super(abilityDefinition, attack.getSprite(), stats);
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

    public Ability copy() {
        return new MissileSpell(abilityDef, getMissile().copy(), this.stats);
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
    public void init(Creature c) {
        initValues(c);
        c.addAction(this);
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
    public void deInit(Creature c) {}
    
    @Override
    public void addAllEffects(EffectContainer effects) {
        missile.addAllEffects(effects);
    }

    @Override
    public Collection<Effect> getAllEffects() {
        return missile.getAllEffects();
    }

    @Override
    public void initValues(Creature c) {
        stats.initValues(c.stats);
        missile.initValues(c);
    }
}
