import templeoftheelements.spells.EnemyTargetSpell;
import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.display.VectorCircle;
import stat.NumericStat;
import java.util.Random;
import templeoftheelements.player.AbilitySkill;

public class EnemyTargetSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilitySkill modify(AbilitySkill abilitySkill) {
        EnemyTargetSpell spell = new EnemyTargetSpell("Enemy Target Spell", new VectorCircle(0.5));
        
        spell.stats.addStat("Cast Time", new NumericStat(0));
        spell.stats.addStat("Mana Cost", new NumericStat(0));
        spell.stats.addStat("Cooldown", new NumericStat(0));
        
        abilitySkill.ability = spell
        return abilitySkill;
    }
}