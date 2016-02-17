import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import stat.*;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import generation.ProceduralGenerator;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;
import templeoftheelements.player.CharacterTreeDef.NodeDefinition;
import templeoftheelements.player.CharacterTreeDef.ClusterDefinition;

/**
 *
 * @author angle
 */
class MagicalStyleGenerator implements ProceduralGenerator<CharacterTreeDef> {
    
    //our random number generator;
    Random random = new Random();
    
    //an unused method from the interface. Sloppy, I know.
    public CharacterTreeDef generate(Object o) {
//        CharacterWheel wheel = (CharacterWheel) o;
        throw new UnsupportedOperationException();
    }
    
    //the main method. This generates the description of the character tree that 
    //the generator uses to generate the substance.
    public CharacterTreeDef generate() {
        
        //the elements this style uses for it's magic. 
        ArrayList<Element> elements = new ArrayList<>();
        
        //this block picks out two elements for the style to use.
        int numElements = game.registry.elements.size();
        for (int i = 0; i < 2; i++) {
            Element e = game.registry.elementList.get(random.nextInt(numElements));
            if (!elements.contains(e))
                elements.add(e);
            else i--;
        }
        
        //assigns the name of the style.
        String name = "Path of " + elements.get(0).name + " and " + elements.get(1).name;
        
        //a list of possible attributes for the style to be based off of.
        ArrayList<StatDescriptor> attributePool = new ArrayList<>();
        
        //the three basic mental attributes.
        attributePool.add(game.registry.statDescriptors.get("Intelligence"));
        attributePool.add(game.registry.statDescriptors.get("Spirit"));
        attributePool.add(game.registry.statDescriptors.get("Perception"));
        
        //adds the attributes associated with each element.
        for (Element e : elements) {
            attributePool.addAll(e.primaryAttributes);
        }
        
        //generates a new CharacterTreeDef to hold all this information.
        CharacterTreeDef ret = new CharacterTreeDef(name);
        
        ret.elements = elements;
        
        //now we need to pick out what kind of things this style focuses on doing.
        //First, we pick out all our options, weighting in favor of some things...
        ArrayList<CharacterTreeDef.Focus> focusPool = new ArrayList<>();
        
        //first, we have a chance to get any focus there is.
        focusPool.addAll(CharacterTreeDef.Focus.values());
        
        //Then theres an adfitional chance to get a focus associated with one of 
        //the elements used for this magical style. 
        for (Element e : elements) {
            focusPool.addAll(e.focuses);
        }
        
        //first, pick out a primary focus
        ret.primaryFocus = focusPool.get(random.nextInt(focusPool.size()));
        
        //then pick out a secondary focus
        ret.secondaryFocuses.add(focusPool.get(random.nextInt(focusPool.size())));
        
        //pick out attributes
        StatDescriptor attribute = attributePool.get(random.nextInt(attributePool.size()));
        while(attributePool.remove(attribute));
        ret.primaryAttributes.add(attribute);
        attribute = attributePool.get(random.nextInt(attributePool.size()));
        while(attributePool.remove(attribute));
        ret.primaryAttributes.add(attribute);
        
        CharacterTreeDef.Detail detail = CharacterTreeDef.Detail.values()[random.nextInt(CharacterTreeDef.Detail.values().length)];
        
        ret.details.add(detail);
        
        detail = CharacterTreeDef.Detail.values()[random.nextInt(CharacterTreeDef.Detail.values().length)];
        
        
        while (ret.details.contains(detail)) detail = CharacterTreeDef.Detail.values()[random.nextInt(CharacterTreeDef.Detail.values().length)];
        
        ret.details.add(detail);
        
        ret.secondaryAttributes.add(game.registry.statDescriptors.get("Added Mana"));
        
        if (ret.details.contains(CharacterTreeDef.Detail.SPEED_BASED)) 
        ret.secondaryAttributes.add(game.registry.statDescriptors.get("Added Speed"));
        
        if (ret.details.contains(CharacterTreeDef.Detail.TOUGHNESS_BASED) ||
            ret.details.contains(CharacterTreeDef.Detail.STAMINA_BASED)) 
        ret.secondaryAttributes.add(game.registry.statDescriptors.get("Added Stamina"));
        
        if (ret.details.contains(CharacterTreeDef.Detail.COSTS_HP)) 
        ret.secondaryAttributes.add(game.registry.statDescriptors.get("Added HP"));
        
        attributePool.clear();
        for (Element e : elements) {
            attributePool.addAll(e.secondaryAttributes);
        }
            
        while (ret.secondaryAttributes.size() < 3) {
            attribute = attributePool.get(random.nextInt(attributePool.size()));
            while(attributePool.remove(attribute));
            ret.secondaryAttributes.add(attribute);
        }
        
        ret.nodeGenerator = new NodeGenerator();
        ret.clusterGenerator = new ClusterGenerator();
        
        return ret;
    }
    
