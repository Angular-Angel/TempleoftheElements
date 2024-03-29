
package templeoftheelements.player;

import templeoftheelements.controller.Action;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
import org.lwjgl.glfw.GLFW;
import stat.NoSuchStatException;
import stat.NumericStat;
import templeoftheelements.creature.Creature;
import templeoftheelements.controller.Controller;
import templeoftheelements.item.Item;
import static templeoftheelements.TempleOfTheElements.PIXELS_PER_METER;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.collision.Position;
import templeoftheelements.collision.Room;
import templeoftheelements.display.CharacterScreen;
import templeoftheelements.display.HUD;
import templeoftheelements.display.Screen;
import templeoftheelements.display.SelectIcon;
import static templeoftheelements.TempleOfTheElements.rotate;

/**
 *
 * @author angle
 */


public class Player implements Controller {

    private Creature creature;
    private Vec2 accel;
    private final Screen characterScreen;
    public HUD hud;
    private final Inventory inventory;
    public CharacterWheel characterWheel;
    public SelectIcon leftClick, rightClick;
    private int experience;
    private int level;
    public int characterPoints;
    private final HashSet<Action> actions;
    
    public Player(Creature b) {
        creature = b;
        accel = new Vec2();
        inventory = new Inventory(this);
        characterWheel = new CharacterWheel(this);
        characterScreen = new CharacterScreen(characterWheel);
        actions = new HashSet<>();
        experience = 0;
        level = 1;
        creature.setController(this);
    }
    
    @Override
    public void init() {
        hud = new HUD(this);
        leftClick = new SelectIcon(new Position(200, 50), 100, 100);
        hud.addIcon(leftClick);
        refactorHUD();
        characterPoints += 3;
    }
    
    public HashSet<Action> getActions() {
        return actions;
    }
    
    @Override
    public void addAction(Action a ) {
        actions.add(a);
    }
    
    @Override
    public void refactorActions() {
        actions.clear();
        creature.refactorActions();
        refactorHUD();
    }
    
    @Override
    public Creature getCreature() {
        return creature;
    }
    
    public void refactorSelectIcon(SelectIcon i) {
        i.clearOptions();
        for (Action a : getActions()) {
            i.addOption(a.getSprite(), a);
        }
    }
    
    public void refactorHUD() {
        refactorSelectIcon(leftClick);
    }
    
    public void mouseEvent(int button, int action, int mods) {
        if (hud.isClicked(game.mouse.getX(), game.mouse.getY())) {
            hud.mouseEvent(button, action, mods);
            return;
        }
        
        if (button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS) {
            Vec2 click = new Vec2(game.mouse.getX(), game.mouse.getY());
            click.x -= game.getResolutionWidth()/2;
            click.y -= game.getResolutionHeight()/7;
            click.x /= PIXELS_PER_METER;
            click.y /= PIXELS_PER_METER;
            click.x += creature.getPosition().x;
            click.y += creature.getPosition().y;
            click = rotate(creature.getPosition(), click, -creature.getDirection());
            Clickable c = game.getClickable(click.x, click.y);
            if (c != null && !(c instanceof Creature)) {
                c.mouseEvent(button, action, mods);
            }
            else leftClick.performAction(creature, button, action, mods, new Position(click));
        }
    }
    
    public void keyEvent(int key, int action, int mods) {
        if (action != GLFW.GLFW_PRESS) return;
        switch (key) {
            case GLFW.GLFW_KEY_I:
                game.screen = inventory;
                break;
            case GLFW.GLFW_KEY_C:
                game.screen = characterScreen;
                break;
            case GLFW.GLFW_KEY_ESCAPE:
                game.screen = game.menu;
                break;
            case GLFW.GLFW_KEY_R:
                game.start();
                break;
            case GLFW.GLFW_KEY_Z:
                debug();
                break;
                
        }
    }

    @Override
    public void step(float dt) { //this function is kinda bad.
        
        accel.x = 0;
        accel.y = 0;
        
        //are we turning? if so, turn. Mouse.getX() > (game.width/2 - 40) + Math.floor(Mouse.getY())
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_E)) {
            creature.modifyDirection(1);
        }
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_Q)) {
            creature.modifyDirection(-1);
        }
        //set the initial direction. Why are we subtracting 90? I dunno, but it works.
        float dir = creature.getDirection() - 90;
        
        //figure out what direction the player WANTS to go.
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_W)) accel.y = 1;
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_A)) accel.x = -1;
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_S)) accel.y = -1;
        if (game.keyboard.isKeyDown(GLFW.GLFW_KEY_D)) accel.x = 1;
        
        
        //Figue out the actual direction we're sending them.
        if (accel.x == 0 && accel.y == 0) return;
        else if (accel.x == 1 && accel.y == 0);
        else if (accel.x == 1 && accel.y == 1) dir -= 45;
        else if (accel.x == 0 && accel.y == 1) dir -= 90;
        else if (accel.x == -1 && accel.y == 1) dir -= 135;
        else if (accel.x == -1 && accel.y == 0) dir -= 180;
        else if (accel.x == -1 && accel.y == -1) dir -= 225;
        else if (accel.x == 0 && accel.y == -1) dir -= 270;
        else if (accel.x == 1 && accel.y == -1) dir -= 315;
        
        //make sure our direction makes sense.
        while (dir >= 360) {
            dir -= 360;
        }
        while (dir < 0) {
            dir += 360;
        }
        //figure out exactly what vector we need to send them on. 
        //Why are we making it negative? I dunno that either, but it works.
        accel.x = -(float) Math.sin(Math.toRadians(dir));
        accel.y = -(float) Math.cos(Math.toRadians(dir));
        
        //the creature will grab the accel vector when it does it's movement code.
        
    }

    @Override
    public Vec2 getAccel() {
        return accel;
    }

    @Override
    public Controller clone(Creature creature) {
        throw new UnsupportedOperationException("Tried to clone Player!.");
    }
    
    public void pickUpItem(Item i) {
        if (i.getPosition().sub(creature.getPosition()).length() < 30 && inventory.addItem(i)) {
            game.removeSprite(i);
            game.removeClickable(i);
            game.room.remove(i);
        }
    }

    public void gainExperience(int experience) {
        this.experience += experience;
        while (this.experience >= level * level * 100) {
            level++;
            characterPoints++;
        }
    }
    
    public void roomCleared(Room room) {
        try {
            creature.stats.getStat("Stamina").set(creature.stats.getScore("Max Stamina"));
            ((NumericStat) creature.stats.getStat("Mana")).modifyBase(creature.stats.getScore("Mana Regen"));
            if (creature.stats.getScore("Mana")> creature.stats.getScore("Max Mana")) creature.stats.getStat("Mana").set(creature.stats.getScore("Max Mana"));
            ((NumericStat) creature.stats.getStat("HP")).modifyBase(creature.stats.getScore("HP Regen"));
            if (creature.stats.getScore("HP")> creature.stats.getScore("Max HP")) creature.stats.getStat("HP").set(creature.stats.getScore("Max HP"));
            gainExperience(room.experience);
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isEnemy() {
        return false;
    }

    @Override
    public boolean isDead() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void debug() {
        
    }
    
}
