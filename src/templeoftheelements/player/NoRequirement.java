/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templeoftheelements.player;

import java.util.ArrayList;

/**
 *
 * @author angle
 */
public class NoRequirement implements Requirement {

    @Override
    public boolean isMet() {
        return true;
    }

    @Override
    public void draw(Requirement req) {
    }

    @Override
    public ArrayList<CharacterNode> getNodes() {
        return new ArrayList<>();
    }
    
}
