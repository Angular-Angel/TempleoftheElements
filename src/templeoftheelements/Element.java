
package templeoftheelements;

import java.util.ArrayList;
import java.util.HashMap;
import templeoftheelements.player.CharacterTreeDef;

/**
 *
 * @author angle
 */


public class Element {
    public final String name;
    
    public ArrayList<CharacterTreeDef.Focus> focuses;
    public ArrayList<Spell.Detail> details;
    public ArrayList<String> relations;
    public ArrayList<String> attributes;
    public HashMap<String, Float> resistances;
    
    public Element(String name) {
        this.name = name;
        attributes = new ArrayList<>(); 
        resistances = new HashMap<>();
        relations = new ArrayList<>();
        details = new ArrayList<>();
        focuses = new ArrayList<>();
    }
}
