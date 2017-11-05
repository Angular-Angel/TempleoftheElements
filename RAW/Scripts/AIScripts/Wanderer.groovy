import templeoftheelements.creature.Creature;
import templeoftheelements.controller.Controller;
import templeoftheelements.controller.Action;
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
class Wanderer implements Controller {
    
    private Creature creature;
    private Vec2 accel;
    private Random random;
    private HashSet<Action> actions;
    
    public Wanderer() {
        accel = new Vec2();
        random = new Random();
    }
    
    public Wanderer(Creature b) {
        creature = b;
        accel = new Vec2();
        random = new Random();
        actions = new HashSet<>();
        creature.setController(this);
    }
    
    public void init() {
        
    }
    
    public Creature getCreature() {
        return creature;
    }
    
    public Vec2 getAccel() {
        return accel;
    }
    
    public Controller clone(Creature creature) {
        return new Wanderer(creature);
    }
    
    
    public void addAction(Action a ) {
        actions.add(a);
    }
    
    public void refactorActions() {
        actions.clear();
        creature.refactorActions();
    }
    
    public void step(float dt) {
        accel.x += -0.5 + random.nextFloat();
        accel.y += -0.5 + random.nextFloat();
        accel.normalize();
//        Vec2 pos = game.player.getCreature().getPosition();
//        Vec2 position = getCreature().getPosition();
//        try {
//            if (VecMath.dist(position, pos) < getCreature().getScore("Sight Range")) {
//                accel = VecMath.direction(position, pos);
//                accel.normalize();
//            } else {
//                accel.x = random.nextFloat();
//                accel.y = random.nextFloat();
//                accel.normalize();
//            }
//        } catch (NoSuchStatException ex) {
//            Logger.getLogger(BasicAI.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    public boolean isTarget(Creature c) {
        return (c.getController() == game.player);
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