
package templeoftheelements.controller;

import templeoftheelements.controller.Controller;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
import stat.NoSuchStatException;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.creature.Creature;
import util.VecMath;

/**
 *
 * @author angle
 */


public class BasicAI implements Controller {

    private Creature creature;
    private Vec2 accel;
    
    public BasicAI() {
        accel = new Vec2();
    }
    
    public BasicAI(Creature b) {
        creature = b;
        accel = new Vec2();
        creature.setController(this);
    }
    
    public void init() {
        
    }
    
    @Override
    public Creature getCreature() {
        return creature;
    }

    @Override
    public void step(float dt) {
        accel.x = 0;
        accel.y = 0;
        Vec2 pos = game.player.getCreature().getPosition();
        Vec2 position = getCreature().getPosition();
        try {
            if (VecMath.dist(position, pos) < getCreature().stats.getScore("Sight Range")) {
                accel = VecMath.direction(position, pos);
                accel.normalize();
            }
        } catch (NoSuchStatException ex) {
            Logger.getLogger(BasicAI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Vec2 getAccel() {
        return accel;
    }

    @Override
    public Controller clone(Creature creature) {
        return new BasicAI(creature);
    }

    @Override
    public boolean isEnemy() {
        return true;
    }

    @Override
    public boolean isDead() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addAction(Action a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void refactorActions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
