import templeoftheelements.Spell;
import templeoftheelements.EnemyTargetSpell;
import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.display.VectorCircle;
import stat.NumericStat;
import java.util.Random;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;

public class EnemyTargetSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
        EnemyTargetSpell spell = new EnemyTargetSpell("Enemy Target Spell", new VectorCircle(0.5));
        
        spell.addStat("Cast Time", new NumericStat(0));
        spell.addStat("Mana Cost", new NumericStat(0));
        spell.addStat("Cooldown", new NumericStat(0));
        
        abilityDef.ability = spell
        return abilityDef;
    }
}