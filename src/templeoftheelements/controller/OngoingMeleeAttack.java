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
                //attackTimer = (int) attack.stats.getScore("Recovery Time") / stats.getScore("Attack Speed Multiplier");
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
    
}
