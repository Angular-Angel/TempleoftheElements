/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.creature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import stat.StatContainer;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectContainer;

/**
 *
 * @author angle
 */
public abstract class AbilityDefinition implements EffectContainer {
    public Ability.Detail usage;
    public Ability.Detail targeting;
    public final String name;
    public final ArrayList<Ability.Detail> costDetails;
    public final ArrayList<Ability.Detail> effectDetails;
    public final ArrayList<Ability.Detail> scalingDetails;
    public final StatContainer stats;
    private Map<String, Effect> effects;
    
    public AbilityDefinition(String name) {
        this.name = name;
        this.costDetails = new ArrayList<>();
        this.effectDetails = new ArrayList<>();
        this.scalingDetails = new ArrayList<>();    
        stats = new StatContainer();
        effects = new HashMap<>();
    }
    
    @Override
    public void addEffect(Effect effect) {
        effects.put(effect.name, effect);
    }
    
    @Override
    public boolean containsEffect(String s) {
        return effects.containsKey(s);
    }
    
    @Override
    public Effect getEffect(String s) {
        return effects.get(s);
    }
    
    
    @Override
    public void addAllEffects(EffectContainer effects) {
        for (Effect e : effects.getAllEffects()) {
            addEffect(e);
        }
    }
    
    @Override
    public Collection<Effect> getAllEffects() {
        return effects.values();
    }
    
    public abstract Ability getAbility();
    
    public abstract String getDescription();
}
