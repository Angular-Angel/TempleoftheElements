
package templeoftheelements.player;

import com.samrj.devil.math.Vec2;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.creature.Creature;

/**
 *
 * @author angle
 */


public class CharacterWheel {
    private final Player player;
    public ArrayList<CharacterNode> nodes; //all the nodes in the character wheel.
    public ArrayList<CharacterTree> trees; //The list of trees on the character wheel.
    
    public final static int NUM_SKILL_TREES = 5;
    
    public CharacterWheel(Player player) {
        nodes = new ArrayList<>();
        trees = new ArrayList<>();
        this.player = player;
        generate();
    }
    
    public void generate() {
        
        double slice = Math.toRadians(360/(double) NUM_SKILL_TREES);
        
        for (int i = 0; i < NUM_SKILL_TREES; i++) {
            CharacterTree tree = game.registry.treeGenerator.generate(this);
            tree.number = i;
            tree.arcLength = slice;
            trees.add(tree);
        }
        
        //the slice of the character wheel that each tree gets.
        
        for (int i = 0; i < 2; i++) { //for each ring of five layers
            for (CharacterTree tree : trees) { //for each tree
                tree.addRing();
                tree.curLayer = i*5;
                for (int j = 0; j < i + 1; j++) { //generate one cluster for the first ring, two for the second, and so on.
                    double diff = (slice/(double) (i + 1)); // The arc between each node in this layer of the tree.
                    tree.curAngle = (tree.number * slice) + (diff * j) - 0.5 * diff * i;
                    game.registry.clusterGenerator.modify(tree);
                }
            }
            
        }
    
    }
    
    public Creature getCreature() {
        return player.getCreature();
    }
    
    public void addNode(CharacterNode node) {
        nodes.add(node);
    }
}
