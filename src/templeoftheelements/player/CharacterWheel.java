
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
    public int statNodes = 0;
    public int abilityNodes = 0;
    public int activeNodes = 0;
    public int passiveNodes = 0;
    
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
        
        
        
        for (int i = 1; i <= 2; i++) { //for each layer
            for (CharacterTree tree : trees) { //for each tree
                tree.newLayer(i);
                for (int j = 0; j <= i; j++) { //for each cluster
//                    CharacterNode node = tree.definition.nodeGenerator.generate(tree); ///generate the node
//                    curNodeRing.add(node); //add the node to our temporary list of nodes for this layer
//                    tree.curLayerNodes.add(node); //add the node to its tree
                }
//                
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
    
    
    public class CharacterTree {
        
        public ArrayList<CharacterNode> prevLayerNodes;
        public ArrayList<CharacterNode> curLayerNodes; //the nodes in this specific tree.
        public ArrayList<CharacterNode> nodes;
        public int layerSize;
        public CharacterTreeDef definition;
        
        public CharacterTree(CharacterTreeDef definition) {
            this.definition = definition;
            curLayerNodes = new ArrayList<>();
            nodes = new ArrayList<>();
            newLayer(1);
        }
        
        public CharacterTree(CharacterTreeDef definition, CharacterNode rootNode) {
            this.definition = definition;
            curLayerNodes = new ArrayList<>();
            curLayerNodes.add(rootNode);
            newLayer(1);
        }
        
        public void newLayer(int clusters) {
            prevLayerNodes = curLayerNodes;
            curLayerNodes = new ArrayList<>();
        }
        
        public Creature getCreature() {
            return player.getCreature();
        }
        
        public class NodeCluster {
            public ArrayList<CharacterNode> nodes;
            public ArrayList<CharacterNode> endNodes;
            
            public NodeCluster() {
                nodes = new ArrayList<>();
                endNodes = new ArrayList<>();
            }
        }

        
    }
}
