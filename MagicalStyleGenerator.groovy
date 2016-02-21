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
        Element element;
        
        //this block picks out two elements for the style to use.
        int numElements = game.registry.elements.size();
        element = game.registry.elementList.get(random.nextInt(numElements));
        
        //assigns the name of the style.
        String name = "Path of " + element.name;
        
        //a list of possible attributes for the style to be based off of.
        ArrayList<StatDescriptor> attributePool = new ArrayList<>();
        
        //the three basic mental attributes.
        attributePool.add(game.registry.statDescriptors.get("Intelligence"));
        attributePool.add(game.registry.statDescriptors.get("Spirit"));
        attributePool.add(game.registry.statDescriptors.get("Perception"));
        
        //adds the attributes associated with each element.
        
        attributePool.addAll(element.primaryAttributes);
        
        //generates a new CharacterTreeDef to hold all this information.
        CharacterTreeDef ret = new CharacterTreeDef(name);
        
        ret.element = element;
        
        //now we need to pick out what kind of things this style focuses on doing.
        //First, we pick out all our options, weighting in favor of some things...
        ArrayList<CharacterTreeDef.Focus> focusPool = new ArrayList<>();
        
        //first, we have a chance to get any focus there is.
        focusPool.addAll(CharacterTreeDef.Focus.values());
        
        //Then theres an adfitional chance to get a focus associated with one of 
        //the elements used for this magical style. 
        focusPool.addAll(element.focuses);
        
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
        
        Spell.Detail detail = Spell.Detail.values()[random.nextInt(Spell.Detail.values().length)];
        
        ret.details.add(detail);
        
        detail = Spell.Detail.values()[random.nextInt(Spell.Detail.values().length)];
        
        
        while (ret.details.contains(detail)) detail = Spell.Detail.values()[random.nextInt(Spell.Detail.values().length)];
        
        ret.details.add(detail);
        
        ret.secondaryAttributes.add(game.registry.statDescriptors.get("Added Mana"));
        
        if (ret.details.contains(Spell.Detail.SPEED_BASED)) 
        ret.secondaryAttributes.add(game.registry.statDescriptors.get("Added Speed"));
        
        if (ret.details.contains(Spell.Detail.STAMINA_BASED)) 
        ret.secondaryAttributes.add(game.registry.statDescriptors.get("Added Stamina"));
        
        if (ret.details.contains(Spell.Detail.TOUGHNESS_BASED) ||
            ret.details.contains(Spell.Detail.HP_COST)) 
        ret.secondaryAttributes.add(game.registry.statDescriptors.get("Added HP"));
        
        attributePool.clear();
        attributePool.addAll(element.secondaryAttributes);
            
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
            if (random.nextInt(4) == 0) {
                //generate ability
                
                NodeDefinition capStone = tree.definition.newNode(tree);
                
                capStone.ability = new AbilityDefinition();
                
                capStone.ability.tree = tree;
                
                for (int i = 0; i < 3; i++) 
                    capStone.ability.details.add(tree.definition.details.get(random.nextInt(tree.definition.details.size())));
                
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
    
    public class NodeGenerator implements ProceduralGenerator<CharacterNode> {
        
        public MissileSpell generateMissile(AbilityDefinition ability) {
            CharacterWheel.CharacterTree tree = ability.tree;
            
            String name;

            Element element = tree.definition.element;
            
            float rangeValue = 1, sizeValue = 1, speedValue = 1, damageValue = 1;
            int limitationValue = 1, complexity = 9 + tree.layers.size(), pool = 10;
            
            //Stats: Range, Size, Speed, Damage, Cast Speed, Mana Cost
            Stat range, size, speed, damage, castTime, manaCost, cooldown;
                
            if (ability.details.contains(Spell.Detail.LONG_COOLDOWN)) {
                int num = 20 + random.nextInt(20);
                cooldown =  new NumericStat(num);
                limitationValue += num / 10;
                
            } else cooldown =  new NumericStat(0);
            
            if (ability.details.contains(Spell.Detail.LONG_CAST_TIME)) {
                int num = 10 + random.nextInt(10);
                castTime =  new NumericStat(num);
                limitationValue += num / 5;
                
            } else castTime =  new NumericStat(0);
            
            if (ability.details.contains(Spell.Detail.NORMAL_MANA_COST)) {
                int num = 10 + random.nextInt(10);
                manaCost =  new NumericStat(num);
                limitationValue += num / 5;
            } else if (ability.details.contains(Spell.Detail.HIGH_MANA_COST)) {
                int num = 20 + random.nextInt(20);
                manaCost =  new NumericStat(num);
                limitationValue += num / 5;
            } else manaCost =  new NumericStat(0);
            
            while (pool > 0) {
                switch (random.nextInt(7)) {
                    case 0:
                        rangeValue += 1;
                        break;
                    case 1:
                        sizeValue += 1;
                        break;
                    case 2:
                        speedValue += 1;
                        break;
                    case 4:
                        damageValue += 1;
                        break;
                }
                pool -= (rangeValue * sizeValue * speedValue * damageValue / limitationValue);
            }
            
            rangeValue = 300 + (rangeValue -1) * 50;
            range = new EquationStat("" + rangeValue + " * [Spell Range Multiplier]");
            
            sizeValue = 0.3 + (sizeValue -1) * 0.03;
            size = new NumericStat(sizeValue);
            
            speedValue = 40 + (speedValue -1) * 4;
            speed = new EquationStat("" + speedValue + " * [Spell Speed Multiplier]");
            
            damageValue = 30 + (damageValue -1) * 3;
            damage = new EquationStat("" + damageValue + " * [Spell Damage Multiplier]");
            
            name = element.name;

            AttackDefinition missile;

            
            missile = new AttackDefinition(name, new VectorCircle(1), element.name);
            missile.addStat("Ranged Attack", new BinaryStat());
            missile.addStat("Range", range);
            missile.addStat("Damage", damage);
            missile.addStat("Size", size);
            missile.addStat("Speed", speed);

            MissileSpell ret = new MissileSpell(missile);
            ret.addStat("Cast Time", castTime);
            ret.addStat("Mana Cost", manaCost);
            ret.addStat("Cooldown", cooldown);

            return ret;
        }
        
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

                node = new AbilityNode(req, tree, generateMissile(nodeDef.ability));
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