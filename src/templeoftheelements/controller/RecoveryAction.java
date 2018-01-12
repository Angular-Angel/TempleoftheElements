/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.controller;

import templeoftheelements.creature.Creature;

/**
 *
 * @author angle
 */
public class RecoveryAction implements OngoingAction {
    
    private float timer;
    private Creature creature;
    
    public RecoveryAction(float timer) {
        this.timer = timer;
    }

    @Override
    public void begin(Creature c) {
        creature = c;
    }

    @Override
    public boolean interruptible() {
        return false;
    }

    @Override
    public float movespeedModifier() {
        return 0.75f;
    }

    @Override
    public void step(float dt) {
        System.out.println(timer);
        timer -= dt;
        if (timer <= 0) {
            creature.endAction();
        }
    }

    @Override
    public void end() {
    }
    
}
