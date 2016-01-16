
package templeoftheelements.player;

import com.samrj.devil.math.Vec2;
import generation.ProceduralGenerator;
import java.util.ArrayList;
import java.util.HashSet;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.collision.Creature;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public class CharacterWheel {
    private Player player;
    public ArrayList<CharacterNode> nodes; //all the nodes in the character wheel.
    public ArrayList<CharacterTree> trees; //The list of trees on the character wheel.
    
    public CharacterWheel(Player player) {
        nodes = new ArrayList<>();
        trees = new ArrayList<>();
        this.player = player;
        generate();
    }
    
    public void generate() {
        ArrayList<CharacterNode> curNodeRing = new ArrayList<>(); // the nodes in the current layer.
        
        
        
        for (int i = 0; i < 5; i++) {
            trees.add(new CharacterTree(game.registry.treeGenerator.generate()));
        }
        
        for (int i = 0; i < 20; i++) { //for each layer
            for (CharacterTree tree : trees) { //for each tree
                for (int j = 0; j <= i; j++) { //for each node
                    CharacterNode node = tree.definition.nodeGenerator.generate(tree); ///generate the node
                    curNodeRing.add(node); //add the node to our temporary list of nodes for this layer
                    tree.curLayerNodes.add(node); //add the node to its tree
                }
                tree.newLayer(i+2);
            }
            double angle, diff = Math.toRadians(360/ (double) curNodeRing.size()), offset;
            offset = -0.5 * diff * i;
            for (int j = 0; j < curNodeRing.size(); j++) {
                CharacterNode node = curNodeRing.get(j);
                angle = (diff * j) + offset;
                Vec2 position = new Vec2();
                position.x = (float) (65 * (i + 1) * Math.sin(angle));
                position.y = (float) (65 * (i + 1) * Math.cos(angle));
                node.setPosition(position);
                nodes.add(node);
            }
            curNodeRing.clear();
        }
        
        
    
    }
    
    public static enum GoalType {
        ABILITY, ABILITY_BOOST, PASSIVE, STAT, ONETIME;
    }
    
    public class CharacterTree {
        
        public ArrayList<CharacterNode> prevLayerNodes;
        public ArrayList<CharacterNode> curLayerNodes; //the nodes in this specific tree.
        public ArrayList<CharacterNode> nodes;
        public ArrayList<Goal> goals;
        public int layerSize;
        public CharacterTreeDef definition;
        
        public CharacterTree(CharacterTreeDef definition) {
            this.definition = definition;
            curLayerNodes = new ArrayList<>();
            nodes = new ArrayList<>();
            goals = new ArrayList<>();
            newLayer(1);
        }
        
        public CharacterTree(CharacterTreeDef definition, CharacterNode rootNode) {
            this.definition = definition;
            curLayerNodes = new ArrayList<>();
            curLayerNodes.add(rootNode);
            newLayer(1);
        }
        
        public void newLayer(int size) {
            prevLayerNodes = curLayerNodes;
            
            curLayerNodes = new ArrayList<>();
            layerSize = size;
        }
        
        public Creature getCreature() {
            return player.getCreature();
        }
        
        public class CharacterCluster {

        }

        public class AbilityCluster extends CharacterCluster {

        }

        public class Goal {
            public GoalType type;
            public CharacterTreeDef.Focus focus;
            public ArrayList<CharacterTreeDef.Detail> details;
            public boolean fulfilled;
            public int count;

            public Goal(GoalType type, CharacterTreeDef.Focus focus) {
                this.type = type;
                this.focus = focus;
                details = new ArrayList<>();
                fulfilled = false;
                count = 0;
            }
        }
        
    }
}
