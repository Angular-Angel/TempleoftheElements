
package templeoftheelements.display;

import com.samrj.devil.gl.Texture2D;
import org.lwjgl.opengl.GL11;

public class Sprite implements Renderable{

    private Texture2D texture;
    
    private float x, y, texWidth, texHeight, width, height;
    
    public Sprite(Texture2D texture, float width, float height) {
        this(texture, 0, 0, texture.getWidth(), texture.getHeight(), width, height);
    }
    
    public Sprite(Texture2D texture, float x, float y, float texWidth, float texHeight, float width, float height) {
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
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        
        // store the current model matrix
	GL11.glPushMatrix();
		
	// bind to the appropriate texture for this sprite
	texture.bind();
        texture.parami(GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    
	// translate to the right location and prepare to draw	
        GL11.glTranslatef(-width/2, -height/2, 0);
    	GL11.glColor3f(1, 1, 1);
	
	// draw a quad textured to match the sprite
    	GL11.glBegin(GL11.GL_QUADS);
	{
            float tcx0 = x/texture.getWidth();
            float tcx1 = (x + texWidth)/texture.getWidth();
            float tcy0 = y/texture.getHeight();
            float tcy1 = (y + texHeight)/texture.getHeight();
            
	    GL11.glTexCoord2f(tcx0, tcy0);
	    GL11.glVertex2f(0, 0);
	    GL11.glTexCoord2f(tcx0, tcy1);
	    GL11.glVertex2f(0, height);
	    GL11.glTexCoord2f(tcx1, tcy1);
	    GL11.glVertex2f(width, height);
	    GL11.glTexCoord2f(tcx1, tcy0);
	    GL11.glVertex2f(width, 0);
	}
	GL11.glEnd();
        
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        
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
