import templeoftheelements.spells.AreaSpell;
import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.display.VectorCircle;   // the groovy script importing.
import stat.NumericStat;
import templeoftheelements.player.AbilitySkill;

public class AreaSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilitySkill modify(AbilitySkill abilitySkill) {
        AreaSpell spell = new AreaSpell("Area Spell", new VectorCircle(0.5));
        
        spell.stats.addStat("Cast Time", new NumericStat(0));
        spell.stats.addStat("Mana Cost", new NumericStat(0));
        spell.stats.addStat("Cooldown", new NumericStat(0));
        
        abilitySkill.ability = spell
        return abilitySkill;
    }
}