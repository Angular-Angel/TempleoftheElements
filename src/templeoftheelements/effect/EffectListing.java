/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.effect;

import java.util.ArrayList;

/**
 *
 * @author angle
 */
public class EffectListing {
    public String name;
    public ArrayList<String> variables;
    public String effect;
    
    public EffectListing(String name) {
        this.name = name;
        variables = new ArrayList<>();
    }
    
}
