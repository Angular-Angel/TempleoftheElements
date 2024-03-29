 
package templeoftheelements.item;

import com.samrj.devil.math.Vec2i;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;
import stat.NoSuchStatException;
import stat.Stat;
import stat.StatContainer;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.collision.Position;
import templeoftheelements.collision.Positionable;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.Clickable;
import templeoftheelements.effect.EffectSource;

/**
 *
 * @author angle
 */


public abstract class Item implements Renderable, Clickable, Positionable, EffectSource {

    private Position position;
    private Vec2i size;
    private Renderable sprite;
    private String name;
    private int level;
    private boolean inInventory;
    public StatContainer stats;
    
    public Item (String name, Renderable sprite, int level) {
        this(name, sprite, new Vec2i(1, 1), new StatContainer(), level);
    }
    
    public Item (String name, Renderable sprite, int width, int height, int level) {
        this(name, sprite, new Vec2i(width, height), new StatContainer(), level);
    }
    
    public Item (String name, Renderable sprite, StatContainer stats, int level) {
        this(name, sprite, new Vec2i(1, 1), stats, level);
    }
    
    public Item (String name, Renderable sprite, Vec2i size, StatContainer stats, int level) {
        this.stats = new StatContainer(stats);
        this.name = name;
        this.sprite = sprite;
        this.position = new Position();
        this.size = size;
        inInventory = false;
        this.level = level;
    }
    
    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glTranslated(getPosition().x, getPosition().y, 0);
        sprite.draw();
        GL11.glPopMatrix();
    }
    
    public void draw(int x, int y, int width, int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        float xScale = width/sprite.getDrawWidth(), yScale = height/sprite.getDrawHeight();
        GL11.glScalef(xScale, yScale, 1f);
        sprite.draw();
        GL11.glPopMatrix();
    }
    
    
    public void draw(float x, float y, float width, float height) {
        draw((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isClicked(float x, float y) {
        try {
            if (Math.abs(position.x - x) < stats.getScore("Size") && 
                Math.abs(position.y - y) < stats.getScore("Size"))
                return true;
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public void mouseEvent(int button, int action, int mods) {
        game.player.pickUpItem(this);
    }

    /**
     * @return the size
     */
    public Vec2i getSize() {
        return new Vec2i(size);
    }

    /**
     * @return the inInventory
     */
    public boolean isInInventory() {
        return inInventory;
    }

    /**
     * @param inInventory the inInventory to set
     */
    public void setInInventory(boolean inInventory) {
        this.inInventory = inInventory;
    }

    @Override
    public float getDrawWidth() {
        return sprite.getDrawWidth();
    }

    @Override
    public float getDrawHeight() {
        return sprite.getDrawHeight();
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }
    
    @Override
    public boolean isEnemy() {
        return false;
    }
    
}
