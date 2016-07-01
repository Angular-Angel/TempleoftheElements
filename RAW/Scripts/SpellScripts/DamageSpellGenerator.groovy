import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectSource;
import stat.*;
import generation.*;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import generation.ProceduralGenerator;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;
import templeoftheelements.player.CharacterTreeDef.NodeDefinition;
import templeoftheelements.player.CharacterTreeDef.ClusterDefinition;
import org.jbox2d.common.Vec2;

public class DamageSpellGenerator implements GenerationProcedure<AbilityDefinition> {

     //our random number generator;
    Random random = new Random();

    public AbilityDefinition generate() {
        throw new UnsupportedOperationException();
    }

    public AbilityDefinition generate(Object o) {
        AbilityDefinition abilityDef = (AbilityDefinition) o; //the definition for the node we're making
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
    
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isApplicable(AbilityDefinition abilityDef) {
        return (abilityDef.ability instanceof Spell);
    }
}