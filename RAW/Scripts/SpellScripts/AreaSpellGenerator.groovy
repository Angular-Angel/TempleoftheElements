import templeoftheelements.spells.AreaSpell;
import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.creature.AbilityDefinition;
import templeoftheelements.creature.Ability;
import templeoftheelements.display.VectorCircle;   // the groovy script importing.
import stat.NumericStat;
import templeoftheelements.player.AbilitySkill;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectContainer;

public class AreaSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilitySkill modify(AbilitySkill abilitySkill) {
        
        AbilityDefinition areaSpell = new AbilityDefinition("Area Spell") {
            
            Map<String, Effect> effects = new HashMap<>();
            
            public String getDescription() {
                return "This spell targets an enemy!";
            }
            public Ability getAbility() {
                AreaSpell spell = new AreaSpell(this, new VectorCircle(0.5));
       
                spell.stats.addAllStats(stats.viewStats());
                
                spell.addAllEffects(this);
                
                return spell;
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
        }
        
        areaSpell.stats.addStat("Cast Time", new NumericStat(0));
        areaSpell.stats.addStat("Mana Cost", new NumericStat(0));
        areaSpell.stats.addStat("Cooldown", new NumericStat(0));
        
        abilitySkill.abilityDef = areaSpell
        return abilitySkill;
    }
}