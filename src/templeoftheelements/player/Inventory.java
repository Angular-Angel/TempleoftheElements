 
package templeoftheelements.player;

import com.samrj.devil.math.Vec4;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import stat.NoSuchStatException;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.collision.Creature;
import templeoftheelements.collision.Creature.Hand;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Screen;
import templeoftheelements.item.Consumable;
import templeoftheelements.item.Equipment;
import templeoftheelements.item.Item;

/**
 *
 * @author angle
 */


public class Inventory extends Screen {
    
    private HashSet<Item> inventory;
    private ItemSlot origin;
    private Item dragging;
    private Player player;
    private ArrayList<ItemSlot> itemSlots;
    private StatScreen itemDisplay;
    
    public final int gridX, gridY, gridWidth, gridHeight, cellWidth, cellHeight;
    
    public Inventory(Player player) {
        inventory = new HashSet<>();
        this.player = player;
        gridX = game.getResolutionWidth()/2;
        gridY = 50;
        gridWidth = 20;
        gridHeight = 10;
        cellWidth = 30;
        cellHeight = 30;
        itemSlots = new ArrayList<>();
        int i = 0;
        for (Creature.BodyPart b : player.getCreature().bodyParts) {
            float x, y;
            y = game.getResolutionHeight() -110 - 105 * (i/3);
            x = 40 + 105 * (i % 3);
            if (b instanceof Hand) itemSlots.add(new WeaponSlot((Hand) b, x, y, 90, 90));
            else itemSlots.add(new ItemSlot(b, x, y, 90, 90));
            i++;
        }
        itemDisplay = new StatScreen(0, 0, 0, 0);
    }
    
    public int size() {
        return inventory.size();
    }
    
    public boolean addItem(Item i) {
        for (int x = 0; x < gridX; x++) {
            for (int y = 0; y < gridY; y++) {
                if (itemFits(x, y, i)) {
                    inventory.add(i);
                    i.setPosition(new Position(x, y));
                    i.setInInventory(true);
                    return true;
                }
            }
        }
        return false;
        
    }
    
    public boolean inInventory(float x, float y) {
        if (x < 0 || x > gridWidth -1 || y < 0 || y > gridHeight -1) {
            return false;
        } else return true;
    }
    
    public boolean itemFits(int x, int y, Item i) {
        if (!inInventory(x, y)) return false;
        
        for (Item item : inventory) {
            if (item.isInInventory() && (item.getPosition().x + item.getSize().x - 1) >= x 
                && item.getPosition().x <= (x + i.getSize().x - 1) && 
                (item.getPosition().y + item.getSize().y - 1) >= y && item.getPosition().y <= (y + i.getSize().y - 1))
                return false;
        }
        return true;
    }
    
    public Item itemAt(int x, int y) {
        for (Item item : inventory) {
            if ((item.getPosition().x + item.getSize().x - 1) >= x && item.getPosition().x <= (x) && 
                (item.getPosition().y + item.getSize().y - 1) >= y && item.getPosition().y <= (y))
                return item;
        }
        return null;
    }
    
    public void removeItem(Item i) {
        inventory.remove(i);
    }
    
    @Override
    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glColor3f(255, 0, 0);
        GL11.glPushMatrix();
        GL11.glTranslatef(gridX, gridY, 0);
        
        for (int i = 0; i <= gridHeight; i++) {
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex2f(0, i*cellHeight);
            GL11.glVertex2f(cellWidth*gridWidth, i*cellHeight);
            GL11.glEnd();
        }
        
        for (int i = 0; i <= gridWidth; i++) {
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex2f(i*cellWidth, 0);
            GL11.glVertex2f(i*cellWidth, cellHeight*gridHeight);
            GL11.glEnd();
        }
        
        for (Item i : inventory) {
            GL11.glPushMatrix();
            float xOffset = i.getPosition().x*cellWidth + i.getSize().x*cellWidth/2 -1;
            float yOffset = i.getPosition().y*cellHeight + i.getSize().y*cellHeight/2 +1;
            GL11.glTranslated(xOffset, yOffset, 0);
            i.draw(0, 0, i.getSize().x*cellWidth -1, i.getSize().y*cellHeight -1);
            GL11.glPopMatrix();
        }
        
        GL11.glPopMatrix();
        
        for (ItemSlot i : itemSlots) i.draw();
        
        if (dragging != null) dragging.draw(game.mouse.getX(), game.mouse.getY(), 
                dragging.getSize().x*cellWidth -1, dragging.getSize().y*cellHeight -1);
        
