
package templeoftheelements.player;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author angle
 */


public class OrRequirement implements Requirement{
    private ArrayList<Requirement> requirements;

    public OrRequirement(Requirement... reqs) {
        requirements = new ArrayList<>();
        for (Requirement r : reqs) {
            requirements.add(r);
        }
    }
    
    @Override
    public boolean isMet() {
        for (Requirement req : requirements) {
            if (req.isMet()) return true;
        }
        return false;
    }

    @Override
    public void draw(Requirement req) {
        CharacterNode source;
        if (!(req instanceof CharacterNode)) return;
        else source = (CharacterNode) req;
        for (Requirement requirement : requirements) {
            if (!(requirement instanceof CharacterNode)) return;
            
            if (requirement instanceof CharacterNode) {
                CharacterNode target = (CharacterNode) requirement;
                if (source.isAcquired() && target.isMet()) {
                    GL11.glColor3f(0, 0, 255);
                } else if (target.isMet()) {
                    GL11.glColor3f(0, 255, 0);
                } else
                    GL11.glColor3f(255, 0, 0);

                GL11.glBegin(GL11.GL_LINE_LOOP);

                GL11.glVertex2f(source.position.x, source.position.y);
                GL11.glVertex2f(target.position.x, target.position.y);

                GL11.glEnd();
            }
        }
    }

    @Override
    public ArrayList<CharacterNode> getNodes() {
        ArrayList<CharacterNode> ret =  new ArrayList<>();
        for (Requirement r : requirements) {
            ret.addAll(r.getNodes());
        }
        return ret;
    }
    
}
