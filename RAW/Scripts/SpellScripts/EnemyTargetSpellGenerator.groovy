import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import stat.*;
import generation.*;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import generation.ProceduralGenerator;
import templeoftheelements.player.characterwheel.CharacterWheel;
import templeoftheelements.player.characterwheel.CharacterTreeDef.AbilityDefinition;
import templeoftheelements.player.characterwheel.CharacterTreeDef.NodeDefinition;
import templeoftheelements.player.characterwheel.CharacterTreeDef.ClusterDefinition;
import org.jbox2d.common.Vec2;

public class EnemyTargetSpellGenerator implements GenerationProcedure<AbilityDefinition> {

     //our random number generator;
    Random random = new Random();

    public AbilityDefinition generate() {
        throw new UnsupportedOperationException();
    }

    public AbilityDefinition generate(Object o) {
        AbilityDefinition abilityDef = (AbilityDefinition) o; //the definition for the node we're making
        CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) abilityDef.tree; //the tree to which the node will belong
        
        EnemyTargetSpell spell = new EnemyTargetSpell("Enemy Target Spell", new VectorCircle(0.5));
        
        spell.addStat("Cast Time", new NumericStat(0));
        spell.addStat("Mana Cost", new NumericStat(0));
        spell.addStat("Cooldown", new NumericStat(0));
        
        abilityDef.ability = spell
        return abilityDef;
    }
    
    public AbilityDefinition modify(AbilityDefinition node) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isApplicable(AbilityDefinition node) {
        throw new UnsupportedOperationException();
    }
}