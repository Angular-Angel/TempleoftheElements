
package templeoftheelements.player;

import com.samrj.devil.math.Vec2;
import java.util.ArrayList;
import java.util.HashSet;
import templeoftheelements.collision.Creature;

/**
 *
 * @author angle
 */


public class CharacterWheel {
    private Player player;
    public HashSet<CharacterNode> nodes; //all the nodes in the character wheel.
    
    public CharacterWheel(Player player) {
        nodes = new HashSet<>();
        this.player = player;
        generate();
    }
    
    public void generate() {
        ArrayList<CharacterNode> curNodeRing = new ArrayList<>(); // the nodes in the current layer.
        
        CharacterNode baseNode = new CharacterNode(new AndRequirement(), new CharacterTree());
        
        baseNode.acquire();
        nodes.add(baseNode);
        
        ArrayList<CharacterTree> trees = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            trees.add(new CharacterTree(baseNode));
        }
        
        for (int i = 0; i < 20; i++) {
            for (CharacterTree tree : trees) {
                for (int j = 0; j <= i; j++) 
                    curNodeRing.add(tree.generateNode());
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
        
        private ArrayList<CharacterNode> prevLayerNodes;
        private ArrayList<CharacterNode> curLayerNodes; //the nodes in this specific tree.
        private int layerSize;
        private CharacterTreeDef def;
        
        public CharacterTree() {}
        
        public CharacterTree(CharacterNode rootNode) {
            curLayerNodes = new ArrayList<>();
            curLayerNodes.add(rootNode);
            newLayer(1);
        }
        
        public CharacterNode generateNode() {
            double num = (curLayerNodes.size() / (double) layerSize) * prevLayerNodes.size();
            Requirement req;
            if (num < prevLayerNodes.size() - 1)
                req = new OrRequirement(prevLayerNodes.get((int) num), prevLayerNodes.get(((int) num) + 1));
            else req = prevLayerNodes.get((int) num);
            CharacterNode node = new CharacterNode(req, this);
            curLayerNodes.add(node);
            return node;
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
