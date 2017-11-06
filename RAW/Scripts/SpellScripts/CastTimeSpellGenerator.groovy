import templeoftheelements.spells.Spell;
import templeoftheelements.creature.AbilityGenerationProcedure;
import stat.NumericStat;
import java.util.Random;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;

public class CastTimeSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
        int pool = Math.min(10, (abilityDef.getScore("Cost Pool")));
        if (pool == 0) {
            abilityDef.getStat("Cost Pool").modifyBase(-1);
            return abilityDef.ability;
        }
        
        int castTime = 1 + random.nextInt(pool) / Spell.Detail.CAST_TIME.cost;
        
        abilityDef.ability.addStat("Cooldown", new NumericStat(castTime));
        
        abilityDef.getStat("Cost Pool").modifyBase(-castTime * Spell.Detail.CAST_TIME.cost);
        abilityDef.getStat("Pool").modifyBase(castTime * Spell.Detail.CAST_TIME.cost);
        
        return abilityDef;
    }

}