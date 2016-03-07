import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;
import org.jbox2d.common.Vec2;
import stat.NumericStat;
import java.util.Random;
import stat.*;
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
        clearStats();
        addStat("Dexterity", new NumericStat((float) -c.getScore("Dexterity") * 0.1 * this.severity));
        addStat("Stamina Regen", new NumericStat((float) -c.getScore("Stamina Regen") * 0.09 * this.severity));
        addStat("Intelligence", new NumericStat((float) -c.getScore("Intelligence") * 0.05 * this.severity));
        addStat("Perception", new NumericStat((float) -c.getScore("Perception") * 0.05 * this.severity));
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
        if (creature.getScore("Stamina")/creature.getScore("Max Stamina") > (1.05 - 0.1 * severity)) {
            creature.removeStatusEffect(this);
        }
        
    }

    @Override
    public boolean isEnemy() {return false;}

    @Override
    public boolean isDead() {
        return (creature == null); 
    }

    @Override
    public void destroy() {
        
    }
    
    public StatusEffect clone() {
        StatusEffect ret = new Fatigue();
        return ret;
    }
}