
package templeoftheelements.creature;

import stat.StatContainer;



public abstract class Ability {
    
    public static enum Detail {
        
        //Usage
//Usage
//Usage
//Usage
        _USAGES_(-1), SPAMMABLE(0), SITUATIONAL(0), NONCOMBAT(0), PASSIVE(0),
        
        //Costs
        _COSTS_(-1), MANA_COST(2), TEMPORARY_STAT_COST(4), PERMANENT_STAT_COST(8), 
        HP_COST(3), MATERIAL_COMPONENT(5), STAMINA_COST(1), XP_COST(5), DELAY(1), 
        CHARGE(3), COOLDOWN(2), CAST_TIME(3), RITUAL(5),
        
        //targeting types
        _TARGETING_(-1), PROJECTILE(0), AREA_TARGET(0), ENEMY_TARGET(0), SELF_TARGET(0), //MINION, 

        //common effects
        _COMMON_(-1), DAMAGE(3), ARMOR_DIVISOR(2), BUFF(3), DEBUFF(2), ON_HIT_EFFECT(2), 
        ON_ATTACK_EFFECT(3), ON_CAST_EFFECT(5), DAMAGE_OVER_TIME(1), AREA_OF_EFFECT(1), SLOW(1), STUN(6),
        IMMOBILIZATION(4), SILENCE(3), BLIND(4), MOVEMENT(2),

        //rare effects
        _RARE_(-1), TERRAIN_ALTERATION(5), TELEPORTATION(6), ITEM_CREATION(6), ITEM_ALTERATION(6), 
        MIND_CONTROL(8), CONFUSION(6), PETRIFICATION(10), DIVINATION(4), HEAL(3), MANA_DRAIN(2), 
        EXHAUSTION(3), MULTI_TARGET(4), HOMING(3), CHAIN(4),
        
        //Scaling
        _SCALING_(-1), SPIRIT_BASED(0), INTELLECT_BASED(0), PERCEPTION_BASED(0), SPEED_BASED(0), 
        STRENGTH_BASED(0), VITALITY_BASED(0), DEXTERITY_BASED(0), STAMINA_BASED(0), HP_BASED(0), LUCK_BASED(0);
        
        //
        
        public final int cost;
        
        Detail(int cost) {
            this.cost = cost;
        }

    }
    public final AbilityDefinition abilityDef;
    public final StatContainer stats;
    
    public Ability(AbilityDefinition abilityDef) {
        this(abilityDef, new StatContainer());
    }
    
    public Ability(AbilityDefinition abilityDef, StatContainer stats) {
        this.abilityDef =  abilityDef;
        this.stats = stats;
    }
    
    public abstract void initValues(Creature c);
    
    public abstract void init(Creature c);
    
    public abstract void deInit(Creature c);
    
}
