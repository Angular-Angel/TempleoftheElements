
package templeoftheelements;

import templeoftheelements.item.AttackDefinition;
import java.util.HashMap;
import org.jbox2d.common.Vec2;
import stat.Stat;
import templeoftheelements.collision.Creature;

/**
 *
 * @author angle
 */


public class MissileSpell extends Spell {

    private AttackDefinition missile;
    
    public MissileSpell(AttackDefinition attack) {
        this(attack.getName(), attack, new HashMap<>());
    }
    
    public MissileSpell(String name, AttackDefinition attack) {
        this(name, attack, new HashMap<>());
    }
    
    public MissileSpell(String name, AttackDefinition attack, HashMap<String, Stat> stats) {
        super(name, attack.getSprite(), stats);
        missile = attack;
    }

    @Override
    public void perform(Creature creature, Vec2 in) {
        if (!isPossible(creature)) return;
        super.perform(creature, in);
        cast(creature, in);
    }

    @Override
    public void cast(Creature caster, Vec2 in) {
        caster.attack(missile);
    }
    
}
