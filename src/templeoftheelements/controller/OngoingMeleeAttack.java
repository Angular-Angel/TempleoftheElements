/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.controller;

import stat.NumericStat;
import templeoftheelements.TempleOfTheElements;
import templeoftheelements.collision.Attack;
import templeoftheelements.collision.MeleeAttack;
import templeoftheelements.creature.Creature;
import templeoftheelements.creature.CreatureEvent;
import templeoftheelements.item.AttackDefinition;

/**
 *
 * @author angle
 */
public class OngoingMeleeAttack implements OngoingAction{
    
    AttackDefinition attackDefinition;
    private MeleeAttack attack;
    private Creature creature;
    
    public OngoingMeleeAttack(AttackDefinition attackDefinition) {
        this.attackDefinition = attackDefinition;
    }

    @Override
    public void step(float dt) {
        if (attack != null) {
            if (!attack.isDead()) {
                attack.move(creature.getPosition(), creature.getDirection());
            } else {
                creature.endAction();
                creature.performAction(new RecoveryAction(attack.stats.getScore("Recovery Time") * creature.stats.getScore("Attack Speed Multiplier")));
                attack = null;
            }
        }
    }

    @Override
    public void begin(Creature c) {
        creature = c;
        Attack a = attackDefinition.generate(creature);
        TempleOfTheElements.game.addSprite(a);
        TempleOfTheElements.game.addActor(a);
        if (a instanceof MeleeAttack) attack = (MeleeAttack) a;
        creature.notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.ATTACKED, a));
        if (attack.stats.hasStat("Stamina Cost")) {
            ((NumericStat) creature.stats.getStat("Stamina")).modifyBase(-a.stats.getScore("Stamina Cost"));
            creature.notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.SPENT_STAMINA, attack.stats.getScore("Stamina Cost")));
        }
    }

    @Override
    public boolean interruptible() {
        return false;
    }

    @Override
    public float movespeedModifier() {
        return attackDefinition.stats.getScore("Movespeed Modifier");
    }

    @Override
    public void end() {
    }

    @Override
    public float staminaRegenModifier() {
        return attackDefinition.stats.getScore("Stamina Regen Modifier");
    }
    
}
