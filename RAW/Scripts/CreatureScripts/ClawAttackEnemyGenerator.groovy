
import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import stat.*;
import generation.*;
import java.util.Random;
import com.samrj.devil.gl.Texture2D;
import static templeoftheelements.TempleOfTheElements.game;

/**
 *
 * @author angle
 */
class ClawAttackEnemyGenerator implements GenerationProcedure<CreatureDefinition> {
	
    int count = 0;
    Random random = new Random();
    
    public CreatureDefinition generate(Object o) {
        throw new UnsupportedOperationException();
    }
    
    public CreatureDefinition generate() {
        throw new UnsupportedOperationException();
    }
    
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
    
    public boolean isApplicable(CreatureDefinition definition) {
        throw new UnsupportedOperationException();
    }
    
}

