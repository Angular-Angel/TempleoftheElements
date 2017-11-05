
package templeoftheelements.player;

import com.samrj.devil.math.Vec2;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.creature.Creature;
import templeoftheelements.creature.Ability;
import templeoftheelements.player.CharacterTreeDef.ClusterDefinition;
import templeoftheelements.player.CharacterTreeDef.NodeDefinition;
import templeoftheelements.player.Player;

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
        for (int i = 0; i < 5; i++) {
            trees.add(new CharacterTree(game.registry.treeGenerator.generate()));
        }
        
        
        
        for (int i = 0; i < 10; i++) { //for each layer
            for (CharacterTree tree : trees) { //for each tree
                int ring = i/5 +1; //What ring are we on?
                if (i % 5 == 0) { //Are we at the start f the ring?
                    tree.clusters.clear(); //if so, we need to generate clusters for it.
                    for (int j = 0; j < ring; j++) { 
                        //generate the cluster definition
                        tree.cluster = j;
                        tree.clusters.add(tree.definition.clusterGenerator.generate(tree));
                    }
                }
                
                for (int j = 0; j < tree.clusters.size(); j++) { //for each cluster
                    ClusterDefinition cluster = tree.clusters.get(j); //get the cluster definition
                    tree.cluster = j; //tell the tree which cluster we're generating.
                    switch (i % 5) { //where in the cluster are we?
                        case 0: //if we're at the start, make the entry node.
                            tree.layerSize = ring;
                            
                            //generate the lead-in node
                            NodeDefinition nodeDef = cluster.entry;
                            nodeDef.layer = i;
                            nodeDef.cluster = tree.cluster;

                            CharacterNode node = tree.definition.nodeGenerator.generate(nodeDef);
                            node.cluster = tree.cluster;
                            tree.layers.get(i).add(node);
                            break;
                        case 1:
                        case 2:
                        case 3:
                            //generate the bulk nodes
                            tree.layerSize = cluster.bulk.size() * (ring);
                            for (NodeDefinition bulkDef : cluster.bulk) {
                                bulkDef.layer = i;
                                bulkDef.cluster = tree.cluster;
                                node = tree.definition.nodeGenerator.generate(bulkDef);
                                node.cluster = tree.cluster;
                                tree.layers.get(i).add(node);
                            }
                            break;
                        case 4:
                            //generate the capstonecluster.bulk
                            tree.layerSize = ring;
                            cluster.capstone.layer = i;
                            cluster.capstone.cluster = tree.cluster;
                            node = tree.definition.nodeGenerator.generate(cluster.capstone);
                            node.cluster = tree.cluster;
                            tree.layers.get(i).add(node);
                            tree.cluster++;
                            break;
                    }   
                }
                tree.newLayer(); 
            }
            
        }
        
        for (int k = 0; k < trees.size(); k++) { //for each tree
            CharacterTree tree = trees.get(k); //get the tree
            for (int i = 0; i < tree.layers.size(); i++) { //for each layer
                ArrayList<CharacterNode> layer = tree.layers.get(i); //get the layer
                double angle; //the angle which we use to place our node relative to the center of the character wheel.
                double slice = Math.toRadians(360/(double) trees.size()); //the slice of the character wheel that this tree gets
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
        
        public int layerSize, cluster;
        public ArrayList<ClusterDefinition> clusters;
        public ArrayList<Ability> abilities;
        public CharacterTreeDef definition;
        public float spammables, situationals;
        
        public CharacterTree(CharacterTreeDef definition) {
            this.definition = definition;
            layers = new ArrayList<>();
            nodes = new ArrayList<>();
            clusters = new ArrayList<>();
            abilities = new ArrayList<>();
            newLayer();
        }
        
        public CharacterTree(CharacterTreeDef definition, CharacterNode rootNode) {
            this.definition = definition;
            clusters = new ArrayList<>();
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
