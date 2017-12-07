import templeoftheelements.spells.Spell;
import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.creature.Ability;
import templeoftheelements.effect.Effect;
import java.util.Random;
import templeoftheelements.player.AbilitySkill;

public class DamageOverTimeSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();

    @Override
    public AbilitySkill modify(AbilitySkill abilitySkill) {
         Spell spell = (Spell) abilitySkill.ability;
        
        int pool = Math.min(20, (abilityDef.getScore("Pool")));
        
        int damageValue = 1 + (random.nextInt(pool) / Ability.Detail.DAMAGE.cost);
        Stat damage;
        
        if (spell instanceof AreaSpell) {
            AreaSpell areaSpell = (AreaSpell) spell;    
        } else if (spell instanceof MissileSpell) {
            
            AttackDefinition missile = ((MissileSpell) spell).missile;
            damage = new EquationStat("" + damageValue * 3 + " * [Attacker@Spell Damage Multiplier]");
            missile.addStat("Damage", damage);
//            }
            
        } else {
//            Effect e = new Effect() {
//
//                @Override
//                public float effect(EffectSource src, Object object) {
//                    if (!(object instanceof Creature && src instanceof Creature)) return 0;
//
//                    Creature source = (Creature) src;
//
//                    
//                    return ((Creature) object).takeDamage(damage, "Fire");
//                }
//            };
//
//            spell.addEffect(e)
        }
        abilitySkill.stats.getStat("Pool").modifyBase(-damageValue * Ability.Detail.DAMAGE.cost);
        
        return abilitySkill;
    }
}