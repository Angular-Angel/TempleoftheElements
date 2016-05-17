import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import templeoftheelements.effect.*;
import stat.*;
import generation.*;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import generation.ProceduralGenerator;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;
import templeoftheelements.player.CharacterTreeDef.NodeDefinition;
import templeoftheelements.player.CharacterTreeDef.ClusterDefinition;

public class MissileGenerator implements GenerationProcedure<AbilityDefinition> {

     //our random number generator;
    Random random = new Random();
    
    public AbilityDefinition generate(Object o) {
        AbilityDefinition abilityDef = (AbilityDefinition) o; //the definition for the node we're making
        CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) abilityDef.tree; //the tree to which the node will belong

        String name;

        Element element = tree.definition.element;

        int sizeValue = 1, speedValue = 1, pool = abilityDef.getScore("Pool")/4 + random.nextInt((int) (abilityDef.getScore("Pool") / 4));

        abilityDef.getStat("Pool").modify(-pool);

        //Stats: Range, Size, Speed, Damage, Cast Speed, Mana Cost
        Stat size, speed;

        sizeValue = random.nextInt(pool);
        
        speedValue = pool - sizeValue;

        speedValue = 40 + (speedValue -1) * 4;
        speed = new EquationStat("" + speedValue + " * [Attacker@Spell Speed Multiplier]");

        sizeValue = 0.3 + (sizeValue -1) * 0.03;
        size = new NumericStat(sizeValue);

        name = element.name;

        AttackDefinition missile;


        missile = new AttackDefinition(name, new VectorCircle(1), element.name);
        missile.addStat("Ranged Attack", new BinaryStat());
        missile.addStat("Size", size);
        missile.addStat("Speed", speed);

        MissileSpell ret = new MissileSpell(missile);
        ret.addStat("Cast Time", new NumericStat(0));
        ret.addStat("Mana Cost", new NumericStat(0));
        ret.addStat("Cooldown", new NumericStat(0));

        ret.description += "This spell shoots a missile.";
        ret.description += "\nSpeed: " + ((EquationStat) speed).equation;
        ret.description += "\nSize: " + size.getScore();

        abilityDef.ability = ret;
        
        return abilityDef;
    }

    public AbilityDefinition generate() {
        throw new UnsupportedOperationException();
    }
    
    public AbilityDefinition modify(AbilityDefinition node) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isApplicable(AbilityDefinition node) {
        throw new UnsupportedOperationException();
    }
}