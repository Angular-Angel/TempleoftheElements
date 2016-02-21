
package templeoftheelements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
import stat.NoSuchStatException;
import stat.Stat;
import stat.StatContainer;
import templeoftheelements.collision.Creature;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.Ability;
import templeoftheelements.player.Action;
import templeoftheelements.player.CreatureEvent;



public abstract class Spell extends Action implements Ability {
    
    public static enum Detail {
        
        //Usage
        SPAMMABLE, SITUATIONAL, NONCOMBAT, PASSIVE, CROWD_CONTROL,
        BURST, FINISHER, 
        
        //Downsides
        NORMAL_MANA_COST, HIGH_MANA_COST, TEMPORARY_STAT_COST, 
        PERMANENT_STAT_COST, HP_COST, MATERIAL_COMPONENT, STAMINA_COST, XP_COST,
        DELAY, SHORT_COOLDOWN, LONG_COOLDOWN, LONG_CAST_TIME, RITUAL,
        
        //targeting types
        PROJECTILE, AREA_TARGET, ENEMY_TARGET, SELF_TARGET, MINION, 
        MULTI_TARGET, HOMING, CHAIN,

        //common effects
        DAMAGE, ARMOR_DIVISOR, BUFF, DEBUFF, ON_HIT_EFFECT, ON_ATTACK_EFFECT,
        ON_CAST_EFFECT, DAMAGE_OVER_TIME, AREA_OF_EFFECT, SLOW, STUN,
        IMMOBILIZATION, SILENCE, BLIND, MOVEMENT,

        //rare effects
        TERRAIN_ALTERATION, TELEPORTATION, ITEM_CREATION, ITEM_ALTERATION, 
        MIND_CONTROL, CONFUSION, PETRIFICATION, DIVINATION, HEAL, MANA_DRAIN, 
        EXHAUSTION,
        
        //Scaling
        SPEED_BASED, TOUGHNESS_BASED, LUCK_BASED, STAMINA_BASED;
        
        //

    }
    
    private Renderable sprite;
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
    public void perform(Creature creature, Vec2 in) {
        try {
            creature.notifyCreatureEvent(new CreatureEvent(CreatureEvent.Type.USED_SPELL, this));
            creature.getStat("Mana").modifyBase(-getScore("Mana Cost"));
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Spell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public abstract void cast(Creature caster, Vec2 in);
    
    public Renderable getSprite() {
        return sprite;
    }
    
}
