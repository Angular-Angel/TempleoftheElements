import templeoftheelements.creature.StatusEffect;
import templeoftheelements.creature.Creature;
import templeoftheelements.creature.CreatureDefinition;
import org.jbox2d.common.Vec2;
import stat.NumericStat;
import java.util.Random;
import stat.NumericStat;
import static templeoftheelements.TempleOfTheElements.game;
import util.VecMath;

/**
 *
 * @author angle
 */
class Fatigue extends StatusEffect {
    
    public Fatigue() {
        super("Fatigue");
        severity = 1;
    }
    
    public void init(Creature c) {
        creature = c;
        stats.clearStats();
        stats.addStat("Dexterity", new NumericStat((float) -c.stats.getScore("Dexterity") * 0.1 * this.severity));
        stats.addStat("Stamina Regen", new NumericStat((float) -c.stats.getScore("Stamina Regen") * 0.09 * this.severity));
        stats.addStat("Intelligence", new NumericStat((float) -c.stats.getScore("Intelligence") * 0.05 * this.severity));
        stats.addStat("Perception", new NumericStat((float) -c.stats.getScore("Perception") * 0.05 * this.severity));
    }
    
    public void update(StatusEffect effect) {
        if (effect.severity > this.severity) {
            this.severity = effect.severity;
            creature.defactorStatusEffect(this);
            init(creature);
            creature.factorStatusEffect(this);
            
        }
    }

    @Override
    public void step(float dt) {
        if (creature == null) {
            System.out.println("Null creature in fatigue.groovy!");
            return;
        }
        if (creature.stats.getScore("Stamina")/creature.stats.getScore("Max Stamina") > (1.05 - 0.1 * severity)) {
            creature.removeStatusEffect(this);
        }
        
    }
    
    public StatusEffect clone() {
        StatusEffect ret = new Fatigue();
        return ret;
    }
}