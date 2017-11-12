import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.creature.Ability;
import stat.NumericStat;
import java.util.Random;
import templeoftheelements.player.AbilitySkill;

public class CooldownSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilitySkill modify(AbilitySkill abilitySkill) {
        int pool = Math.min(10, (abilitySkill.getScore("Cost Pool")));
        if (pool == 0) {
            abilitySkill.getStat("Cost Pool").modifyBase(-1);
            return abilitySkill.ability;
        }
        
        int cooldown = 1 + random.nextInt(pool) / Ability.Detail.COOLDOWN.cost;
        
        abilitySkill.ability.addStat("Cooldown", new NumericStat(cooldown));
        
        abilitySkill.getStat("Cost Pool").modifyBase(-cooldown * Ability.Detail.COOLDOWN.cost);
        abilitySkill.getStat("Pool").modifyBase(cooldown * Ability.Detail.COOLDOWN.cost);
        
        return abilitySkill;
    }
}