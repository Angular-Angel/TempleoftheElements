
package templeoftheelements.player;

import com.samrj.devil.math.Vec2;
import generation.ProceduralGenerator;
import java.util.ArrayList;
import java.util.HashSet;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.collision.Creature;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.CharacterTreeDef.ClusterDefinition;
import templeoftheelements.player.CharacterTreeDef.NodeDefinition;

/**
 *
 * @author angle
 */


public class CharacterWheel {
    private Player player;
    public ArrayList<CharacterNode> nodes; //all the nodes in the character wheel.
    public ArrayList<CharacterTree> trees; //The list of trees on the character wheel.
    public ArrayList<ArrayList<CharacterNode>> nodeWheel;
    public int statNodes = 0;
    public int abilityNodes = 0;
    public int activeNodes = 0;
    public int passiveNodes = 0;
    
    public CharacterWheel(Player player) {
        nodes = new ArrayList<>();
        trees = new ArrayList<>();
        nodeWheel = new ArrayList<>();
        this.player = player;
        generate();
    }
    
    public void generate() {
        ArrayList<CharacterNode> curNodeRing = new ArrayList<>(); // the nodes in the current layer.
        
        
        
        for (int i = 0; i < 5; i++) {
            trees.add(new CharacterTree(game.registry.treeGenerator.generate()));
        }
        
        
        
        for (int i = 0; i < 1; i++) { //for each layer
            for (CharacterTree tree : trees) { //for each tree
                for (int j = 0; j <= i; j++) { //for each cluster
                    int k = i*5;
                    //generate the cluster definition
                    ClusterDefinition cluster = tree.definition.clusterGenerator.generate(tree); 
                    
                    //generate the lead-in node
                    NodeDefinition nodeDef = cluster.bulk.get(0);
                    tree.layerSize = 1;
                    
                    CharacterNode node = tree.definition.nodeGenerator.generate(nodeDef);
                    tree.curLayerNodes.add(node);
                    nodeWheel.add(new ArrayList<>());
                    nodeWheel.get(k).add(node);
                    k++;
                    tree.newLayer();
                    
                    //generate the bulk nodes
                    tree.layerSize = cluster.bulk.size();
                    for (int l = 0; l < cluster.length; l++) {
                        for (NodeDefinition bulkDef : cluster.bulk) {
                            node = tree.definition.nodeGenerator.generate(bulkDef);
                            tree.curLayerNodes.add(node);
                            nodeWheel.add(new ArrayList<>());
                            nodeWheel.get(k).add(node);
                        }
                        k++;
                        tree.newLayer();
                    }
                    
                    //generate the capstonecluster.bulk
                    tree.layerSize = 1;
                    node = tree.definition.nodeGenerator.generate(cluster.capstone);
                    tree.curLayerNodes.add(node);
                    nodeWheel.add(new ArrayList<>());
                    nodeWheel.get(k).add(node);
                    tree.newLayer();
                }
//                
            }
            
        }
        
        for (int i = 0; i < nodeWheel.size(); i++) {
            ArrayList<CharacterNode> layer = nodeWheel.get(i);
            double angle, diff = Math.toRadians(360/ (double) layer.size()), offset;
            offset = 0 * diff * i;
            for (int j = 0; j < layer.size(); j++) {
                CharacterNode node = layer.get(j);
                angle = (diff * j) + offset;
                Vec2 position = new Vec2();
                position.x = (float) (65 * (i + 1) * Math.sin(angle));
                position.y = (float) (65 * (i + 1) * Math.cos(angle));
                node.setPosition(position);
                nodes.add(node);
            }
            
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
            newLayer();
        }
        
        public CharacterTree(CharacterTreeDef definition, CharacterNode rootNode) {
            this.definition = definition;
            curLayerNodes = new ArrayList<>();
            curLayerNodes.add(rootNode);
            newLayer();
        }
        
        public void newLayer() {
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
