
package templeoftheelements.display;

import com.samrj.devil.graphics.GLTextureRectangle;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import org.lwjgl.opengl.GL31;



public class Sprite implements Renderable{

    private GLTextureRectangle texture;
    
    private float x, y, texWidth, texHeight, width, height;
    
    public Sprite(GLTextureRectangle texture, float width, float height) {
        this(texture, 0, 0, texture.width, texture.height, width, height);
    }
    
    public Sprite(GLTextureRectangle texture, float x, float y, float texWidth, float texHeight, float width, float height) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.texHeight = texHeight;
        this.texWidth = texWidth;
        this.height = height;
        this.width = width;
    }
    
    @Override
    public void draw() {
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        glTexParameteri(GL31.GL_TEXTURE_RECTANGLE, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
//        
        GL11.glEnable(GL31.GL_TEXTURE_RECTANGLE);
        
        // store the current model matrix
	GL11.glPushMatrix();
		
	// bind to the appropriate texture for this sprite
	texture.glBind();
    
	// translate to the right location and prepare to draw	
        GL11.glTranslatef(-width/2, -height/2, 0);
    	GL11.glColor3f(1, 1, 1);
	
	// draw a quad textured to match the sprite
    	GL11.glBegin(GL11.GL_QUADS);
	{
	    GL11.glTexCoord2f(x, y);
	    GL11.glVertex2f(0, 0);
	    GL11.glTexCoord2f(x, y + texHeight);
	    GL11.glVertex2f(0, height);
	    GL11.glTexCoord2f(x + texWidth, y + texHeight);
	    GL11.glVertex2f(width, height);
	    GL11.glTexCoord2f(x + texWidth, y);
	    GL11.glVertex2f(width, 0);
	}
	GL11.glEnd();
        GL11.glDisable(GL31.GL_TEXTURE_RECTANGLE);
        
        GL11.glDisable(GL11.GL_BLEND);
		
	// restore the model view matrix to prevent contamination
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
