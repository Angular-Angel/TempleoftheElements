/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.controller;

import templeoftheelements.Steppable;
import templeoftheelements.creature.Creature;

/**
 *
 * @author angle
 */
public interface OngoingAction extends Steppable {
    
    public void begin(Creature c);
    
    public boolean interruptible();
    
    public void end();
    
    public float movespeedModifier();
    
}
