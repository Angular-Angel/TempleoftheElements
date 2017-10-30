
package templeoftheelements.display;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import templeoftheelements.collision.Position;
import templeoftheelements.collision.Positionable;
import templeoftheelements.player.Clickable;

/**
 *
 * @author angle
 */


public class Icon implements Renderable, Clickable, Positionable {

    protected float width, height;

    protected Position position;

    private Renderable sprite;

    public Icon(Position pos, Renderable sprite, float width, float height) {
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        this.position = pos;
    }
    
    public Renderable getSprite() {
        return sprite;
    }

    /**
     * Draw the Icon.
     */
    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glTranslated(getPosition().x+width/2, getPosition().y+height/2, 0);
        sprite.draw();
        GL11.glPopMatrix();
    }

    @Override
    public boolean isClicked(float x, float y) {
        return (x > position.x && x < position.x + width 
             && y > position.y && y < position.y + height);
    }

    @Override
    public void mouseEvent(int button, int action, int mods) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS) {
        }
    }

    @Override
    public Position getPosition() {
        return new Position(position);
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public float getDrawWidth() {
        return sprite.getDrawWidth();
    }

    @Override
    public float getDrawHeight() {
        return sprite.getDrawHeight();
    }
}