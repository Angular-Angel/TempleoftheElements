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

public class NodeGenerator implements ProceduralGenerator<CharacterNode> {

     //our random number generator;
    Random random = new Random();
    
    public MissileSpell generateMissile(AbilityDefinition ability) {
        CharacterWheel.CharacterTree tree = ability.tree;

        String name;

        Element element = tree.definition.element;

        float rangeValue = 1, sizeValue = 1, speedValue = 1, damageValue = 1;
        int limitationValue = 1, complexity = 9 + tree.layers.size(), pool = 100;

        //Stats: Range, Size, Speed, Damage, Cast Speed, Mana Cost
        Stat size, speed, damage, castTime, manaCost, cooldown;

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

       if (ability.details.contains(Spell.Detail.HIGH_MANA_COST)) {
            int num = 20 + random.nextInt(20);
            manaCost =  new NumericStat(num);
            limitationValue += num / 5;
        } else {
            int num = 10 + random.nextInt(10);
            manaCost =  new NumericStat(num);
            limitationValue += num / 5;
        } 

        while (pool > 0) {
            switch (random.nextInt(3)) {
                case 0:
                    sizeValue += 1;
                    pool -= (speedValue * damageValue / limitationValue);
                    break;
                case 1:
                    speedValue += 1;
                    pool -= (sizeValue * damageValue / limitationValue);
                    break;
                case 2:
                    damageValue += 1;
                    pool -= (sizeValue * speedValue / limitationValue);
                    break;
            }

        }

        speedValue = 40 + (speedValue -1) * 4;
        speed = new EquationStat("" + speedValue + " * [Attacker@Spell Speed Multiplier]");

        sizeValue = 0.3 + (sizeValue -1) * 0.03;
        size = new NumericStat(sizeValue);

        damageValue = 30 + (damageValue -1) * 3;
        damage = new EquationStat("" + damageValue + " * [Attacker@Spell Damage Multiplier]");

        name = element.name;

        AttackDefinition missile;


        missile = new AttackDefinition(name, new VectorCircle(1), element.name);
        missile.addStat("Ranged Attack", new BinaryStat());
        missile.addStat("Damage", damage);
        missile.addStat("Size", size);
        missile.addStat("Speed", speed);

        MissileSpell ret = new MissileSpell(missile);
        ret.addStat("Cast Time", castTime);
        ret.addStat("Mana Cost", manaCost);
        ret.addStat("Cooldown", cooldown);

        ret.description += "This spell shoots a missile.";
        ret.description += "\nDamage: " + ((EquationStat) damage).equation;
        ret.description += "\nSpeed: " + ((EquationStat) speed).equation;
        ret.description += "\nSize: " + size.getScore();


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