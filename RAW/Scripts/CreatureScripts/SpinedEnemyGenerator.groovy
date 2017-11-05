import templeoftheelements.creature.CreatureGenerationProcedure;
import templeoftheelements.creature.CreatureDefinition;
import templeoftheelements.creature.Creature;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.TriggeredAbility;
import templeoftheelements.creature.CreatureEvent;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;

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

            public AbilityDefinition getDef() {
                throw new UnsupportedOperationException();
            }

            public void setDef(AbilityDefinition abilityDef) {
                throw new UnsupportedOperationException();
            }

            public void init(Creature c) {
                creature = c;
            }
            
            public void handle(CreatureEvent event) {
                if (event.type == CreatureEvent.Type.COLLIDED && event.thing instanceof Creature) {
                    event.thing.takeDamage(10, "Piercing");
                }
                   
            }
        }
        
        definition.addAbility(ability);
        
        return definition;
    }
    
    public boolean isApplicable(CreatureDefinition definition) {
        throw new UnsupportedOperationException();
    }
    
}

