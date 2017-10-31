import templeoftheelements.Spell;
import templeoftheelements.Element;
import templeoftheelements.collision.Creature; // these are used for
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectSource;
import stat.*;
import java.util.Random;
import templeoftheelements.player.characterwheel.*;
import templeoftheelements.player.characterwheel.CharacterTreeDef.AbilityDefinition;

public class DamageSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
        CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) abilityDef.tree; //the tree to which the node will belong
        Spell spell = (Spell) abilityDef.ability;
        
        int pool = Math.min(20, (abilityDef.getScore("Pool")));
        
        int damageValue = (Spell.Detail.DAMAGE.cost + random.nextInt(pool-Spell.Detail.DAMAGE.cost)) / Spell.Detail.DAMAGE.cost;
        Stat damage;
        
        Element element = tree.definition.element;
        
        abilityDef.getStat("Pool").modifyBase(-damageValue * Spell.Detail.DAMAGE.cost);
        
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
        
        return abilityDef;
    }
}