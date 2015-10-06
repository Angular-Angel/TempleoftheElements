
package templeoftheelements;

import generation.ProceduralGenerator;
import java.util.HashSet;
import java.util.Set;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.collision.Room;
import templeoftheelements.collision.Room.Door;
import util.Direction;

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
        rooms.add(room);
    }
    
    public void addRoom(Room room) {
        rooms.add(room);
    }
    
    public Room getEntrance() {
        return entrance;
    }
    
    public static class FloorSchematic implements ProceduralGenerator<Floor>{
        private RoomSchematic[][] schematic;
        private Set<RoomSchematic> rooms;
        
        public FloorSchematic(int width, int height) {
            schematic = new RoomSchematic[width][height];
            for (int i = 0; i < schematic.length; i++) {
                for (int j = 0; j < schematic[0].length; j++) {
                    schematic[i][j] = null;
                }
            }
            this.rooms = new HashSet<>();
        }

        @Override
        public Floor generate() {
            RoomSchematic startRoom = generateRoom(schematic.length/2, schematic[0].length/2, 4, 5);
            rooms.add(startRoom);
            generateBranch(schematic.length/2, schematic[0].length/2, Direction.NORTH, 5, true);
            generateBranch(schematic.length/2, schematic[0].length/2, Direction.EAST, 5, false);
            generateBranch(schematic.length/2, schematic[0].length/2, Direction.SOUTH, 5, false);
            generateBranch(schematic.length/2, schematic[0].length/2, Direction.WEST, 5, false);
            
            Floor floor = new Floor(startRoom.generate());
            
            for (RoomSchematic roomSchematic : rooms) {
                floor.addRoom(roomSchematic.generate());
            }
            
            for (int i = 0; i < schematic.length; i++) {
                for (int j = 0; j < schematic[0].length; j++) {
                    if (schematic[i][j] != null) {
                        if (i < schematic.length-1 && schematic[i+1][j] != null 
                            && schematic[i][j] != schematic[i+1][j]) {
                            RoomSchematic room1 = schematic[i][j];
                            RoomSchematic room2 = schematic[i+1][j];
                            int x1 = (i +1 - room1.x) * 5;
                            int y1 = (j +1 - room1.y) * 5;
                            Door door1 = room1.room.createDoor(1, 1, x1, y1);
                            Door door2 = room2.room.createDoor(x1, y1, 1, 1);
                            door1.setDestination(door2);
                            door2.setDestination(door1);
                        }
                        if (j < schematic[0].length-1 && schematic[i][j+1] != null 
                            && schematic[i][j] != schematic[i][j+1]) {
                            RoomSchematic room1 = schematic[i][j];
                            RoomSchematic room2 = schematic[i][j+1];
                            int x1 = (i +1 - room1.x) * 5;
                            int y1 = (j +1 - room1.y) * 5;
                            Door door1 = room1.room.createDoor(1, 1, x1, y1);
                            Door door2 = room2.room.createDoor(x1, y1, 1, 1);
                            door1.setDestination(door2);
                            door2.setDestination(door1);
                        }
                    }
                }
            }
            
            return floor;
        }
        
        public void generateBranch(int x, int y, Direction dir, int rooms, boolean main) {
            for (int i = 0; i < rooms; i++) {
                while(schematic[x][y] != null) {
                    x += dir.xmod; y += dir.ymod;
                }
                int roomWidth = 1, roomHeight = 1;
                while(roomWidth < 30 && roomHeight < 30 && schematic[x+roomWidth][y+roomHeight] != null) {
                    roomWidth++; roomHeight++;
                    if (dir.xmod < 0) x--;
                    if (dir.ymod < 0) y--;
                }

                generateRoom(x, y, roomWidth, roomHeight);
                System.out.println("" + x + ", " + y + ", " + roomWidth + ", " + roomHeight);
                x += roomWidth;
                y += roomHeight;
            }
            
        }
        
        public RoomSchematic generateRoom(int x, int y, int width, int height) {
            RoomSchematic room = new RoomSchematic(x, y, width, height);
            x -= width/2;
            y -= height/2;
            int endX = x + room.width;
            int endY = y + room.height;
            
            for (; x < endX; x++) {
                for (; y < endY; y++) {
                    schematic[x][y] = room;
                }
            }
            rooms.add(room);
            
            return room;
            
        }
    }
    
    public static class RoomSchematic implements ProceduralGenerator<Room>{

        public int x, y, width, height;
        
        public Room room;
        
        public RoomSchematic(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            
        }
        
        @Override
        public Room generate() {
            room = new Room(width*5, height*5, game.registry.textures.get("Stone Floor.png"));
            return room;
        }
        
    }
}
