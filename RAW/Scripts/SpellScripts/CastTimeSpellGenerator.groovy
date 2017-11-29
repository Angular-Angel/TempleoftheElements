import templeoftheelements.creature.AbilityGenerationProcedure;;
import templeoftheelements.creature.Ability;
import stat.NumericStat;
import java.util.Random;
import templeoftheelements.player.AbilitySkill;

public class CastTimeSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilitySkill modify(AbilitySkill abilitySkill) {
        int pool = Math.min(10, (abilitySkill.getScore("Cost Pool")));
        if (pool == 0) {
            abilitySkill.getStat("Cost Pool").modifyBase(-1);
            return abilitySkill.ability;
        }
        
        int castTime = 1 + random.nextInt(pool) / Ability.Detail.CAST_TIME.cost;
        
        abilitySkill.ability.stats.addStat("Cast Time", new NumericStat(castTime));
        
        abilitySkill.getStat("Cost Pool").modifyBase(-castTime * Ability.Detail.CAST_TIME.cost);
        abilitySkill.getStat("Pool").modifyBase(castTime * Ability.Detail.CAST_TIME.cost);
        
        return abilitySkill;
    }

}