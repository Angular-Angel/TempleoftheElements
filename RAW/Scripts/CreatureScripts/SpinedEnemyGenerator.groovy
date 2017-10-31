
import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import stat.*;
import generation.*;
import java.util.Random;
import com.samrj.devil.gl.Texture2D;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.player.characterwheel.CharacterTreeDef.AbilityDefinition;

/**
 *
 * @author angle
 */
class SpinedEnemyGenerator implements GenerationProcedure<CreatureDefinition> {
	
    int count = 0;
    Random random = new Random();
    
    public CreatureDefinition generate(Object o) {
        throw new UnsupportedOperationException();
    }
    
    public CreatureDefinition generate() {
        throw new UnsupportedOperationException();
    }
    
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

