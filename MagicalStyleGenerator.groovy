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

/**
 *
 * @author angle
 */
class MagicalStyleGenerator implements ProceduralGenerator<CharacterTreeDef> {
    
    //our random number generator;
    Random random = new Random();
    
    //an unused method from the interface. Sloppy, I know.
    public CharacterTreeDef generate(Object o) {
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
        ArrayList<String> attributePool = new ArrayList<>();
        
        //the three basic mental attributes.
        attributePool.add("Intelligence");
        attributePool.add("Spirit");
        attributePool.add("Perception");
        
        //adds the attributes associated with each element.
        for (Element e : elements) {
            attributePool.addAll(e.attributes);
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
        
        String attribute = attributePool.get(random.nextInt(attributePool.size()));
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
        
        
        if (ret.details.contains(CharacterTreeDef.Detail.SPEED_BASED)) 
        ret.secondaryAttributes.add("Dexterity");
        
        if (ret.details.contains(CharacterTreeDef.Detail.TOUGHNESS_BASED) ||
            ret.details.contains(CharacterTreeDef.Detail.STAMINA_BASED)) 
        ret.secondaryAttributes.add("Constitution");
        
        if (ret.details.contains(CharacterTreeDef.Detail.COSTS_HP)) 
        ret.secondaryAttributes.add("Max HP");
        
        ret.nodeGenerator = new NodeGenerator();
        
        return ret;
    }
    
    public class NodeGenerator implements ProceduralGenerator<CharacterNode> {
        
        public CharacterNode generate() {
            throw new UnsupportedOperationException();
        }
        
        public CharacterNode generate(Object o) {
            CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) o;
        
            double num = (tree.curLayerNodes.size() / (double) tree.layerSize) * tree.prevLayerNodes.size();
            Requirement req;
            if (tree.prevLayerNodes.size() == 0) {
                req = new AndRequirement();
            } else if (num < tree.prevLayerNodes.size() - 1)
                req = new OrRequirement(tree.prevLayerNodes.get((int) num), tree.prevLayerNodes.get(((int) num) + 1));
            else req = tree.prevLayerNodes.get((int) num);


            if (random.nextInt(2) == 0) {
                CharacterNode node = new CharacterNode(req, tree);
                String stat;

                if (tree.definition.secondaryAttributes.size() > 0) {

                    if (random.nextInt(4) < 3)
                        stat = tree.definition.primaryAttributes.get(random.nextInt(tree.definition.primaryAttributes.size()));
                    else stat = tree.definition.secondaryAttributes.get(random.nextInt(tree.definition.secondaryAttributes.size()));

                }
                else stat = tree.definition.primaryAttributes.get(random.nextInt(tree.definition.primaryAttributes.size()));

                node.addStat(stat, new NumericStat(1));

                return node;
            } else {
                Ability ability;

                if (true) {
                    ability = generateMissile(tree);
                }

                AbilityNode node = new AbilityNode(req, tree, ability);
            }
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
                    missile.addStat("Damage", new NumericStat(12));
                    missile.addStat("Size", new NumericStat(0.35));
                    missile.addStat("Speed", new NumericStat(50));
                    break;
                case 1: 
                    name += " Blast";
                    missile = new AttackDefinition(name, new VectorCircle(1), element.name);
                    missile.addStat("Ranged Attack", new BinaryStat());
                    missile.addStat("Damage", new NumericStat(26));
                    missile.addStat("Size", new NumericStat(0.70));
                    missile.addStat("Speed", new NumericStat(50));
                    break;
                case 2: 
                    name += " Ball";
                    missile = new AttackDefinition(name, new VectorCircle(1), element.name);
                    missile.addStat("Ranged Attack", new BinaryStat());
                    missile.addStat("Damage", new NumericStat(30));
                    missile.addStat("Size", new NumericStat(1));
                    missile.addStat("Speed", new NumericStat(50));
                    break;
            }

            MissileSpell ret = new MissileSpell(missile);
            ret.addStat("Mana Cost", new NumericStat(4));

            return ret;
        }
    }
    
    public CharacterTreeDef modify(CharacterTreeDef tree) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isApplicable(CharacterTreeDef tree) {
        throw new UnsupportedOperationException();
    }
    
}