import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import stat.*;
import generation.*;
import java.util.Random;
import static templeoftheelements.TempleOfTheElements.game;
import org.jbox2d.collision.shapes.CircleShape;

/**
 *
 * @author angle
 */
class RockyRoom implements GenerationProcedure<Room> {
    
    Random random = new Random();
    
    public Room generate() {
        throw new UnsupportedOperationException();
    }
    
    public Room generate(Object o) {
        throw new UnsupportedOperationException();
    }
    
    public Room modify(Room room) {
        for (int i = 0; i < room.width/10; i++) {
            int x, y, size;
            x = -room.width/3 + random.nextInt((int) room.width/1.5);
            y = -room.width/3 + random.nextInt((int) room.width/1.5);
            x /= 2;
            y /= 2;
            size = random.nextInt((int) room.width/5);
            Obstacle obstacle = new Obstacle(x, y, new CircleShape(), new VectorCircle(size), size);
            room.add(obstacle);
        }
        
        for (int i = 0; i < room.width/10; i++) {
            Creature creature = game.registry.creatureList.get(random.nextInt(game.registry.creatureList.size())).genCreature();
            room.add(creature);
        }
    }
    
    public boolean isApplicable(Room room) {
        throw new UnsupportedOperationException();
    }
    
    
}