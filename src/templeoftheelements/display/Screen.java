
package templeoftheelements.display;

import com.samrj.devil.graphics.GraphicsUtil;
import com.samrj.devil.math.Vec4;
import org.lwjgl.opengl.GL11;
import static templeoftheelements.TempleOfTheElements.game;



public abstract class Screen {
    
    public final void stop()
    {
        game.screen = null;
    }
    
    public abstract void render();
    
    public abstract void step();
    
    public abstract void mouseMoved(float x, float y, float dx, float dy);
    
    public abstract void mouseEvent(int button, int action, int mods);
    
    public abstract void keyEvent(int key, int action, int mods);
    
    public class SubScreen implements Renderable{
        public float x, y, width, height;
        public Vec4 background, border;
        
        public SubScreen(float x, float y, float width, float height) {
            this(x, y, width, height, new Vec4(0, 0, 0, 1), new Vec4(1, 1, 1, 1));
        }
        
        public SubScreen(float x, float y, float width, float height, Vec4 background, Vec4 border) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.background = background;
            this.border = border;
        }
        
        public void glTranslate() {
            GL11.glTranslatef(x, y, 0);
        }

        @Override
        public void draw() {
            GL11.glPushMatrix();
            glTranslate();
            GraphicsUtil.glColor(background);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(0, 0);
            GL11.glVertex2f(0 + width, 0);
            GL11.glVertex2f(0 + width, 0 + height);
            GL11.glVertex2f(0, 0 + height);
            GL11.glVertex2f(0, 0);
            GL11.glEnd();

            GraphicsUtil.glColor(border);
            GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex2f(0, 0);
            GL11.glVertex2f(0 + width, 0);
            GL11.glVertex2f(0 + width, 0 + height);
            GL11.glVertex2f(0, 0 + height);
            GL11.glVertex2f(0, 0);
            GL11.glEnd();
            
            GL11.glPopMatrix();
        }

        @Override
        public float getDrawWidth() {
            return width;
        }

        @Override
        public float getDrawHeight() {
            return height;
        }
    }
    
}
