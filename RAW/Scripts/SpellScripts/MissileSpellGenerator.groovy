import templeoftheelements.spells.Spell;
import templeoftheelements.Element;
import templeoftheelements.spells.MissileSpell;
import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.display.VectorCircle;
import templeoftheelements.item.AttackDefinition;
import stat.Stat;
import stat.EquationStat;
import stat.NumericStat;
import stat.BinaryStat;
import java.util.Random;
import templeoftheelements.player.CharacterWheel;
import templeoftheelements.player.AbilitySkill;

public class MissileSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilitySkill modify(AbilitySkill abilitySkill) {
        CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) abilitySkill.tree; //the tree to which the node will belong

        String name;

        Element element = tree.definition.element;

        int sizeValue = 1, speedValue = 1, pool = abilitySkill.getScore("Pool")/4 + random.nextInt((int) (abilitySkill.getScore("Pool") / 4));

        abilitySkill.getStat("Pool").modify(-pool);

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

        abilitySkill.ability = ret;
        
        return abilitySkill;
    }
}