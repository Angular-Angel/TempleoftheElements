
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

/**
 *
 * @author angle
 */
class StrongEnemyGenerator implements GenerationProcedure<CreatureDefinition> {
	
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
        
        definition.getStat("Strength").modifyBase(50);
        
        return definition;
    }
    
    public boolean isApplicable(CreatureDefinition definition) {
        throw new UnsupportedOperationException();
    }
    
}

