
package templeoftheelements.player;

import java.util.HashMap;
import org.jbox2d.common.Vec2;
import stat.StatContainer;
import templeoftheelements.item.AttackDefinition;
import templeoftheelements.collision.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public class AttackAction extends Action {

    
    private AttackDefinition attack;
    public String description;
    
    public AttackAction(AttackDefinition attack) {
        super(attack.getName(), new StatContainer());
        this.attack = attack;
    }
    
    public AttackAction(String name, AttackDefinition attack) {
        super(name, new StatContainer());
        this.attack = attack;
    }
    
    @Override
    public void perform(Creature creature, Position in) {
        creature.attack(attack);
    }

    @Override
    public Renderable getSprite() {
        return attack.getSprite();
    }

    @Override
    public Ability clone() {
        return new AttackAction(this.getName(), attack);
    }

    @Override
    public String getDescription() {
        return description;
    }
    
}
