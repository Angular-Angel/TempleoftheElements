import templeoftheelements.spells.Spell;
import templeoftheelements.Element;
import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.Creature;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectSource;
import stat.*;
import java.util.Random;
import templeoftheelements.player.CharacterWheel;
import templeoftheelements.player.AbilitySkill;

public class DamageSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilitySkill modify(AbilitySkill abilitySkill) {
        CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) abilitySkill.tree; //the tree to which the node will belong
        Spell spell = (Spell) abilitySkill.ability;
        
        int pool = Math.min(20, (abilitySkill.getScore("Pool")));
        
        int damageValue = (Ability.Detail.DAMAGE.cost + random.nextInt(pool-Ability.Detail.DAMAGE.cost)) / Ability.Detail.DAMAGE.cost;
        Stat damage;
        
        Element element = tree.definition.element;
        
        abilitySkill.getStat("Pool").modifyBase(-damageValue * Ability.Detail.DAMAGE.cost);
        
        String effectName = "Damage";
        
        if (spell.containsEffect(effectName)) {
            
            Effect e = spell.getEffect(effectName);
            
            e.addStat("Damage Value", new NumericStat(damageValue));
            
        } else {
            
            damage = new EquationStat(" [Damage Value] * " + spell.damageValueMultiplier() + " * [Source@Spell Damage Multiplier]");
        

            Effect e = new Effect(effectName, false) {

                Element ele = element;

                @Override
                public float effect(EffectSource src, Object object) {
                    if (!(object instanceof Creature && src instanceof Creature)) return 0;

                    return ((Creature) object).takeDamage(getScore("Damage"), ele.name);
                }

                public String getDescription() {
                    refactor();

                    return "Deals " + getScore("Damage") + " " + ele.name + " Damage.";
                }
            };

            e.addStat("Damage Value", new NumericStat(damageValue));

            e.addStat("Damage", damage);

            spell.addEffect(e)
        }
        
        return abilitySkill;
    }
}