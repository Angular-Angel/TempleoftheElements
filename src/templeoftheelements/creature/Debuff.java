/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.creature;

import static templeoftheelements.TempleOfTheElements.game;

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
    public StatusEffect clone() {
        Debuff debuff = new Debuff(name, maxTimer, origin);
        debuff.stats.addAllStats(this.stats);
        return debuff;
    }
    
}
