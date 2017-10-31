import templeoftheelements.Spell;
import templeoftheelements.Element;
import templeoftheelements.MissileSpell;
import templeoftheelements.display.VectorCircle;
import templeoftheelements.item.AttackDefinition;
import stat.*;
import java.util.Random;
import templeoftheelements.player.characterwheel.*;
import templeoftheelements.player.characterwheel.CharacterTreeDef.AbilityDefinition;

public class MissileSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
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
        speed = new EquationStat("" + speedValue + " * [Source@Spell Speed Multiplier]");

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

        abilityDef.ability = ret;
        
        return abilityDef;
    }
}