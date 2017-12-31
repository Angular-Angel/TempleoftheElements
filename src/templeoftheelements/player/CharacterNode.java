
package templeoftheelements.player;

import com.samrj.devil.graphics.GraphicsUtil;
import com.samrj.devil.math.Vec2;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;
import stat.NoSuchStatException;
import stat.StatContainer;
import stat.StatDescriptor;
import templeoftheelements.TempleOfTheElements;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.creature.Creature;
import templeoftheelements.display.CharacterScreen;
import templeoftheelements.display.Renderable;

/**
 *
 * @author angle
 */


public class CharacterNode implements Requirement , Renderable, Clickable {
    
    
    private Vec2 position;
    protected CharacterTree tree;
    protected boolean acquired;
    protected Requirement requirements;
    public String description;
    protected final boolean free; //is this gem acquired automatically?
    private int layer;
    ArrayList<CharacterNode> children; //These are package private
    ArrayList<CharacterNode> parents;
    public StatContainer stats;
    
    public CharacterNode(Requirement requirements, CharacterTree tree, boolean free) {
        position = new Vec2();
        acquired = false;
        this.tree = tree;
        this.requirements = requirements;
        this.free = free;
        description = "";
        children = new ArrayList<>();
        parents = new ArrayList<>();
        stats = new StatContainer();
        
        CharacterNode self = this;
        
        layer = 0;
        
        requirements.getNodes().forEach((CharacterNode node) -> {
            node.children.add(self);
            self.parents.add(node);
            if (node.layer >= this.layer)
                this.layer = node.layer +1;
        });
    }
    
    public CharacterNode(Requirement requirements, CharacterTree tree) {
        this(requirements, tree, false);
    }
    
    public CharacterNode(CharacterTree tree) {
        position = new Vec2();
        acquired = false;
        this.tree = tree;
        requirements =  new NoRequirement();
        this.free = false;
        description = "";
        children = new ArrayList<>();
        parents = new ArrayList<>();
        stats = new StatContainer();
        layer = 0;
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
        return !isAcquired() && requirements.isMet();
    }

    /**
     * @return the acquired
     */
    public boolean isAcquired() {
        return acquired;
    }
    
    public void acquire() {
        acquired = true;
        tree.getCreature().stats.increaseAllStats(this.stats);
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
            
        
        GraphicsUtil.drawCircle(getPosition(), 20, 32);
        
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
            
            GL11.glVertex2f(source.getPosition().x, source.getPosition().y);
            GL11.glVertex2f(this.getPosition().x, this.getPosition().y);
            
            GL11.glEnd();
        }
    }

    @Override
    public boolean isClicked(float x, float y) {
        return (getPosition().dist(new Vec2(x, y)) <= 25);
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
    
    public void init(Creature c) {
        stats.init(c.stats);
    }
    
    public void setReqs(Requirement requirement) {
        this.requirements = requirement;
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
        for (String s : stats.getStatList()) {
            try {
                i -= 20;
                if (stats.getStat(s).getStatDescriptor().type == StatDescriptor.StatType.NUMBER)
                    game.font.draw(s + ": +" + (int) stats.getScore(s), new com.samrj.devil.math.Vec2(screen.x +2, screen.y + i));
                else if (stats.getStat(s).getStatDescriptor().type == StatDescriptor.StatType.PERCENTAGE)
                    game.font.draw(s + ": +" + (int) (stats.getScore(s) * 100) + "%", new com.samrj.devil.math.Vec2(screen.x +2, screen.y + i));
            } catch (NoSuchStatException | NullPointerException ex) {
                Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<CharacterNode> getNodes() {
        ArrayList<CharacterNode> ret =  new ArrayList<>();
        ret.add(this);
        return ret;
    }

    /**
     * @return the layer
     */
    public int getLayer() {
        return layer;
    }
}
