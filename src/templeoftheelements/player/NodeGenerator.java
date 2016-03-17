/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.player;

import generation.GenerationProcedure;
import generation.ProceduralGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import stat.NumericStat;
import stat.StatDescriptor;
import templeoftheelements.CreatureDefinition;
import templeoftheelements.Spell;
import templeoftheelements.player.CharacterTreeDef.NodeDefinition;

/**
 *
 * @author angle
 */
public class NodeGenerator implements ProceduralGenerator<CharacterNode> {

    Random random = new Random();
    HashMap<Spell.Detail, GenerationProcedure<CharacterNode>> procedures = new HashMap<>();

    @Override
    public CharacterNode generate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addProcedure(Spell.Detail detail, GenerationProcedure<CharacterNode> procedure) {
        procedures.put(detail, procedure);
    }
    
    @Override
    public CharacterNode generate(Object o) {
        
        NodeDefinition nodeDef = (NodeDefinition) o; //the definition for the node we're making
        CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) nodeDef.tree; //the tree to which the node will belong

        Requirement req = new AndRequirement(); //the variable that will keep track of what nodes this node requires

        //the layer we're gonna add this node too.
        ArrayList<CharacterNode> curLayerNodes = tree.layers.get(nodeDef.layer); 

        //the layer that contains the nodes upon which this node will depend.
        ArrayList<CharacterNode> prevLayerNodes;

        if (nodeDef.layer >= 1)
            prevLayerNodes = tree.layers.get(nodeDef.layer-1); 
        else
            prevLayerNodes = new ArrayList<>(); 

        //This variable helps determine which nodes this node will require.
        int num = curLayerNodes.size() * prevLayerNodes.size() / tree.layerSize;

       if (prevLayerNodes.size() == 0) {
            req = new AndRequirement();
        } else if (prevLayerNodes.size() > num-1 && nodeDef.requirement.number == 2) {
            req = new OrRequirement(prevLayerNodes.get(num), prevLayerNodes.get(num+1));
        } else if (prevLayerNodes.size() > num) {
            req = prevLayerNodes.get(num);
        } else {
            req = prevLayerNodes.get(0);
        }
       
        CharacterNode node = null;

        //decide whether the node will give a stat boost or a new ability.
         if (nodeDef.ability == null) {
            node = new CharacterNode(req, tree);
        } else {
            for (Spell.Detail detail : nodeDef.ability.details) {
                if (procedures.containsKey(detail)) {
                    if (node == null) {
                        node = procedures.get(detail).generate(o);
                        node.requirements = req;
                    } else
                        node = procedures.get(detail).modify(node);
                }
            }
        }

        for (StatDescriptor stat : nodeDef.stats) {
            node.addStat(stat.name, new NumericStat(stat.increase));
        }

        node.nodeDef = nodeDef;
        return node;
    }
    
}
