
package templeoftheelements.controller;

import templeoftheelements.creature.Creature;

/**
 *
 * @author angle
 */


public class DoNothingAction implements OngoingAction {

    @Override
    public void begin(Creature c) {
    }

    @Override
    public void step(float dt) {
    }

    @Override
    public boolean interruptible() {
        return true;
    }

    @Override
    public float movespeedModifier() {
        return 1;
    }

    @Override
    public void end() {
    }

    @Override
    public float staminaRegenModifier() {
        return 1;
    }
    
}
