
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
        
        
        
        for (int i = 0; i < 3; i++) { //for each layer
            for (CharacterTree tree : trees) { //for each tree
                int clusterNum = 0;
                for (int j = 0; j <= i; j++) { //for each cluster
                    int k = i*5;
                    //generate the cluster definition
                    ClusterDefinition cluster = tree.definition.clusterGenerator.generate(tree); 
                    
                    //generate the lead-in node
                    NodeDefinition nodeDef = cluster.entry;
                    nodeDef.layer = k;
                    tree.layerSize = 1;
                    
                    CharacterNode node = tree.definition.nodeGenerator.generate(nodeDef);
                    node.cluster = clusterNum;
                    tree.layers.get(k).add(node);
                    k++;
                    tree.newLayer();
                    
                    //generate the bulk nodes
                    tree.layerSize = cluster.bulk.size();
                    for (int l = 0; l < cluster.length; l++) {
                        for (NodeDefinition bulkDef : cluster.bulk) {
                            bulkDef.layer = k;
                            node = tree.definition.nodeGenerator.generate(bulkDef);
                            node.cluster = clusterNum;
                            tree.layers.get(k).add(node);
                        }
                        k++;
                        tree.newLayer();
                    }
                    
                    //generate the capstonecluster.bulk
                    tree.layerSize = 1;
                    cluster.capstone.layer = k;
                    node = tree.definition.nodeGenerator.generate(cluster.capstone);
                    node.cluster = clusterNum;
                    tree.layers.get(k).add(node);
                    tree.newLayer();
                    clusterNum++;
                }
//                
            }
            
        }
        for (int k = 0; k < trees.size(); k++) { //for each tree
            CharacterTree tree = trees.get(k); //get the tree
            for (int i = 0; i < tree.layers.size(); i++) { //for each layer
                ArrayList<CharacterNode> layer = tree.layers.get(i); //get the layer
                double angle; //the angle which we use to place our node relative to the center of the character wheel.
                double slice = Math.toRadians(360/(double) trees.size()); //the slice of the character wheel that this tree gets
                System.out.println(slice);
                double diff = (slice/(double) layer.size()); // The arc between each node in this layer of the tree.
                for (int j = 0; j < layer.size(); j++) { //for each node in the layer
                    CharacterNode node = layer.get(j); //get the node 
                    int ring = (int) (Math.floor(i/5)+1); //figure out which ring we're in.
                    //offset = diff * (Math.floor(i/5));
                    Vec2 position;
                    switch (node.nodeDef.position) {
                        case RADIAL:
                            angle = (k * slice) + (diff * j);
                            angle -= 0.5 * diff * Math.floor(i/5);
                            position = new Vec2();
                            position.x = (float) (65 * (i + 1) * Math.sin(angle));
                            position.y = (float) (65 * (i + 1) * Math.cos(angle));
                            node.setPosition(position);
                            nodes.add(node);
                            break;
                        case CLOCKWISE20:
                            angle = (k * ring + node.cluster) * Math.toRadians(360/(double) (trees.size() * ring));
                            angle -= 0.5 * Math.toRadians(360/(double) (trees.size() * ring)) * Math.floor(i/5);
                            position = new Vec2();
                            position.x = (float) (65 * (i + 1) * Math.sin(angle));
                            position.y = (float) (65 * (i + 1) * Math.cos(angle));
                            position.x += (float) (30 * Math.sin(angle-30));
                            position.y += (float) (30 * Math.cos(angle-30));
                            node.setPosition(position);
                            nodes.add(node);
                            break;
                        case COUNTERCLOCKWISE20:
                            angle = (k * ring + node.cluster) * Math.toRadians(360/ (double) (trees.size() * ring));
                            angle -= 0.5 * Math.toRadians(360/(double) (trees.size() * ring)) * Math.floor(i/5);
                            position = new Vec2();
                            position.x = (float) (65 * (i + 1) * Math.sin(angle));
                            position.y = (float) (65 * (i + 1) * Math.cos(angle));
                            position.x += (float) (30 * Math.sin(angle+30));
                            position.y += (float) (30 * Math.cos(angle+30));
                            node.setPosition(position);
                            nodes.add(node);
                            break;
                    }
                }

            }
        }
        
        
    
    }
    
    
    public class CharacterTree {
        
        public ArrayList<ArrayList<CharacterNode>> layers;
        public ArrayList<CharacterNode> nodes;
        
        public int layerSize;
        public CharacterTreeDef definition;
        
        public CharacterTree(CharacterTreeDef definition) {
            this.definition = definition;
            layers = new ArrayList<>();
            nodes = new ArrayList<>();
            newLayer();
        }
        
        public CharacterTree(CharacterTreeDef definition, CharacterNode rootNode) {
            this.definition = definition;
            layers = new ArrayList<>();
            layers.add(new ArrayList<>());
            layers.get(0).add(rootNode);
            newLayer();
        }
        
        public void newLayer() {
            layers.add(new ArrayList<>());
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
