import templeoftheelements.creature.CreatureGenerationProcedure;
import templeoftheelements.creature.CreatureDefinition;
import templeoftheelements.creature.Creature;
import templeoftheelements.creature.SteppableAbility;
import templeoftheelements.creature.AbilityDefinition;
import templeoftheelements.creature.Ability;
import stat.StatContainer;

/**
 *
 * @author angle
 */
class RegeneratingEnemyGenerator extends CreatureGenerationProcedure {
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script makes an enemy stronger.
        
        AbilityDefinition regeneration = new AbilityDefinition("Regeneration") {
            
            public String getDescription() {
                return "This ability heals it's owner very quickly.";
            }
            
            public Ability getAbility() {
            
                return new SteppableAbility(this) {
                    Creature creature;

                    public void init(Creature c) {
                        initValues(c);
                        c.addSteppable(this);
                    }
                    
                    public void initValues(Creature c) {
                        creature = c;
                    }

                    public void step(float dt) {
                        if (creature.stats.getScore("HP") < creature.stats.getScore("Max HP")) {
                            creature.stats.getStat("HP").modifyBase(dt);
                        }
                    }

                    public void deInit(Creature c) {}
                }
            }
        }
        
        definition.addAbility(regeneration);
        
        return definition;
    }
    
}

