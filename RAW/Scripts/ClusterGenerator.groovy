import stat.Stat;
import stat.NumericStat;
import stat.StatDescriptor;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import generation.GenerationProcedure;
import templeoftheelements.player.CharacterTree;
import templeoftheelements.player.CharacterNode;

//This class generates descriptions of clusters if nodes for character trees.

public class ClusterGenerator implements GenerationProcedure<CharacterTree> {

     //our random number generator;
    Random random = new Random();

    public CharacterTree generate(Object o) {
        throw new UnsupportedOperationException();
    }
    
    private StatDescriptor randomStat(CharacterTree characterTree) {
        StatDescriptor stat;
        if (random.nextInt(2) == 0) {
            //Use a primary stat
            stat = characterTree.definition.primaryAttributes.get(random.nextInt(characterTree.definition.primaryAttributes.size()));
        } else {
            //use a secondary stat.
            stat = characterTree.definition.secondaryAttributes.get(random.nextInt(characterTree.definition.secondaryAttributes.size()));
        }
        
        return stat;
    }

    public CharacterTree modify(CharacterTree characterTree) {

        StatDescriptor stat1 = randomStat(characterTree), stat2 = randomStat(characterTree);

        CharacterNode node = new CharacterNode(tree);
        node.addStat(stat1.identifier, new NumericStat(stat1.increase));
        node.addStat(stat2.identifier, new NumericStat(stat2.increase));
        
        CharacterNode entry = node; //remember our first node
        
        for (int i = 0; i < 3; i++) {
            node = new CharacterNode(node, tree);
            node.addStat(stat1.identifier, new NumericStat(stat1.increase));
        }
        
        node = entry; //return to the first node of the cluseter, so we end up with two separate lines of ndes.
        
        for (int i = 0; i < 3; i++) {
            node = new CharacterNode(node, tree);
            node.addStat(stat2.identifier, new NumericStat(stat2.increase));
        }

        //now for the capstone.
        if (random.nextInt(4) == 0) {
            //generate ability

            CharacterNode capStone = tree.definition.newNode(tree);
            
            capStone.abilityDef = game.registry.abilityGenerator.generate(tree);

            capStone.position = CharacterTreeDef.Position.RADIAL;
            ret.capstone = capStone;
        } else {
            CharacterNode capStone = tree.definition.newNode(tree);

            capStone.stats.add(stat1);
            capStone.stats.add(stat2);
            capStone.position = CharacterTreeDef.Position.RADIAL;

            ret.capstone = capStone;
        }


        ret.length = 3;


        return ret;
    }

    public boolean isApplicable(CharacterTree tree) {
        throw new UnsupportedOperationException();
    }
    
    public CharacterTree generate() {
        throw new UnsupportedOperationException();
    }

}