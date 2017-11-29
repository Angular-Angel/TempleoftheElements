import templeoftheelements.creature.CreatureGenerationProcedure;
import templeoftheelements.creature.CreatureDefinition;
import templeoftheelements.creature.Creature;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.TriggeredAbility;
import templeoftheelements.creature.CreatureEvent;
import stat.StatContainer;
import static templeoftheelements.TempleOfTheElements.game;

/**
 *
 * @author angle
 */
class SpinedEnemyGenerator extends CreatureGenerationProcedure {
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script makes an enemy stronger.
        
        TriggeredAbility ability = new TriggeredAbility() {
            
            Creature creature;
            
            public String getName() {
                return "Spines";
            }

            public String getDescription() {
                return "This creature has spines that damage anyone who gets too close."
            }

            public Ability copy() {
                return this.getClass().newInstance();
            }

            public void init(Creature c) {
                creature = c;
            }
            
            public void handle(CreatureEvent event) {
                if (event.type == CreatureEvent.Type.COLLIDED && event.thing instanceof Creature) {
                    event.thing.takeDamage(10, "Piercing");
                }
                   
            }
            
            public void init(StatContainer c) {}
        }
        
        definition.addAbility(ability);
        
        return definition;
    }
    
    public boolean isApplicable(CreatureDefinition definition) {
        throw new UnsupportedOperationException();
    }
    
}

