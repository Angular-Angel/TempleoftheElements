import templeoftheelements.creature.CreatureGenerationProcedure;
import templeoftheelements.creature.CreatureDefinition;
import templeoftheelements.display.VectorCircle;   // the groovy script importing.
import templeoftheelements.controller.AttackAction;   // the groovy script importing.
import templeoftheelements.item.AttackDefinition;
import stat.NumericStat;
import stat.BinaryStat;
import java.util.Random;

/**
 *
 * @author angle
 */
class ClawAttackEnemyGenerator extends CreatureGenerationProcedure {
	
    int count = 0;
    Random random = new Random();
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script give an enemy a claw attack.
        
        AttackDefinition attack = new AttackDefinition("Claw", new VectorCircle(1), "Slashing");
        attack.stats.addStat("Melee Attack", new BinaryStat());
        attack.stats.addStat("Size", new NumericStat(0.43));
        attack.stats.addStat("Duration", new NumericStat(13));
        attack.stats.addStat("Reach", new NumericStat(0.8)); 
        attack.stats.addStat("Angular Travel",  new NumericStat(70));
        attack.stats.addStat("Distance Travel", new NumericStat(0));
        attack.stats.addStat("Recovery Time", new NumericStat(26));
        attack.stats.addStat("Damage Multiplier", new NumericStat(1));
        attack.stats.addStat("Stamina Cost", new NumericStat(14));
        
        definition.addAbility(new AttackAction(attack));
        
        return definition;
    }
    
}

