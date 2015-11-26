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
class Fighter implements Controller {
    
    private Creature creature;
    private Vec2 accel;
    private Random random;
    private HashSet<Action> actions;
    
    public Fighter() {
        accel = new Vec2();
        random = new Random();
    }
    
    public Fighter(Creature b) {
        creature = b;
        creature.setController(this);
        accel = new Vec2();
        random = new Random();
        actions = new HashSet<>();
    }
    
    public Creature getCreature() {
        return creature;
    }
    
    public Vec2 getAccel() {
        return accel;
    }
    
    public Controller clone(Creature creature) {
        return new Fighter(creature);
    }
    
    
    public void addAction(Action a ) {
        actions.add(a);
    }
    
    public void refactorActions() {
        actions.clear();
        creature.refactorActions();
    }
    
    public void step(float dt) {
        accel.x = 0;
        accel.y = 0;
        Vec2 pos = game.player.getCreature().getPosition();
        Vec2 position = getCreature().getPosition();
        try {
            if (VecMath.dist(position, pos) < getCreature().getScore("Sight Range")) {
                accel = VecMath.direction(position, pos);
                accel.normalize();
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(BasicAI.class.getName()).log(Level.SEVERE, null, ex);
        }
        int x = pos.x - getCreature().getPosition().x;
        int y = pos.y - getCreature().getPosition().y;
        if (Math.sqrt(x * x + y * y) <= 100) attack();
    }
    
    public void attack() {
        
    }
    
    public boolean isEnemy() {
        return true;
    }
    public boolean isDead() {
        throw new UnsupportedOperationException();
    }
    public void destroy() {
        creature.destroy();
    }
}