/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.collision;

import org.jbox2d.common.Vec2;

/**
 *
 * @author angle
 */
public class Position extends Vec2 {

    public Position(Vec2 position) {
        super(position);
    }

    public Position(int x, int y) {
        super(x, y);
    }

    public Position() {
        super();
    }

    public Position(float x, float y) {
        super(x, y);
    }

    public Position copy() {
        return new Position(this);
    }
    
}
