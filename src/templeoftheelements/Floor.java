
package templeoftheelements;

import generation.GenerationProcedure;
import generation.ProceduralGenerator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
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
    private final Room entrance;
    private final Set<Room> rooms;
    
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
            RoomSchematic startRoom = generateRoom(schematic.length/2, schematic[0].length/2, 3, 3, 0);
            rooms.add(startRoom);
            generateBranch(schematic.length/2, schematic[0].length/2, Direction.NORTH, 5, true);
            generateBranch(schematic.length/2, schematic[0].length/2, Direction.EAST, 5, false);
            generateBranch(schematic.length/2, schematic[0].length/2, Direction.SOUTH, 5, false);
            generateBranch(schematic.length/2, schematic[0].length/2, Direction.WEST, 5, false);
//            
            Floor floor = new Floor(startRoom.generate());
//            
//            for (int i = 0; i < schematic.length; i++) {
//                for (int j = 0; j < schematic[0].length; j++) {
//                    if (schematic[i][j] == null) {
//                        schematic[i][j] = new RoomSchematic(i, j, 1, 1);
//                        rooms.add(schematic[i][j]);
//                    }
//                }
//            }
            
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
                            float x1 = (-room1.width*5);
                            float y1 = -(j - room1.y)*10;
                            float x2 = (room2.width*5);
                            float y2 = -(j - room2.y)*10;
                            if (room1.width % 2 == 0)
                                y1 -= 5;
                            if (room2.width % 2 == 0)
                                y2 -= 5;
                            Door door1 = room1.room.createDoor(x1 + 2, y1, x1, y1);
                            Door door2 = room2.room.createDoor(x2 - 2, y2, x2, y2);
                            door1.setDestination(door2);
                            door2.setDestination(door1);
                        }
                        if (j < schematic[0].length-1 && schematic[i][j+1] != null 
                            && schematic[i][j] != schematic[i][j+1]) {
                            RoomSchematic room1 = schematic[i][j];
                            RoomSchematic room2 = schematic[i][j+1];
                            float x1 = -(i - room1.x)*10;
                            float y1 = (-room1.height*5);
                            float x2 = -(i - room2.x)*10;
                            float y2 = (room2.height*5);
                            if (room1.height % 2 == 0)
                                x1 -= 5;
                            if (room2.height % 2 == 0)
                                x2 -= 5;
                            Door door1 = room1.room.createDoor(x1, y1 + 2, x1, y1);
                            Door door2 = room2.room.createDoor(x2, y2 - 2, x2, y2);
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
                int roomWidth = 3, roomHeight = 3;
//                while(roomWidth < 30 && roomHeight < 30 && schematic[x+roomWidth][y+roomHeight] != null) {
//                    roomWidth++; roomHeight++;
//                    if (dir.xmod < 0) x--;
//                    if (dir.ymod < 0) y--;
//                }

                generateRoom(x, y, roomWidth, roomHeight, 1);
//                if (dir.xmod >= 0) x += roomWidth;
//                if (dir.ymod >= 0) y += roomHeight;
                x += (roomWidth - 1) * dir.xmod;
                y += (roomHeight - 1) * dir.ymod;
            }
            
        }
        
        public RoomSchematic generateRoom(int x, int y, int width, int height, int complexity) {
            RoomSchematic room = new RoomSchematic(x, y, width, height, complexity);
            x -= width/2;
            y -= height/2;
            int endX = x + room.width;
            int endY = y + room.height;
            
            for (; x < endX; x++) {
                for (int tempY = y; tempY < endY; tempY++) {
                    if (schematic[x][tempY] == null) schematic[x][tempY] = room;
                }
            }
            rooms.add(room);
            
            return room;
            
        }

        @Override
        public Floor generate(Object o) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    public static class RoomSchematic implements ProceduralGenerator<Room>{

        public int x, y, width, height, complexity;
        
        public Room room;
        
        public static ArrayList<GenerationProcedure<Room>> procedures = new ArrayList<>();
        
        public static Random random = new Random();
        
        public RoomSchematic(int x, int y, int width, int height, int complexity) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.complexity = complexity;
        }
        
        @Override
        public Room generate() {
            if (room != null) return room;
            room = new Room(width*10, height*10, game.registry.textures.get("Stone Floor.png"));
            
            for (int i = 0; i < complexity; i++) 
                procedures.get(random.nextInt(procedures.size())).modify(room);
            
            return room;
        }

        @Override
        public Room generate(Object o) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
}
