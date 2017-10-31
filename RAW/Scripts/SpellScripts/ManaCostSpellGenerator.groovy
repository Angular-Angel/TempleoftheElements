import templeoftheelements.Spell;
import stat.NumericStat;
import java.util.Random;
import templeoftheelements.player.characterwheel.*;
import templeoftheelements.player.characterwheel.CharacterTreeDef.AbilityDefinition;

public class ManaCostSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
        int pool = Math.min(10, (abilityDef.getScore("Cost Pool")));
        if (pool == 0) {
            abilityDef.getStat("Cost Pool").modifyBase(-1);
            return abilityDef.ability;
        }
        
        int manaCost = 1 + random.nextInt(pool) / Spell.Detail.MANA_COST.cost;
        
        abilityDef.ability.addStat("Mana Cost", new NumericStat(manaCost));
        
        abilityDef.getStat("Cost Pool").modifyBase(-manaCost * Spell.Detail.MANA_COST.cost);
        abilityDef.getStat("Pool").modifyBase(manaCost * Spell.Detail.MANA_COST.cost);
        
        return abilityDef;
    }
}