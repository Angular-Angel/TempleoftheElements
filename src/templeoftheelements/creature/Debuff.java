/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.creature;

import templeoftheelements.creature.StatusEffect;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.creature.Creature;

/**
 *
 * @author angle
 */
public class Debuff extends StatusEffect {
    
    public int timer, maxTimer;
    public Creature origin;

    public Debuff(String name, int timer, Creature origin) {
        super(name);
        this.timer = timer;
        this.maxTimer = timer;
        this.origin = origin;
        
    }

    @Override
    public void init(Creature c) {}

    @Override
    public void update(StatusEffect effect) {
        this.timer = ((Debuff) effect).timer;
    }

    @Override
    public void step(float dt) {
        timer--;
    }

    @Override
    public boolean isEnemy() {
        return origin != game.player.getCreature();
    }

    @Override
    public boolean isDead() {
        return timer <= 0;
    }

    @Override
    public void destroy() {
        
    }

    @Override
    public StatusEffect clone() {
        Debuff debuff = new Debuff(name, maxTimer, origin);
        debuff.addAllStats(this);
        return debuff;
    }
    
}
