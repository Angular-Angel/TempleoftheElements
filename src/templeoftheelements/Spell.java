
package templeoftheelements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
import stat.NoSuchStatException;
import stat.Stat;
import templeoftheelements.collision.Creature;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.Action;
import templeoftheelements.player.CreatureEvent;



public abstract class Spell extends Action {
    
    public static enum Detail {
        
        //targeting types
        PROJECTILE, AREA_TARGET, ENEMY_TARGET, SELF_TARGET, MINION,

        //common effects
        DAMAGE, ARMOR_DIVISOR, BUFF, DEBUFF, ON_HIT_EFFECT, ON_ATTACK_EFFECT,
        ON_CAST_EFFECT, DAMAGE_OVER_TIME, AREA_OF_EFFECT, SLOW, STUN,
        IMMOBILIZATION, SILENCE, BLIND, MOVEMENT,

        //rare effects
        TERRAIN_ALTERATION, TELEPORTATION, ITEM_CREATION, ITEM_ALTERATION, 
        PERMANENT_STAT_COST, TEMPORARY_STAT_COST, MIND_CONTROL, CONFUSION,
        PETRIFICATION, MULTI_TARGET, HOMING, DIVINATION, HEAL, MANA_DRAIN, 
        EXHAUSTION;

    }
    
    private Renderable sprite;
    public ArrayList<Detail> details;

    public Spell(String name, Renderable sprite) {
        this(name, sprite, new HashMap<>());
    }
    
    
    public Spell(String name, Renderable sprite, HashMap<String, Stat> stats) {
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
