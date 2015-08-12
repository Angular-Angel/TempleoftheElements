 
package templeoftheelements.collision;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

/**
 *
 * @author angle
 */


public class CollisionManager implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        Object obj1 = (Collidable) contact.m_fixtureA.getUserData();
        Object obj2 = (Collidable) contact.m_fixtureB.getUserData();
        if (obj1 instanceof Collidable) ((Collidable)obj1).collisionLogic(obj2);
        if (obj2 instanceof Collidable) ((Collidable)obj2).collisionLogic(obj1);
    }

    @Override
    public void endContact(Contact contact) {
        
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
        
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse ci) {
        
    }
    
}
