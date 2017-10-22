
import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import java.lang.reflect.*;
import stat.*;
import generation.*;
import java.util.Random;
import com.samrj.devil.gl.Texture2D;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;

/**
 *
 * @author angle
 */
class RegeneratingEnemyGenerator implements GenerationProcedure<CreatureDefinition> {
	
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
        
        PassiveAbility regeneration = new PassiveAbility() {
            
            Creature creature;
            
            public String getDescription() {
                return "This ability heals it's owner very quickly.";
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
    
    public boolean isApplicable(CreatureDefinition definition) {
        throw new UnsupportedOperationException();
    }
    
}

