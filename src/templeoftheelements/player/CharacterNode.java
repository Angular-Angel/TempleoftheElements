
package templeoftheelements.player;

import com.samrj.devil.graphics.GraphicsUtil;
import com.samrj.devil.math.Vec2;
import org.lwjgl.opengl.GL11;
import stat.StatContainer;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.CharacterWheel.CharacterTree;

/**
 *
 * @author angle
 */


public class CharacterNode extends StatContainer implements Requirement , Renderable, Clickable {
    
    protected Vec2 position;
    protected CharacterTree tree;
    protected boolean acquired;
    protected Requirement requirements;
    protected final boolean free; //is this gem acquired automatically?
    
    public CharacterNode(Requirement requirements, CharacterTree tree, boolean free) {
        position = new Vec2();
        acquired = false;
        this.tree = tree;
        this.requirements = requirements;
        this.free = free;
    }
    
    public CharacterNode(Requirement requirements, CharacterTree tree) {
        this(requirements, tree, false);
    }
    
    /**
     * @return the position of this node.
     */
    public Vec2 getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Vec2 position) {
        this.position = position;
    }
    
    public boolean isAccessible() {
        return requirements.isMet();
    }

    /**
     * @return the acquired
     */
    public boolean isAcquired() {
        return acquired;
    }
    
    public void acquire() {
        acquired = true;
        tree.getPlayer().addAllStats(this);
    }

    @Override
    public boolean isMet() {
        return isAcquired();
    }

    @Override
    public void draw() {
        if (isAcquired()) {
            GL11.glColor3f(0, 0, 255);
        } else if (requirements.isMet()) {
            GL11.glColor3f(0, 255, 0);
        } else
            GL11.glColor3f(255, 0, 0);
            
        
        GraphicsUtil.drawCircle(position, 20, 32);
    }

    @Override
    public boolean isClicked(float x, float y) {
        return (position.dist(new Vec2(x, y)) <= 25);
    }

    @Override
    public void mouseEvent(int button, int action, int mods) {
        if (isAccessible()) acquire();
    }

    @Override
    public float getDrawWidth() {
        return 40;
    }

    @Override
    public float getDrawHeight() {
        return 40; 
    }
    
}
