
package templeoftheelements.display;

import com.samrj.devil.graphics.Color4f;
import com.samrj.devil.graphics.GraphicsUtil;
import com.samrj.devil.math.Vec2;



public class VectorCircle implements Renderable {
    private Color4f color;
    private float size;

    public VectorCircle(float size) {
        color = new Color4f(0, 0, 255);
        this.size = size;
    }
    
    @Override
    public void draw() {
        color.glColor();
        Vec2 center = new Vec2();
        GraphicsUtil.drawCircle(center, size, 32);
    }

    @Override
    public float getDrawWidth() {
        return size*2;
    }

    @Override
    public float getDrawHeight() {
        return size*2;
    }
}
