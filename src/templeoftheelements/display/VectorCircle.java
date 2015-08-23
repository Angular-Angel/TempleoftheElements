
package templeoftheelements.display;

import com.samrj.devil.graphics.GraphicsUtil;
import com.samrj.devil.math.Vec2;
import com.samrj.devil.math.Vec4;



public class VectorCircle implements Renderable {
    private Vec4 color;
    private float size;

    public VectorCircle(float size) {
        color = new Vec4(0, 0, 1, 1);
        this.size = size;
    }
    
    @Override
    public void draw() {
        GraphicsUtil.glColor(color);
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
