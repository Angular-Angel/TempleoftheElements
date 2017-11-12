
package templeoftheelements.player;

import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.creature.Creature;

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
    }
    
    public void generate() {
        for (int i = 0; i < 5; i++) {
            trees.add(game.registry.treeGenerator.generate(this));
        }
        
        //the slice of the character wheel that each tree gets.
        double slice = Math.toRadians(360/(double) trees.size());
        
        for (int i = 0; i < 2; i++) { //for each ring
            for (CharacterTree tree : trees) { //for each tree
                for (int j = 0; j < i + 1; j++) { //generate one cluster for the first ring, two for the second, and so on.
                    game.registry.clusterGenerator.modify(tree);
                }
            }
        }
        
        /*for (int i = 0; i < 10; i++) { //for each layer
            for (CharacterTree tree : trees) { //for each tree
                int ring = i/5 +1; //What ring are we on?
                if (i % 5 == 0) { //Are we at the start f the ring?
                    for (int j = 0; j < ring; j++) { 
                        //generate the cluster definition
                        tree.cluster = j;
                        tree.clusters.add(tree.definition.clusterGenerator.generate(tree));
                    }
                }
                
                for (int j = 0; j < tree.clusters.size(); j++) { //for each cluster
                    NodeCluster cluster = tree.clusters.get(j); //get the cluster definition
                    tree.cluster = j; //tell the tree which cluster we're generating.
                    switch (i % 5) { //where in the cluster are we?
                        case 0: //if we're at the start, make the entry node.
                            tree.layerSize = ring;
                            
                            //generate the lead-in node
                            CharacterNode entryNode = cluster.entry;
                            entryNode.layer = i;
                            entryNode.cluster = tree.cluster;
                            tree.layers.get(i).add(entryNode);
                            break;
                        case 1:
                        case 2:
                        case 3:
                            //generate the bulk nodes
                            tree.layerSize = cluster.nodes.size() * (ring);
                            for (CharacterNode bulkNode : cluster.nodes) {
                                
                                bulkNode.layer = i;
                                bulkNode.cluster = tree.cluster;
                                tree.layers.get(i).add(bulkNode);
                            }
                            break;
                        case 4:
                            //generate the capstonecluster.bulk
                            tree.layerSize = ring;
                            cluster.capstone.layer = i;
                            cluster.capstone.cluster = tree.cluster;
                            CharacterNode capstoneNode = cluster.capstone;
                            capstoneNode.cluster = tree.cluster;
                            tree.layers.get(i).add(capstoneNode);
                            tree.cluster++;
                            break;
                    }   
                }
                tree.newLayer(); 
            }
            
        }*/
        
        for (int k = 0; k < trees.size(); k++) { //for each tree
            CharacterTree tree = trees.get(k); //get the tree
            for (CharacterNode node : tree.nodes) { //for each node in the layer
                
            }
            
            
            /*for (int i = 0; i < tree.layers.size(); i++) { //for each layer
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
            }*/
        }
    
    }
    
    public Creature getCreature() {
            return player.getCreature();
        }
}
