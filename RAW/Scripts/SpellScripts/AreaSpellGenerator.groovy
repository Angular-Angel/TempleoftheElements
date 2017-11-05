import templeoftheelements.AreaSpell;
import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.display.VectorCircle;   // the groovy script importing.
import stat.NumericStat;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;

public class AreaSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
        AreaSpell spell = new AreaSpell("Area Spell", new VectorCircle(0.5));
        
        spell.addStat("Cast Time", new NumericStat(0));
        spell.addStat("Mana Cost", new NumericStat(0));
        spell.addStat("Cooldown", new NumericStat(0));
        
        abilityDef.ability = spell
        return abilityDef;
    }
}