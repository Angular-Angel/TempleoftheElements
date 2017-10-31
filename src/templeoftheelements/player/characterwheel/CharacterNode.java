
package templeoftheelements.player.characterwheel;

import com.samrj.devil.graphics.GraphicsUtil;
import com.samrj.devil.math.Vec2;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;
import stat.NoSuchStatException;
import stat.Stat;
import stat.StatContainer;
import templeoftheelements.TempleOfTheElements;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.display.CharacterScreen;
import templeoftheelements.display.Renderable;
import templeoftheelements.player.characterwheel.CharacterTreeDef.NodeDefinition;
import templeoftheelements.player.characterwheel.CharacterWheel.CharacterTree;
import templeoftheelements.player.Clickable;
import templeoftheelements.player.Inventory;

/**
 *
 * @author angle
 */


public class CharacterNode extends StatContainer implements Requirement , Renderable, Clickable {
    
    protected Vec2 position;
    protected CharacterTree tree;
    protected boolean acquired;
    protected Requirement requirements;
    public String description;
    protected final boolean free; //is this gem acquired automatically?
    public NodeDefinition nodeDef;
    public int cluster;
    
    public CharacterNode(Requirement requirements, CharacterTree tree, boolean free) {
        position = new Vec2();
        acquired = false;
        this.tree = tree;
        this.requirements = requirements;
        this.free = free;
        description = "";
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
        tree.getCreature().addAllStats(this);
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
        
        requirements.draw(this);
    }
    
    @Override
    public void draw(Requirement req) {
        
        if (req instanceof CharacterNode) {
            CharacterNode source = (CharacterNode) req;
            if (source.isAcquired()) {
                GL11.glColor3f(0, 0, 255);
            } else if (source.isAccessible()) {
                GL11.glColor3f(0, 255, 0);
            } else
                GL11.glColor3f(255, 0, 0);
            
            GL11.glBegin(GL11.GL_LINE_LOOP);
            
            GL11.glVertex2f(source.position.x, source.position.y);
            GL11.glVertex2f(this.position.x, this.position.y);
            
            GL11.glEnd();
        }
    }

    @Override
    public boolean isClicked(float x, float y) {
        return (position.dist(new Vec2(x, y)) <= 25);
    }

    @Override
    public void mouseEvent(int button, int action, int mods) {
        if (isAccessible() && 
            TempleOfTheElements.game.player.characterPoints > 0) {
            acquire();
            TempleOfTheElements.game.player.characterPoints--;
        }
    }

    @Override
    public float getDrawWidth() {
        return 40;
    }

    @Override
    public float getDrawHeight() {
        return 40; 
    }

    @Override
    public ArrayList<Requirement> getReqs() {
        ArrayList<Requirement> ret = new ArrayList<>();
        ret.add(this);
        return ret;
    }
    
    public void showDescription(CharacterScreen.StatScreen screen) {
        float i = screen.height - 12;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor3f(255, 0, 0);
        game.font.getTexture().bind();
        if (description.length() > 0) {
            i -= 20;
            screen.height += 20;
            game.font.draw(description, new com.samrj.devil.math.Vec2(screen.x +2, screen.y + i));
        }
        for (String s : getStatList()) {
            try {
                i -= 20;
                if (getStat(s).statDescriptor.type == Stat.Type.INTEGER)
                    game.font.draw(s + ": " + getScore(s), new com.samrj.devil.math.Vec2(screen.x +2, screen.y + i));
                else if (getStat(s).statDescriptor.type == Stat.Type.PERCENTAGE)
                    game.font.draw(s + ": " + getScore(s) + "%", new com.samrj.devil.math.Vec2(screen.x +2, screen.y + i));
            } catch (NoSuchStatException ex) {
                Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}