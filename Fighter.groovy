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
    private Action attack;
    
    public Fighter() {
        accel = new Vec2();
        random = new Random();
    }
    
    public Fighter(Creature b) {
        creature = b;
        accel = new Vec2();
        random = new Random();
        actions = new HashSet<>();
        creature.setController(this);
    }
    
    public void init() {
        for (Action a : creature.getActions()) {
            addAction(a);
        }
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
        if (a instanceof AttackAction) attack = (AttackAction) a;
    }
    
    public void refactorActions() {
        actions.clear();
        creature.refactorActions();
    }
    
    public void step(float dt) {
        Vec2 pos = game.player.getCreature().getPosition();
        Vec2 position = getCreature().getPosition();
        accel = new Vec2();
        try {
            if (VecMath.dist(position, pos) < getCreature().getScore("Sight Range")) {
                accel = VecMath.direction(position, pos);
                accel.normalize();
                Vec2 dir = new Vec2(accel);
                Boolean xNeg, yNeg;
                if (dir.x < 0) {
                    xNeg = true;
                    dir.x *= -1;
                }
                if (dir.y < 0) {
                    yNeg = true;
                    dir.y *= -1;
                }
                float angle = Math.toDegrees(Math.atan((double) dir.y/dir.x));
                if (xNeg && yNeg) {
                    angle = Math.abs(90 - angle);
                    angle += 180;
                }
                else if (yNeg) angle += 90;
                else if (xNeg) angle += 270;
                else angle = Math.abs(90 -angle);
                angle -= getCreature().getDirection();
                while (angle >= 180) angle -= 360;
                while (angle < -180) angle += 360;
                if (angle < -45 || angle > 45 ) {
                    accel.x = 0;
                    accel.y = 0;
                } else {
                    int x = pos.x - getCreature().getPosition().x;
                    int y = pos.y - getCreature().getPosition().y;
                    if (Math.sqrt(x * x + y * y) <= 10) attack();
                }
                if (angle > 0 ) getCreature().modifyDirection(1);
                    else if (angle < 0) getCreature().modifyDirection(-1);
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(BasicAI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void attack() {
        Vec2 pos = game.player.getCreature().getPosition();
        if (attack != null) attack.perform(creature, pos);
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