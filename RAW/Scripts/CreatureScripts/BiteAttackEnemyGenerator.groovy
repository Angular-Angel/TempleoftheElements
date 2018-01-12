import templeoftheelements.creature.CreatureGenerationProcedure;
import templeoftheelements.creature.CreatureDefinition;
import templeoftheelements.creature.NaturalAttack;
import templeoftheelements.display.VectorCircle;   // the groovy script importing.
import templeoftheelements.controller.AttackAction;   // the groovy script importing.
import templeoftheelements.item.AttackDefinition;
import stat.NumericStat;
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
        attack.stats.addStat("Melee Attack", new NumericStat(1));
        attack.stats.addStat("Size", new NumericStat(0.43));
        attack.stats.addStat("Duration", new NumericStat(2));
        attack.stats.addStat("Reach", new NumericStat(0.4)); 
        attack.stats.addStat("Angular Travel",  new NumericStat(0));
        attack.stats.addStat("Distance Travel", new NumericStat(0.2));
        attack.stats.addStat("Recovery Time", new NumericStat(2.5));
        attack.stats.addStat("Min Damage", new NumericStat(20));
        attack.stats.addStat("Max Damage", new NumericStat(50));
        attack.stats.addStat("Stamina Cost", new NumericStat(4));
        attack.stats.addStat("Movespeed Modifier", new NumericStat(0.85));
        
        definition.addAbility(new NaturalAttack(attack));
        
        return definition;
    }
    
}

