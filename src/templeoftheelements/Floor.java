
package templeoftheelements;

import java.util.HashSet;
import java.util.Set;
import templeoftheelements.collision.Room;

/**
 *
 * @author angle
 */


public class Floor {
    private Room entrance;
    private Set<Room> rooms;
    
    public Floor(Room room) {
        entrance = room;
        rooms = new HashSet<>();
    }
    
    private class RoomSchematic {
        
    }
}