    public class ClusterGenerator implements ProceduralGenerator<ClusterDefinition> {
        
    
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
            if (random.nextInt(2) == 0) {
                //generate ability
                
                NodeDefinition capStone = tree.definition.newNode(tree);
                
                capStone.ability = new AbilityDefinition(generateMissile(tree));
                
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
        
        public MissileSpell generateMissile(CharacterWheel.CharacterTree tree) {
            String name;

            Element element = tree.definition.elements.get(random.nextInt(tree.definition.elements.size()));

            name = element.name;

            AttackDefinition missile;

            switch (random.nextInt(3)) {
                case 0: 
                    name += " Bolt";
                    missile = new AttackDefinition(name, new VectorCircle(1), element.name);
                    missile.addStat("Ranged Attack", new BinaryStat());
                    missile.addStat("Damage", new NumericStat(120));
                    missile.addStat("Size", new NumericStat(0.35));
                    missile.addStat("Speed", new NumericStat(50));
                    break;
                case 1: 
                    name += " Blast";
                    missile = new AttackDefinition(name, new VectorCircle(1), element.name);
                    missile.addStat("Ranged Attack", new BinaryStat());
                    missile.addStat("Damage", new NumericStat(260));
                    missile.addStat("Size", new NumericStat(0.70));
                    missile.addStat("Speed", new NumericStat(50));
                    break;
                case 2: 
                    name += " Ball";
                    missile = new AttackDefinition(name, new VectorCircle(1), element.name);
                    missile.addStat("Ranged Attack", new BinaryStat());
                    missile.addStat("Damage", new NumericStat(500));
                    missile.addStat("Size", new NumericStat(1));
                    missile.addStat("Speed", new NumericStat(50));
                    break;
            }

            MissileSpell ret = new MissileSpell(missile);
            ret.addStat("Mana Cost", new NumericStat(4));

            return ret;
        }
        
        public ClusterDefinition modify(ClusterDefinition cluster) {
            throw new UnsupportedOperationException();
        }

        public boolean isApplicable(ClusterDefinition cluster) {
            throw new UnsupportedOperationException();
        }
    
    }
    
    public class NodeGenerator implements ProceduralGenerator<CharacterNode> {
        
        public CharacterNode generate() {
            throw new UnsupportedOperationException();
        }
        
        public CharacterNode generate(Object o) {
            NodeDefinition nodeDef = (NodeDefinition) o; //the definition for the node we're making
            CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) nodeDef.tree; //the tree to which the node will belong
        
            Requirement req; //the variable that will keep track of what nodes this node requires
            
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
            System.out.println("" + nodeDef.layer + ": " + curLayerNodes.size() + ", " + tree.layerSize + ", " + prevLayerNodes.size() + ", " + num);
            
            switch (nodeDef.requirement.and) {
                case false:
                    if (prevLayerNodes.size() == 0) {
                        req = new AndRequirement();
                    } else if (prevLayerNodes.size() > num-1 && nodeDef.requirement.number == 2) {
                        req = new OrRequirement(prevLayerNodes.get(num), prevLayerNodes.get(num+1));
                    } else if (prevLayerNodes.size() > num) {
                        req = prevLayerNodes.get(num);
                    } else {
                        req = prevLayerNodes.get(0);
                    }
                    break;
                default: 
                    System.out.println(nodeDef.requirement);
            }

            CharacterNode node;
            
            //decide whether the node will give a stat boost or a new ability.
             if (nodeDef.ability == null) {
                node = new CharacterNode(req, tree);
            } else {

                node = new AbilityNode(req, tree, nodeDef.ability.ability);
            }
            
            for (StatDescriptor stat : nodeDef.stats) {
                node.addStat(stat.name, new NumericStat(stat.increase));
            }
            
            node.nodeDef = nodeDef;
            return node;
        }
    }
    
    public CharacterTreeDef modify(CharacterTreeDef tree) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isApplicable(CharacterTreeDef tree) {
        throw new UnsupportedOperationException();
    }
    
}