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
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;
import templeoftheelements.player.CharacterTreeDef.NodeDefinition;
import templeoftheelements.player.CharacterTreeDef.ClusterDefinition;
import org.jbox2d.common.Vec2;

public class EnemyTargetSpellGenerator implements GenerationProcedure<CharacterNode> {

     //our random number generator;
    Random random = new Random();

    public CharacterNode generate() {
        throw new UnsupportedOperationException();
    }

    public CharacterNode generate(Object o) {
        NodeDefinition nodeDef = (NodeDefinition) o; //the definition for the node we're making
        CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) nodeDef.tree; //the tree to which the node will belong
        
        EnemyTargetSpell spell = new EnemyTargetSpell("Enemy Target Spell", new VectorCircle(0.5));
        
        spell.addStat("Mana Cost", new NumericStat(1));
        
        CharacterNode node = new AbilityNode(null, tree, spell);

        for (StatDescriptor stat : nodeDef.stats) {
            node.addStat(stat.name, new NumericStat(stat.increase));
        }

        node.nodeDef = nodeDef;
        return node;
    }
    
    public CharacterNode modify(CharacterNode node) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isApplicable(CharacterNode node) {
        throw new UnsupportedOperationException();
    }
}