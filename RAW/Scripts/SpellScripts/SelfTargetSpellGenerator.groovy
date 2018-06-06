import templeoftheelements.spells.SelfTargetSpell;
import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.display.VectorCircle;
import stat.NumericStat;
import java.util.Random;
import templeoftheelements.creature.AbilityDefinition;
import templeoftheelements.creature.Ability;
import templeoftheelements.player.AbilitySkill;

public class SelfTargetSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilitySkill modify(AbilitySkill abilitySkill) {
        
        AbilityDefinition selfTargetSpell = new AbilityDefinition("Self Target Spell") {
            public String getDescription() {
                return "This spell is self targeted.";
            }
            public Ability getAbility() {
                SelfTargetSpell spell = new SelfTargetSpell(this, new VectorCircle(0.5));
       
                spell.stats.addAllStats(stats.viewStats());
                return spell;
            }
        }
        
        selfTargetSpell.stats.addStat("Cast Time", new NumericStat(0));
        selfTargetSpell.stats.addStat("Mana Cost", new NumericStat(0));
        selfTargetSpell.stats.addStat("Cooldown", new NumericStat(0));
        
        abilitySkill.abilityDef = selfTargetSpell;
        return abilitySkill;
    }
}