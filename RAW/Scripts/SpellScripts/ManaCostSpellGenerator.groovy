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
        int pool = Math.min(10, (abilitySkill.getScore("Cost Pool")));
        if (pool == 0) {
            abilitySkill.getStat("Cost Pool").modifyBase(-1);
            return abilitySkill.ability;
        }
        
        int manaCost = 1 + random.nextInt(pool) / Ability.Detail.MANA_COST.cost;
        
        abilitySkill.ability.addStat("Mana Cost", new NumericStat(manaCost));
        
        abilitySkill.getStat("Cost Pool").modifyBase(-manaCost * Ability.Detail.MANA_COST.cost);
        abilitySkill.getStat("Pool").modifyBase(manaCost * Ability.Detail.MANA_COST.cost);
        
        return abilitySkill;
    }
}