import stat.Stat;
import stat.NumericStat;
import stat.StatDescriptor;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import generation.GenerationProcedure;
import templeoftheelements.player.CharacterTree;
import templeoftheelements.player.CharacterNode;
import templeoftheelements.player.SkillNode;
import templeoftheelements.player.OrRequirement;

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
            stat = characterTree.primaryAttributes.get(random.nextInt(characterTree.primaryAttributes.size()));
        } else {
            //use a secondary stat.
            stat = characterTree.secondaryAttributes.get(random.nextInt(characterTree.secondaryAttributes.size()));
        }
        
        return stat;
    }

    public CharacterTree modify(CharacterTree characterTree) {

        StatDescriptor stat1 = randomStat(characterTree), stat2 = randomStat(characterTree);

        CharacterNode node = new CharacterNode(characterTree);
        node.addStat(stat1.identifier, new NumericStat(stat1.increase));
        node.addStat(stat2.identifier, new NumericStat(stat2.increase));
        
        CharacterNode entry = node; //remember our first node
        
        for (int i = 0; i < 3; i++) {
            node = new CharacterNode(node, characterTree);
            node.addStat(stat1.identifier, new NumericStat(stat1.increase));
        }
        
        CharacterNode endLine1 = node;
        
        node = entry; //return to the first node of the cluseter, so we end up with two separate lines of ndes.
        
        for (int i = 0; i < 3; i++) {
            node = new CharacterNode(node, characterTree);
            node.addStat(stat2.identifier, new NumericStat(stat2.increase));
        }
        
        CharacterNode endLine2 = node;

        //now for the capstone.
        if (random.nextInt(4) == 0) {
            //generate skill

            node = new SkillNode(new OrRequirement(endLine1, endLine2), characterTree, 
                game.registry.abilityGenerator.generate(characterTree));

        } else {
            node = new CharacterNode(new OrRequirement(endLine1, endLine2), characterTree);

            node.addStat(stat1.identifier, new NumericStat(stat1.increase));
            node.addStat(stat2.identifier, new NumericStat(stat2.increase));
        }


        return characterTree;
    }

    public boolean isApplicable(CharacterTree tree) {
        throw new UnsupportedOperationException();
    }
    
    public CharacterTree generate() {
        throw new UnsupportedOperationException();
    }

}