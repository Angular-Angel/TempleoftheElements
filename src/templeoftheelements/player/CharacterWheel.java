
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
        generate();
    }
    
    public void generate() {
        
        for (int i = 0; i < 5; i++) {
            CharacterTree tree = game.registry.treeGenerator.generate(this);
            tree.number = i;
            trees.add(tree);
        }
        
        double slice = Math.toRadians(360/(double) trees.size());
        
        //the slice of the character wheel that each tree gets.
        
        for (int i = 0; i < 2; i++) { //for each ring
            for (CharacterTree tree : trees) { //for each tree
                for (int j = 0; j < i + 1; j++) { //generate one cluster for the first ring, two for the second, and so on.
                    game.registry.clusterGenerator.modify(tree);
                }
                tree.curLayer = tree.layers.size() - 1;
            }
            
        }
        
        for (int k = 0; k < trees.size(); k++) { //for each tree
            CharacterTree tree = trees.get(k); //get the tree
            for (CharacterNode node : tree.nodes) { //for each node in the layer
                node.place(slice);
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
    
    public void addNode(CharacterNode node) {
        nodes.add(node);
    }
}
