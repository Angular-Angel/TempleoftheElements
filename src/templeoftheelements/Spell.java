
package templeoftheelements;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import stat.NoSuchStatException;
import stat.StatContainer;
import templeoftheelements.collision.Creature;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.Action;
import templeoftheelements.player.CreatureEvent;
import templeoftheelements.effect.EffectContainer;



public abstract class Spell extends Action implements EffectContainer {
    
    public static enum Detail {
        
        //Usage
        _USAGES_(0), SPAMMABLE(0), SITUATIONAL(0), NONCOMBAT(0), PASSIVE(0),
        
        //Costs
        _COSTS_(0), MANA_COST(2), TEMPORARY_STAT_COST(4), PERMANENT_STAT_COST(8), 
        HP_COST(3), MATERIAL_COMPONENT(5), STAMINA_COST(1), XP_COST(5), DELAY(1), 
        CHARGE(3), COOLDOWN(2), CAST_TIME(3), RITUAL(5),
        
        //targeting types
        _TARGETING_(0), PROJECTILE(0), AREA_TARGET(0), ENEMY_TARGET(0), SELF_TARGET(0), //MINION, 

        //common effects
        _COMMON_(0), DAMAGE(3), ARMOR_DIVISOR(2), BUFF(3), DEBUFF(2), ON_HIT_EFFECT(2), 
        ON_ATTACK_EFFECT(3), ON_CAST_EFFECT(5), DAMAGE_OVER_TIME(1), AREA_OF_EFFECT(1), SLOW(1), STUN(6),
        IMMOBILIZATION(4), SILENCE(3), BLIND(4), MOVEMENT(2),

        //rare effects
        _RARE_(0), TERRAIN_ALTERATION(5), TELEPORTATION(6), ITEM_CREATION(6), ITEM_ALTERATION(6), 
        MIND_CONTROL(8), CONFUSION(6), PETRIFICATION(10), DIVINATION(4), HEAL(3), MANA_DRAIN(2), 
        EXHAUSTION(3), MULTI_TARGET(4), HOMING(3), CHAIN(4),
        
        //Scaling
        _SCALING_(0), SPIRIT_BASED(0), INTELLIGENCE_BASED(0), PERCEPTION_BASED(0), SPEED_BASED(0), 
        STRENGTH_BASED(0), CONSTITUTION_BASED(0), DEXTERITY_BASED(0), STAMINA_BASED(0), LUCK_BASED(0);
        
        //
        
        public final int cost;
        
        Detail(int cost) {
            this.cost = cost;
        }

    }
    
    protected Renderable sprite;
    public ArrayList<Detail> details;

    public Spell(String name, Renderable sprite) {
        this(name, sprite, new StatContainer());
    }
    
    
    public Spell(String name, Renderable sprite, StatContainer stats) {
        super(name, stats);
        this.sprite = sprite;
        this.details = new ArrayList<>();
    }
    
    @Override
    public boolean isPossible(Creature c) {
        try {
            return (super.isPossible(c) && c.getScore("Mana") > getScore("Mana Cost"));
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Spell.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public void perform(Creature creature, Position pos) {
        try {
            creature.notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.USED_SPELL, this));
            creature.getStat("Mana").modifyBase(-getScore("Mana Cost"));
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Spell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public abstract void cast(Creature caster, Position pos);
    
    
    public String showCosts() {
        String ret = "";
        
        try { 
            ret += "\ncast Time: " + getScore("Cast Time");
            
            ret += "\nMana Cost: " + getScore("Mana Cost");
            
            ret += "\nCooldown: " + getScore("Cooldown");
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
    
}
