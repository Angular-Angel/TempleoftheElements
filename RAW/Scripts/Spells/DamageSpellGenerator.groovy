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
        throw new UnsupportedOperationException();
    }
    
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
        CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) abilityDef.tree; //the tree to which the node will belong
        
        Spell spell = (Spell) node.ability;
        
        AbilityDefinition ability = node.nodeDef.ability;
        
        if (spell instanceof AreaSpell) {
            AreaSpell areaSpell = (AreaSpell) spell;
            
            
        } else if (spell instanceof MissileSpell) {
            
            AttackDefinition missile = ((MissileSpell) spell).missile;
            
            if (ability.details.contains(Spell.Detail.SPIRIT_BASED)) {
            damageValue = 0.2 + (damageValue -1) * 0.05;
            damage = new EquationStat("" + damageValue + " * [Attacker@Spirit] * [Attacker@Spell Damage Multiplier]");
        } else if (ability.details.contains(Spell.Detail.INTELLIGENCE_BASED)) {
            damageValue = 0.2 + (damageValue -1) * 0.05;
            damage = new EquationStat("" + damageValue + " * [Attacker@Intelligence] * [Attacker@Spell Damage Multiplier]");
        } else if (ability.details.contains(Spell.Detail.PERCEPTION_BASED)) {
            damageValue = 0.2 + (damageValue -1) * 0.05;
            damage = new EquationStat("" + damageValue + " * [Attacker@Perception] * [Attacker@Spell Damage Multiplier]");
        } else {
            damageValue = 30 + (damageValue -1) * 3;
            damage = new EquationStat("" + damageValue + " * [Attacker@Spell Damage Multiplier]");
        }
            
        } else {
            Effect e = new Effect() {

                @Override
                public float effect(EffectSource src, Object object) {
                    if (!(object instanceof Creature && src instanceof Creature)) return 0;

                    Creature source = (Creature) src;

                    float damage = source.getScore("Spirit") / 5;
                    return ((Creature) object).takeDamage(damage, "Fire");
                }
            };

            spell.addEffect(e)
        }
        
        return node;
    }
    
    public boolean isApplicable(Ability ability) {
        return (ability instanceof Spell);
    }
}