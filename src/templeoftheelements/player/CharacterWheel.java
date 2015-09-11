
package templeoftheelements.player;

import com.samrj.devil.math.Vec2;
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
    public HashSet<CharacterNode> nodes; //all the nodes in the character wheel.
    public HashSet<Renderable> renderables; //all the non-node requirements.
    
    public CharacterWheel(Player player) {
        nodes = new HashSet<>();
        renderables = new HashSet<>();
        this.player = player;
        generate();
    }
    
    public void generate() {
        ArrayList<CharacterNode> curNodeRing = new ArrayList<>(); // the nodes in the current layer.
        
        ArrayList<CharacterTree> trees = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            trees.add(new CharacterTree(game.registry.treeGenerator.genCharacterTreeDef()));
        }
        
        for (int i = 0; i < 20; i++) {
            for (CharacterTree tree : trees) {
                for (int j = 0; j <= i; j++) {
                    CharacterNode node = game.registry.treeGenerator.generateNode(tree);
                    curNodeRing.add(node);
                    tree.curLayerNodes.add(node);
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
    
    public class CharacterTree {
        
        public ArrayList<CharacterNode> prevLayerNodes;
        public ArrayList<CharacterNode> curLayerNodes; //the nodes in this specific tree.
        public int layerSize;
        public CharacterTreeDef definition;
        
        public CharacterTree(CharacterTreeDef definition) {
            this.definition = definition;
            curLayerNodes = new ArrayList<>();
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
        
        public Creature getPlayer() {
            return player.getCreature();
        }
        
    }
}
