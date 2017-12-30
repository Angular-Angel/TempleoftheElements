import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.creature.Ability;
import stat.NumericStat;
import java.util.Random;
import templeoftheelements.player.AbilitySkill;

public class ManaCostSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilitySkill modify(AbilitySkill abilitySkill) {
        int pool = Math.min(10, (abilitySkill.stats.getScore("Cost Pool")));
        if (pool == 0) {
            abilitySkill.stats.getStat("Cost Pool").modifyBase(-1);
            return abilitySkill;
        }
        
        int manaCost = 1 + random.nextInt(pool) / Ability.Detail.MANA_COST.cost;
        
        abilitySkill.abilityDef.stats.getStat("Mana Cost").modify("Base", manaCost);
        
        abilitySkill.stats.getStat("Cost Pool").modifyBase(-manaCost * Ability.Detail.MANA_COST.cost);
        abilitySkill.stats.getStat("Pool").modifyBase(manaCost * Ability.Detail.MANA_COST.cost);
        
        return abilitySkill;
    }
}