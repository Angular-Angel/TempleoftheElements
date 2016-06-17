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
        
        
        abilityDef.getStat("Pool").modifyBase(-damageValue * Spell.Detail.DAMAGE.cost);
        
       if (spell instanceof MissileSpell) {
            
            AttackDefinition missile = ((MissileSpell) spell).missile;
            missile.addStat("Damage Value", new NumericStat(damageValue * 3));
            
            if (!missile.hasStat("Damage")) {
                damage = new EquationStat("[Damage Value] * [Attacker@Spell Damage Multiplier]");
                missile.addStat("Damage", damage);
            }
            
//            spell.description += "\nDamage: " + damageValue * 3;
        
            return abilityDef;
        } else if (spell instanceof AreaSpell) {
            
            damage = new EquationStat("" + damageValue * 0.5 + " * [Attacker@Spell Damage Multiplier]");
            
        } else if (spell instanceof EnemyTargetSpell) {
            
            damage = new EquationStat("" + damageValue * 1 + " * [Attacker@Spell Damage Multiplier]");
            
        }

        Effect e = new Effect(false) {

            @Override
            public float effect(EffectSource src, Object object) {
                if (!(object instanceof Creature && src instanceof Creature)) return 0;

                Creature source = (Creature) src;

                clearReferences();

                active = true;

                addReference("Attacker", source);

                refactor();

                return ((Creature) object).takeDamage(getScore("Damage"), "Fire");
            }
        };

        e.addStat("Damage", damage);

        spell.addEffect(e)
        
        return abilityDef;
    }
    
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isApplicable(AbilityDefinition abilityDef) {
        return (abilityDef.ability instanceof Spell);
    }
}