
package templeoftheelements.display;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author angle
 */


public class QuadRender implements Renderable{

    @Override
    public void draw() {
        // set the color of the quad (R,G,B,A)
        GL11.glColor3f(0.5f,0.5f,0.5f);

        // draw quad
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(100,100);
        GL11.glVertex2f(100+200,100);
        GL11.glVertex2f(100+200,100+200);
        GL11.glVertex2f(100,100+200);
        GL11.glEnd();
    }

    @Override
    public float getDrawWidth() {
        return 300;
    }

    @Override
    public float getDrawHeight() {
        return 300;
    }
    
}
