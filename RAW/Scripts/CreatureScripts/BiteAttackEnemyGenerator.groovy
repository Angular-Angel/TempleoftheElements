
import templeoftheelements.CreatureGenerationProcedure;
import templeoftheelements.CreatureDefinition;
import templeoftheelements.display.VectorCircle;   // the groovy script importing.
import templeoftheelements.player.AttackAction;   // the groovy script importing.
import templeoftheelements.item.AttackDefinition;
import stat.NumericStat;
import stat.BinaryStat;
import java.util.Random;

/**
 *
 * @author angle
 */
class BiteAttackEnemyGenerator extends CreatureGenerationProcedure {
	
    int count = 0;
    Random random = new Random();
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script give an enemy a claw attack.
        
        AttackDefinition attack = new AttackDefinition("Bite", new VectorCircle(1), "Piercing");
        attack.addStat("Melee Attack", new BinaryStat());
        attack.addStat("Size", new NumericStat(0.43));
        attack.addStat("Duration", new NumericStat(2));
        attack.addStat("Reach", new NumericStat(0.8)); 
        attack.addStat("Angular Travel",  new NumericStat(0));
        attack.addStat("Distance Travel", new NumericStat(5));
        attack.addStat("Recovery Time", new NumericStat(50));
        attack.addStat("Damage Multiplier", new NumericStat(0));
        attack.addStat("Stamina Cost", new NumericStat(4));
        
        definition.addAbility(new AttackAction(attack));
        
        return definition;
    }
    
}

