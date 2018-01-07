import templeoftheelements.creature.CreatureGenerationProcedure;
import templeoftheelements.creature.CreatureDefinition;
import templeoftheelements.creature.Creature;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.AbilityDefinition;
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
        
        AbilityDefinition spines = new AbilityDefinition("Spines") {
            
            public String getDescription() {
                return "This creature has spines that damage anyone who gets too close."
            }
            
            public Ability getAbility(){
                return new TriggeredAbility(this) {
                    Creature creature;

                    public void init(Creature c) {
                        initValues(c);
                        c.addListener(this);
                    }
                    
                    public void initValues(Creature c) {
                        creature = c;
                    }

                    public void deInit(Creature c) {

                    }

                    public void handle(CreatureEvent event) {
                        if (event.type == CreatureEvent.Type.COLLIDED && event.thing instanceof Creature) {
                            System.out.println("Spined Collision: " + event.quantity);
                            event.thing.takeDamage(10, "Piercing");
                        }

                    }
                }
            }
        }
        
        definition.addAbility(spines);
        
        return definition;
    }
    
    public boolean isApplicable(CreatureDefinition definition) {
        throw new UnsupportedOperationException();
    }
    
}

