
package templeoftheelements.spells;

import java.util.logging.Level;
import java.util.logging.Logger;
import stat.NoSuchStatException;
import stat.NumericStat;
import stat.StatContainer;
import templeoftheelements.creature.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.controller.Action;
import templeoftheelements.display.Renderable;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.AbilityDefinition;
import templeoftheelements.creature.CreatureEvent;
import templeoftheelements.effect.EffectContainer;



public abstract class Spell extends Ability implements EffectContainer, Action {
    
    protected Renderable sprite;

    public Spell(AbilityDefinition abilityDefinition, Renderable sprite) {
        this(abilityDefinition, sprite, new StatContainer());
    }
    
    
    public Spell(AbilityDefinition abilityDefinition, Renderable sprite, StatContainer stats) {
        super(abilityDefinition, stats);
        this.sprite = sprite;
    }
    
    @Override
    public boolean isPossible(Creature c) {
        return (c.stats.getScore("Mana") > stats.getScore("Mana Cost"));
    }
    
    @Override
    public void perform(Creature creature, Position pos) {
        creature.notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.USED_SPELL, this));
        ((NumericStat)creature.stats.getStat("Mana")).modifyBase(-stats.getScore("Mana Cost"));
    }
    
    public abstract void cast(Creature caster, Position pos);
    
    
    public String showCosts() {
        String ret = "";
        
        ret += "\nCast Time: " + stats.getScore("Cast Time");

        ret += "\nMana Cost: " + stats.getScore("Mana Cost");

        ret += "\nCooldown: " + stats.getScore("Cooldown");
        
        return ret;
    }
    
    @Override
    public Renderable getSprite() {
        return sprite;
    }
    
    public void initValues(Creature c) {
        c.addAction(this);
    }
    
}
