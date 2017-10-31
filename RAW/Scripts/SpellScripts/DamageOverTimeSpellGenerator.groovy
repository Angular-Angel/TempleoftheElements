import templeoftheelements.Spell;
import templeoftheelements.effect.Effect;
import java.util.Random;
import templeoftheelements.player.characterwheel.*;
import templeoftheelements.player.characterwheel.CharacterTreeDef.AbilityDefinition;

public class DamageOverTimeSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();

    @Override
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
         Spell spell = (Spell) abilityDef.ability;
        
        int pool = Math.min(20, (abilityDef.getScore("Pool")));
        
        int damageValue = 1 + (random.nextInt(pool) / Spell.Detail.DAMAGE.cost);
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
        abilityDef.getStat("Pool").modifyBase(-damageValue * Spell.Detail.DAMAGE.cost);
        
        return abilityDef;
    }
}