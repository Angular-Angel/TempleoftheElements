import templeoftheelements.spells.EnemyTargetSpell;
import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.display.VectorCircle;
import stat.NumericStat;
import java.util.Random;
import templeoftheelements.creature.AbilityDefinition;
import templeoftheelements.creature.Ability;
import templeoftheelements.player.AbilitySkill;

public class EnemyTargetSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilitySkill modify(AbilitySkill abilitySkill) {
        
        AbilityDefinition enemyTargetSpell = new AbilityDefinition("Enemy Target Spell") {
            public String getDescription() {
                return "This spell targets an enemy!";
            }
            public Ability getAbility() {
                EnemyTargetSpell spell = new EnemyTargetSpell(this, new VectorCircle(0.5));
       
                spell.stats.addAllStats(stats.viewStats());
                
                return spell;
            }
        }
        
        enemyTargetSpell.stats.addStat("Cast Time", new NumericStat(0));
        enemyTargetSpell.stats.addStat("Mana Cost", new NumericStat(0));
        enemyTargetSpell.stats.addStat("Cooldown", new NumericStat(0));
        
        abilitySkill.abilityDef = enemyTargetSpell;
        return abilitySkill;
    }
}