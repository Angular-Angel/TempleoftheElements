import stat.Stat;
import stat.StatContainer;
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
import com.samrj.devil.math.Vec2;

//This class generates descriptions of clusters if nodes for character trees.

public class ClusterGenerator implements GenerationProcedure<CharacterTree> {

     //our random number generator;
    Random random = new Random();

    public CharacterTree generate(Object o) {
        throw new UnsupportedOperationException();
    }
    
    private StatDescriptor randomStat(CharacterTree tree) {
        StatDescriptor stat;
        if (random.nextInt(3) > 2) {
            //Use a primary stat
            stat = tree.primaryAttributes.get(random.nextInt(tree.primaryAttributes.size()));
            if (stat == null) System.out.println(tree.primaryAttributes);
        } else {
            //use a secondary stat.
            stat = tree.secondaryAttributes.get(random.nextInt(tree.secondaryAttributes.size()));
            if (stat == null) System.out.println(tree.secondaryAttributes);
        }
        
        return stat;
    }
    
    private StatContainer randomStats(CharacterTree tree) {
        StatContainer ret = new StatContainer();
        
        int statpoints = 15;
        
        while (statpoints > 0) {
            StatDescriptor stat = randomStat(tree);
            int statIncrease = Math.min(random.nextInt(4) + 1, statpoints);
            if (ret.hasStat(stat))
                ((NumericStat) ret.getStat(stat)).modifyBase((float) stat.increase * statIncrease);
            else ret.addStat(stat, (float) stat.increase * statIncrease);
            statpoints -= statIncrease;
        }
        
        return ret;
    }

    @Override
    public CharacterTree modify(CharacterTree tree) {

        StatContainer stats1 = randomStats(tree), stats2 = randomStats(tree);
        
        CharacterNode node; //whichever node we're currently working on.
        
        
        // Generate the entry node
        if (tree.curLayer > 0) {
            node = new CharacterNode(tree.layers.get(tree.curLayer - 1).get(0), tree);
        } else node = new CharacterNode(tree);
        
        //Set the position of the entry node
        Vec2 position = new Vec2();
        position.x = (float) (65 * (tree.curLayer + 1) * Math.sin(tree.curAngle));
        position.y = (float) (65 * (tree.curLayer + 1) * Math.cos(tree.curAngle));
        node.setPosition(position);
        
        node.stats.mergeStats(stats1);
        node.stats.mergeStats(stats2);
        tree.addNode(node);
        
        CharacterNode entry = node; //remember our first node
        
        for (int i = 0; i < 3; i++) {
            node = new CharacterNode(node, tree);
            node.stats.addAllStats(stats1);
            
            position = new Vec2();
            position.x = (float) (65 * (tree.curLayer + 2 + i) * Math.sin(tree.curAngle));
            position.y = (float) (65 * (tree.curLayer + 2 + i) * Math.cos(tree.curAngle));
            position.x += (float) (30 * Math.sin(tree.curAngle - 30));
            position.y += (float) (30 * Math.cos(tree.curAngle - 30));
            node.setPosition(position);
            
            tree.addNode(node);
        }
        
        CharacterNode endLine1 = node;
        
        node = entry; //return to the first node of the cluseter, so we end up with two separate lines of ndes.
        
        for (int i = 0; i < 3; i++) {
            node = new CharacterNode(node, tree);
            node.stats.addAllStats(stats2);
            
            position = new Vec2();
            position.x = (float) (65 * (tree.curLayer + 2 + i) * Math.sin(tree.curAngle));
            position.y = (float) (65 * (tree.curLayer + 2 + i) * Math.cos(tree.curAngle));
            position.x += (float) (30 * Math.sin(tree.curAngle + 30));
            position.y += (float) (30 * Math.cos(tree.curAngle + 30));
            node.setPosition(position);
            
            tree.addNode(node);
        }
        
        CharacterNode endLine2 = node;

        //now for the capstone.
        if (random.nextInt(4) == 0) {
            //generate skill

            node = new SkillNode(new OrRequirement(endLine1, endLine2), tree, 
                game.registry.abilityGenerator.generate(tree));
        } else {
            node = new CharacterNode(new OrRequirement(endLine1, endLine2), tree);

            node.stats.mergeStats(stats1);
            node.stats.mergeStats(stats2);
        }
        position = new Vec2();
        position.x = (float) (65 * (tree.curLayer + 5) * Math.sin(tree.curAngle));
        position.y = (float) (65 * (tree.curLayer + 5) * Math.cos(tree.curAngle));
        node.setPosition(position);
        
        tree.addNode(node);

        return tree;
    }

    public boolean isApplicable(CharacterTree tree) {
        throw new UnsupportedOperationException();
    }
    
    public CharacterTree generate() {
        throw new UnsupportedOperationException();
    }

}