
package templeoftheelements.spells;

import java.util.logging.Level;
import java.util.logging.Logger;
import stat.NoSuchStatException;
import stat.StatContainer;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.controller.Action;
import templeoftheelements.display.Renderable;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.CreatureEvent;
import templeoftheelements.effect.EffectContainer;



public abstract class Spell extends Ability implements EffectContainer, Action {
    
    protected Renderable sprite;

    public Spell(String name, Renderable sprite) {
        this(name, sprite, new StatContainer());
    }
    
    
    public Spell(String name, Renderable sprite, StatContainer stats) {
        super(name, stats);
        this.sprite = sprite;
    }
    
    @Override
    public boolean isPossible(Creature c) {
        try {
            return (c.stats.getScore("Mana") > stats.getScore("Mana Cost"));
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Spell.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public void perform(Creature creature, Position pos) {
        try {
            creature.notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.USED_SPELL, this));
            creature.stats.getStat("Mana").modifyBase(-stats.getScore("Mana Cost"));
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Spell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public abstract void cast(Creature caster, Position pos);
    
    
    public String showCosts() {
        String ret = "";
        
        try { 
            ret += "\ncast Time: " + stats.getScore("Cast Time");
            
            ret += "\nMana Cost: " + stats.getScore("Mana Cost");
            
            ret += "\nCooldown: " + stats.getScore("Cooldown");
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Spell.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ret;
    }
    
    @Override
    public Renderable getSprite() {
        return sprite;
    }
    
    public abstract float damageValueMultiplier();
    
    
    public void init(Creature c) {
        c.addAction(this);
    }
    
}
