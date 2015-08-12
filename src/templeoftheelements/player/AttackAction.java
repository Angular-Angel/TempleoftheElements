
package templeoftheelements.player;

import java.util.HashMap;
import org.jbox2d.common.Vec2;
import templeoftheelements.item.AttackDefinition;
import templeoftheelements.collision.Creature;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public class AttackAction extends Action{

    private AttackDefinition attack;
    
    public AttackAction(AttackDefinition attack) {
        super(attack.getName(), new HashMap<>());
        this.attack = attack;
    }
    
    public AttackAction(String name, AttackDefinition attack) {
        super(name, new HashMap<>());
        this.attack = attack;
    }
    
    @Override
    public void perform(Creature creature, Vec2 in) {
        creature.attack(attack);
    }

    @Override
    public Renderable getSprite() {
        return attack.getSprite();
    }
    
}
