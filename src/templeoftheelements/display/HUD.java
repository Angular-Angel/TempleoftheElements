
package templeoftheelements.display;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;
import stat.NoSuchStatException;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.player.Clickable;
import templeoftheelements.player.Player;

/**
 *
 * @author angle
 */


public class HUD implements Renderable, Clickable {

    private Player player;
    private ArrayList<Icon> icons;
    
    public HUD(Player player) {
        this.player = player;
        icons  = new ArrayList<>();
    }
    
    public void addIcon(Icon i) {
        icons.add(i);
    }
    
    @Override
    public void draw() {
        
        float hp = 0;
        
        try {
            //How much health we got?

            hp = player.getCreature().getScore("HP")/player.getCreature().getScore("Max HP");
        } catch (NoSuchStatException ex) {
            Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //draw the health bar.
        GL11.glColor3f(255, 0, 0);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(1, 1);
        GL11.glVertex2f(game.res.width/20, 1);
        GL11.glVertex2f(game.res.width/20, game.res.height*hp/3);
        GL11.glVertex2f(1, game.res.height*hp/3);
//        GL11.glVertex2f(1, 1);
        GL11.glEnd();
        
        GL11.glColor3f(128, 128, 128);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(1, 1);
        GL11.glVertex2f(game.res.width/20, 1);
        GL11.glVertex2f(game.res.width/20, game.res.height/3);
        GL11.glVertex2f(1, game.res.height/3);
        GL11.glVertex2f(1, 1);
        GL11.glEnd();
        
        float mana = 0;
        
        try {
            //How much mana we got?

            mana = player.getCreature().getScore("Mana")/player.getCreature().getScore("Max Mana");
        } catch (NoSuchStatException ex) {
            Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //draw the mana bar.
        GL11.glColor3f(0, 0, 255);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(19*game.res.width/20, 1);
        GL11.glVertex2f(game.res.width, 1);
        GL11.glVertex2f(game.res.width, game.res.height*mana/3);
        GL11.glVertex2f(19*game.res.width/20, game.res.height*mana/3);
//        GL11.glVertex2f(1, 1);
        GL11.glEnd();
        
        GL11.glColor3f(128, 128, 128);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(19*game.res.width/20, 1);
        GL11.glVertex2f(game.res.width, 1);
        GL11.glVertex2f(game.res.width, game.res.height/3);
        GL11.glVertex2f(19*game.res.width/20, game.res.height/3);
        GL11.glVertex2f(19*game.res.width/20, 1);
        GL11.glEnd();
        
        float stamina = 0;
        
        try {
            //How much stamina we got?

            stamina = player.getCreature().getScore("Stamina")/player.getCreature().getScore("Max Stamina");
        } catch (NoSuchStatException ex) {
            Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Draw the Stamina Bar
        GL11.glColor3f(0, 255, 0);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(1, game.res.height);
        GL11.glVertex2f(game.res.width*stamina/3, game.res.height);
        GL11.glVertex2f(game.res.width*stamina/3, 19*game.res.height/20);
        GL11.glVertex2f(1, 19*game.res.height/20);
//        GL11.glVertex2f(1, 1);
        GL11.glEnd();
        
        GL11.glColor3f(128, 128, 128);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(1, game.res.height);
        GL11.glVertex2f(game.res.width/3, game.res.height);
        GL11.glVertex2f(game.res.width/3, 19*game.res.height/20);
        GL11.glVertex2f(1, 19*game.res.height/20);
        GL11.glVertex2f(1, 1);
        GL11.glEnd();
        
        //don't forget to draw the Icons.
        icons.forEach((Icon i) -> i.draw());
    }

    @Override
    public boolean isClicked(float x, float y) {
        for (Icon i : icons) if(i.isClicked(x, y)) return true;
        return false;
    }

    @Override
    public void mouseEvent(int button, int action, int mods) {
        for (Icon i : icons) if(i.isClicked(game.mouse.getX(), game.mouse.getY())) {
            i.mouseEvent(button, action, mods);
            return;
        }
    }

    @Override
    public float getDrawWidth() {
        return game.res.width;
    }

    @Override
    public float getDrawHeight() {
        return game.res.height;
    }
    
}
