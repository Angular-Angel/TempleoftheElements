
package templeoftheelements.display;

import com.samrj.devil.graphics.GLTexture2D;
import com.samrj.devil.graphics.GLTextureRectangle;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import org.lwjgl.opengl.GL31;

/**
 *
 * @author angle
 */


public class Texture {
    private GLTexture2D texture;
    
    private float x, y, texWidth, texHeight;
    
     public Texture(GLTexture2D texture) {
        this(texture, 0, 0, texture.width, texture.height);
    }
    
    public Texture(GLTexture2D texture, float x, float y, float texWidth, float texHeight) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.texHeight = texHeight;
        this.texWidth = texWidth;
    }
    
    public void draw(float width, float height) {
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        glTexParameteri(GL11.GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
//        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        
        // store the current model matrix
	GL11.glPushMatrix();
		
	// bind to the appropriate texture for this sprite
	texture.glBind();
    
	// translate to the right location and prepare to draw	
        GL11.glScalef(2, 2, 1);
        GL11.glTranslatef(-width/4, -height/4, 0);
    	GL11.glColor3f(1, 1, 1);
	
	// draw a quad textured to match the sprite
    	GL11.glBegin(GL11.GL_QUADS);
	{
	    GL11.glTexCoord2f(x/2, y/2);
	    GL11.glVertex2f(0, 0);
	    GL11.glTexCoord2f(x/2, (y + texHeight)/2);
	    GL11.glVertex2f(0, height/2);
	    GL11.glTexCoord2f((x + texWidth)/2, (y + texHeight)/2);
	    GL11.glVertex2f(width/2, height/2);
	    GL11.glTexCoord2f((x + texWidth)/2, y/2);
	    GL11.glVertex2f(width/2, 0);
	}
	GL11.glEnd();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        
        GL11.glDisable(GL11.GL_BLEND);
		
	// restore the model view matrix to prevent contamination
	GL11.glPopMatrix();
    }
}
