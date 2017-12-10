import templeoftheelements.creature.CreatureGenerationProcedure;
import templeoftheelements.creature.CreatureDefinition;
import templeoftheelements.creature.Creature;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.PassiveAbility;
import stat.StatContainer;

/**
 *
 * @author angle
 */
class RegeneratingEnemyGenerator extends CreatureGenerationProcedure {
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script makes an enemy stronger.
        
        PassiveAbility regeneration = new PassiveAbility() {
            
            Creature creature;
            
            public String getDescription() {
                return "This ability heals it's owner very quickly.";
            }

            public Ability copy() {
                return this.getClass().newInstance();
            }

            public void init(Creature c) {
                creature = c;
            }
            
            public void step(float dt) {
                if (creature.stats.getScore("HP") < creature.stats.getScore("Max HP")) {
                    creature.stats.getStat("HP").modifyBase(dt);
                }
            }
            
            public void deInit(Creature c) {
                
            }
        }
        
        definition.addAbility(regeneration);
        
        return definition;
    }
    
}