        itemDisplay.draw();

    }
    
    @Override
    public void mouseMoved(float x, float y, float dx, float dy) {
        int tx = (int) (x - gridX);
        tx /= cellWidth;
        int ty = (int) (y - gridY);
        ty /= cellHeight;
        Item item = itemAt(tx, ty);
        
        if (item != null) {
            itemDisplay.setItem(item);
            itemDisplay.x = game.mouse.getX();
            itemDisplay.y = game.mouse.getY();
            itemDisplay.display = true;
        } else itemDisplay.display = false;
    }

    public void mouseEvent(int button, int action, int mods) {
        int x = (int) (game.mouse.getX() - gridX);
        x /= cellWidth;
        int y = (int) (game.mouse.getY() - gridY);
        y /= cellHeight;
        Item item = itemAt(x, y);
        
        if (button == GLFW.GLFW_MOUSE_BUTTON_1 && game.mouse.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_1) == true) {
            if (game.mouse.getX() >= gridX && game.mouse.getX() <= gridX + gridWidth * cellWidth && 
                game.mouse.getY() >= gridY && game.mouse.getY() <= gridY + gridHeight * cellHeight) {
                
                if (item != null) {
                    dragging = item;
                    item.setInInventory(false);
                    origin = null;
                }
            } else {
                for (ItemSlot i : itemSlots) {
                    if (i.item != null && i.contains(game.mouse.getX(), game.mouse.getY())) {
                        dragging = i.item;
                        i.item.setInInventory(false);
                        origin = i;
                        break;
                    }
                }
            }
        } else if (dragging != null && button == GLFW.GLFW_MOUSE_BUTTON_1 && game.mouse.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_1) == false) {
            x = (int) (game.mouse.getX() - gridX);
            x /= cellWidth;
            x -= dragging.getSize().x/2;
            y = (int) (game.mouse.getY() - gridY);
            y /= cellHeight;
            y -= dragging.getSize().y/2;
            if (itemFits(x, y, dragging)) {
                if (origin != null) {
                    origin.unequip();
                }
                dragging.setPosition(new Position(x, y));
                dragging = null;
                origin = null;
                return;
            }
            else if (dragging instanceof Equipment) {
                for (ItemSlot i : itemSlots) {
                    if (i.contains(game.mouse.getX(), game.mouse.getY()) && i.canEquip((Equipment) dragging)) {
                        i.equip((Equipment) dragging);
                        dragging.setInInventory(true);
                        if (origin == null) {
                            inventory.remove(dragging);
                        }
                        else origin.unequip();
                        dragging = null;
                        origin = null;
                        return;
                    }
                }
            }
            dragging.setInInventory(true);
            dragging = null;
            origin = null;
        } else if (button ==  GLFW.GLFW_MOUSE_BUTTON_2 && game.mouse.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_2)) {
            if (item instanceof Consumable) {
                ((Consumable) item).use(player.getCreature());
                removeItem(item);
            }
        }
        
    }

    public void in(int key, int action, int mods) {
        if (key == GLFW.GLFW_KEY_ESCAPE) stop();
    }

    @Override
    public void step() {
        
    }

    @Override
    public void keyEvent(int key, int action, int mods)  {
        if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) stop();
    }
    
    public class StatScreen extends SubScreen {
        
        private Item item;
        public boolean display;

        public StatScreen(float x, float y, float width, float height) {
            super(x, y, width, height);
        }
        
        public void setItem(Item i) {
            if (item == i) return;
            item = i;
            width = 0;
            height = (item.getStatList().size() + 1) * 20;
            for (String s : item.getStatList()) 
                if (width < s.length() * 20 + 100) width = s.length() * 20 + 100;
        }
        
        @Override
        public void draw() {
            if (!display) return;
            super.draw();
            int i = -10;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor3f(255, 0, 0);
            game.font.getTexture().bind();
            for (String s : item.getStatList()) {
                try {
                    game.font.draw(s + ": " + item.getScore(s), new com.samrj.devil.math.Vec2(x, y + i));
                    i += 20;
                } catch (NoSuchStatException ex) {
                    Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            game.font.draw(item.getName(), new com.samrj.devil.math.Vec2(x, y + i));
        }
        
    }
    
    public class ItemSlot extends SubScreen {
        public final Creature.BodyPart bodypart;
        public Equipment item;
        
        public ItemSlot(Creature.BodyPart bodypart, float x, float y, float width, float height) {
            this(bodypart, x, y, width, height, new Vec4(0, 0, 0, 1), new Vec4(1, 1, 1, 1));
        }
        
        public ItemSlot(Creature.BodyPart bodypart, float x, float y, float width, float height, Vec4 background, Vec4 border) {
            super(x, y, width, height, background, border);
            this.bodypart = bodypart;
            item = null;
        }
        
        public boolean contains(float x, float y) {
            return (x >= this.x && x <= (this.x + this.width) && y >= this.y && y <= (this.y + this.height));
        }
        
        public boolean canEquip(Equipment e) {
            return bodypart.canEquip(e);
        }
        
        public void unequip() {
            if (item != null) {
                bodypart.unequip();
                if (!addItem(item)) player.getCreature().drop(item);
                item = null;
            }
        }
        
        public boolean equip(Equipment i) {
            if (bodypart.canEquip(i)) {
                bodypart.equip(i);
                item = i;
                return true;
            }
            return false;
        }
        
        @Override
        public void draw() {
            super.draw();
            if (item != null) item.draw(x + width/2, y + height/2, width, height);
        }
        
    }
    
    public class WeaponSlot extends ItemSlot {

        public WeaponSlot(Creature.Hand bodypart, float x, float y, float width, float height) {
            this(bodypart, x, y, width, height, new Vec4(0, 0, 0, 1), new Vec4(1, 0, 0, 1));
        }
        
        public WeaponSlot(Creature.Hand bodypart, float x, float y, float width, float height, Vec4 background, Vec4 border) {
            super(bodypart, x, y, width, height, background, border);
        }
        
        public void unequip() {
            if (item != null) {
                bodypart.unequip();
                if (!addItem(item)) player.getCreature().drop(item);
                item = null;
                player.refactorHUD();
            }
        }
        
        public boolean equip(Equipment i) {
            if (bodypart.canEquip(i)) {
                if (i != null) unequip();
                bodypart.equip(i);
                item = i;
                player.refactorHUD();
                return true;
            }
            return false;
        }
        
    }
}
