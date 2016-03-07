/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.collision;

/**
 *
 * @author angle
 */
public interface Damageable extends Collidable {
    
    public float takeDamage(float damage, String type);
}
