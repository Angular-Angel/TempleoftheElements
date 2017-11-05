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
        attack.addStat("Melee Attack", new BinaryStat());
        attack.addStat("Size", new NumericStat(0.43));
        attack.addStat("Duration", new NumericStat(13));
        attack.addStat("Reach", new NumericStat(0.8)); 
        attack.addStat("Angular Travel",  new NumericStat(70));
        attack.addStat("Distance Travel", new NumericStat(0));
        attack.addStat("Recovery Time", new NumericStat(26));
        attack.addStat("Damage Multiplier", new NumericStat(1));
        attack.addStat("Stamina Cost", new NumericStat(14));
        
        definition.addAbility(new AttackAction(attack));
        
        return definition;
    }
    
}

