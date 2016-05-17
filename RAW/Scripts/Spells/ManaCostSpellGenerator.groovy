import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;
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

public class ManaCostSpellGenerator implements GenerationProcedure<AbilityDefinition> {

     //our random number generator;
    Random random = new Random();

    public AbilityDefinition generate() {
        throw new UnsupportedOperationException();
    }

    public AbilityDefinition generate(Object o) {
        AbilityDefinition abilityDef = (AbilityDefinition) o; //the definition for the node we're making
        CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) abilityDef.tree; //the tree to which the node will belong
        
        
        int pool = Math.min(10, (abilityDef.getScore("Cost Pool")));
        if (pool == 0) {
            abilityDef.getStat("Cost Pool").modifyBase(-1);
            return abilityDef.ability;
        }
        
        int manaCost = 1 + random.nextInt(pool) / Spell.Detail.MANA_COST.cost;
        
        abilityDef.ability.addStat("Mana Cost", new NumericStat(5 * manaCost));
        
        abilityDef.getStat("Cost Pool").modifyBase(-manaCost * Spell.Detail.MANA_COST.cost);
        abilityDef.getStat("Pool").modifyBase(manaCost * Spell.Detail.MANA_COST.cost);
        
        return abilityDef;
    }
    
    public AbilityDefinition modify(AbilityDefinition node) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isApplicable(AbilityDefinition node) {
        throw new UnsupportedOperationException();
    }
}