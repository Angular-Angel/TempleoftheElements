import templeoftheelements.Spell;
import stat.NumericStat;
import java.util.Random;
import templeoftheelements.player.characterwheel.*;
import templeoftheelements.player.characterwheel.CharacterTreeDef.AbilityDefinition;

public class CooldownSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
        int pool = Math.min(10, (abilityDef.getScore("Cost Pool")));
        if (pool == 0) {
            abilityDef.getStat("Cost Pool").modifyBase(-1);
            return abilityDef.ability;
        }
        
        int cooldown = 1 + random.nextInt(pool) / Spell.Detail.COOLDOWN.cost;
        
        abilityDef.ability.addStat("Cooldown", new NumericStat(cooldown));
        
        abilityDef.getStat("Cost Pool").modifyBase(-cooldown * Spell.Detail.COOLDOWN.cost);
        abilityDef.getStat("Pool").modifyBase(cooldown * Spell.Detail.COOLDOWN.cost);
        
        return abilityDef;
    }
}