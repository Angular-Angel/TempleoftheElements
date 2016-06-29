import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import templeoftheelements.effect.*;
import stat.*;
import generation.*;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import generation.ProceduralGenerator;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;
import templeoftheelements.effect.Effect;

public class DebuffSpellGenerator implements GenerationProcedure<AbilityDefinition> {

     //our random number generator;
    Random random = new Random();

    public AbilityDefinition generate() {
        throw new UnsupportedOperationException();
    }

    public AbilityDefinition generate(Object o) {
        AbilityDefinition abilityDef = (AbilityDefinition) o; //the definition for the node we're making
        CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) abilityDef.tree; //the tree to which the node will belong
        
        String name = "Debuff";
        
        final ArrayList<StatDescriptor> debuffAttributes = tree.definition.element.debuffAttributes;
        
        final Debuff debuff = new Debuff(name, 30, null);
        
        int pool = Math.min(20, (abilityDef.getScore("Pool"))), i = 0;
        
        while (pool > Spell.Detail.DEBUFF.cost && i < 10) {
            i++;
            StatDescriptor debuffStat = debuffAttributes.get(random.nextInt(debuffAttributes.size()));
            int debuffValue = 1 + random.nextInt((int) (pool / Spell.Detail.DEBUFF.cost) - 1);
            debuff.addStat(debuffStat.name, new NumericStat(debuffValue));
            abilityDef.getStat("Pool").modifyBase(-debuffValue * Spell.Detail.DAMAGE.cost);
//            ((Spell) abilityDef.ability).description += "\nDebuff: " + debuffStat.name + ", " + debuffValue;
        }
        
        Effect effect = new Effect("Debuff") {
            @Override
            public float effect(EffectSource source, Object obj) {
                if (source instanceof Creature) debuff.origin = (Creature) source;
                if (o instanceof Creature)
                    ((Creature) obj).addStatusEffect(debuff);
                return 0;
            }
            
            public String getDescription() {
                return "Debuffs the target.";
            }
        };
        
        ((Spell) abilityDef.ability).addEffect(effect);
        
        return abilityDef;
    }
    
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isApplicable(AbilityDefinition abilityDef) {
        throw new UnsupportedOperationException();
    }
}