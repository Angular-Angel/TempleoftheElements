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
            
            StatContainer creature;
            
            public String getDescription() {
                return "This ability heals it's owner very quickly.";
            }

            public Ability copy() {
                return this.getClass().newInstance();
            }

            public void init(StatContainer c) {
                creature = c;
                super.init(c);
            }
            
            public void step(float dt) {
                if (creature.getScore("HP") < creature.getScore("Max HP")) {
                    creature.getStat("HP").modifyBase(dt);
                    System.out.println(creature.getScore("HP"));
                }
            }
        }
        
        definition.addAbility(regeneration);
        
        return definition;
    }
    
}

