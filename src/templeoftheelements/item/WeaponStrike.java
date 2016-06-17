/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.item;

import org.jbox2d.common.Vec2;
import stat.StatContainer;
import templeoftheelements.collision.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.Ability;
import templeoftheelements.player.Action;

/**
 *
 * @author angle
 */
public class WeaponStrike extends Action {
    
    AttackDefinition attack;

    public WeaponStrike(String name, StatContainer stats) {
        super(name, stats);
    }

    @Override
    public Renderable getSprite() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void perform(Creature creature, Position in) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ability clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
