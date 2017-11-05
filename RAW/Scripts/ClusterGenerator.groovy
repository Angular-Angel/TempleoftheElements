import stat.StatDescriptor;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import generation.ProceduralGenerator;
import templeoftheelements.player.CharacterWheel;
import templeoftheelements.player.CharacterTreeDef;

//This class generates descriptions of clusters if nodes for character trees.

public class ClusterGenerator implements ProceduralGenerator<CharacterTreeDef.ClusterDefinition> {

     //our random number generator;
    Random random = new Random();

    public CharacterTreeDef.ClusterDefinition generate(Object o) {

        CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) o;

        //first, generate the ClusterDefinition. 

        CharacterTreeDef.ClusterDefinition ret = new CharacterTreeDef.ClusterDefinition();

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

        CharacterTreeDef.NodeDefinition bulk = tree.definition.newNode(tree);
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

            CharacterTreeDef.NodeDefinition capStone = tree.definition.newNode(tree);
            
            capStone.abilityDef = game.registry.abilityGenerator.generate(tree);

            capStone.requirement = new CharacterTreeDef.Requirement(2);
            capStone.position = CharacterTreeDef.Position.RADIAL;
            ret.capstone = capStone;
        } else {
            CharacterTreeDef.NodeDefinition capStone = tree.definition.newNode(tree);

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

    public CharacterTreeDef.ClusterDefinition modify(CharacterTreeDef.ClusterDefinition cluster) {
        throw new UnsupportedOperationException();
    }

    public boolean isApplicable(CharacterTreeDef.ClusterDefinition cluster) {
        throw new UnsupportedOperationException();
    }
    
    public CharacterTreeDef.ClusterDefinition generate() {
        throw new UnsupportedOperationException();
    }

}