
package templeoftheelements;

import templeoftheelements.item.AttackDefinition;
import java.util.HashMap;
import org.jbox2d.common.Vec2;
import stat.Stat;
import stat.StatContainer;
import templeoftheelements.collision.Creature;
import templeoftheelements.player.Ability;

/**
 *
 * @author angle
 */


public class MissileSpell extends Spell {

    private AttackDefinition missile;
    public String description;
    
    public MissileSpell(AttackDefinition attack) {
        this(attack.getName(), attack, new StatContainer());
    }
    
    public MissileSpell(String name, AttackDefinition attack) {
        this(name, attack, new StatContainer());
    }
    
    public MissileSpell(String name, AttackDefinition attack, StatContainer stats) {
        super(name, attack.getSprite(), stats);
        missile = attack;
        description = "";
    }

    @Override
    public void perform(Creature creature, Vec2 in) {
        if (!isPossible(creature)) return;
        super.perform(creature, in);
        cast(creature, in);
    }

    @Override
    public void cast(Creature caster, Vec2 in) {
        caster.attack(getMissile());
    }

    public String getDescription() {
        return description;
    }

    @Override
    public Ability clone() {
        return new MissileSpell(this.getName(), getMissile(), this);
    }

    /**
     * @return the missile
     */
    public AttackDefinition getMissile() {
        return missile;
    }
    
}
