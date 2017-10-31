import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import templeoftheelements.effect.*;
import stat.*;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import generation.ProceduralGenerator;
import templeoftheelements.player.characterwheel.CharacterWheel;
import templeoftheelements.player.characterwheel.CharacterTreeDef;
import templeoftheelements.player.characterwheel.CharacterTreeDef.NodeDefinition;
import templeoftheelements.player.characterwheel.CharacterTreeDef.ClusterDefinition;

//This class generates descriptions of clusters if nodes for character trees.

public class ClusterGenerator implements ProceduralGenerator<ClusterDefinition> {

     //our random number generator;
    Random random = new Random();
    
    public ClusterDefinition generate() {
        throw new UnsupportedOperationException();
    }

    public ClusterDefinition generate(Object o) {

        CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) o;

        //first, generate the capstone. 

        ClusterDefinition ret = new ClusterDefinition();

        //The stats our cluster will feature.
        StatDescriptor stat1, stat2;

        //the pool of attributes to draw from.
        ArrayList<StatDescriptor> attributePool = new ArrayList<>();

        attributePool.addAll(tree.definition.secondaryAttributes);

        stat1 = attributePool.get(random.nextInt(attributePool.size()));
        attributePool.remove(stat1);

        stat2 = attributePool.get(random.nextInt(attributePool.size()));

        ret.entry = tree.definition.newNode(tree);
        ret.entry.position = CharacterTreeDef.Position.RADIAL;
        ret.entry.stats.add(stat1);

        if (tree.cluster > 0 && tree.layerSize > tree.cluster)
            ret.entry.requirement = new CharacterTreeDef.Requirement(2);
        else
            ret.entry.requirement = new CharacterTreeDef.Requirement(1);

        NodeDefinition bulk = tree.definition.newNode(tree);
        bulk.requirement = new CharacterTreeDef.Requirement(1);
        bulk.position = CharacterTreeDef.Position.CLOCKWISE20;
        bulk.stats.add(stat1);
        ret.bulk.add(bulk);

        bulk = tree.definition.newNode(tree);
        bulk.requirement = new CharacterTreeDef.Requirement(1);
        bulk.position = CharacterTreeDef.Position.COUNTERCLOCKWISE20;
        bulk.stats.add(stat2);
        ret.bulk.add(bulk);

        //now for the capstone.
        if (random.nextInt(4) == 0) {
            //generate ability

            NodeDefinition capStone = tree.definition.newNode(tree);
            
            capStone.abilityDef = game.registry.abilityGenerator.generate(tree);

            capStone.requirement = new CharacterTreeDef.Requirement(2);
            capStone.position = CharacterTreeDef.Position.RADIAL;
            ret.capstone = capStone;
        } else {
            NodeDefinition capStone = tree.definition.newNode(tree);

            capStone.stats.add(stat1);
            capStone.stats.add(stat2);
            capStone.requirement = new CharacterTreeDef.Requirement(2);
            capStone.position = CharacterTreeDef.Position.RADIAL;

            ret.capstone = capStone;
        }


        ret.length = 3;


        return ret;
    }

//        public Ability generateAbility(CharacterWheel.CharacterTree tree) {
//            
//        }

    public ClusterDefinition modify(ClusterDefinition cluster) {
        throw new UnsupportedOperationException();
    }

    public boolean isApplicable(ClusterDefinition cluster) {
        throw new UnsupportedOperationException();
    }

}