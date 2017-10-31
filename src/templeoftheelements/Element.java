
package templeoftheelements;

import java.util.ArrayList;
import java.util.HashMap;
import stat.StatDescriptor;
import templeoftheelements.player.characterwheel.CharacterTreeDef;

/**
 *
 * @author angle
 */


public class Element {
    public final String name;
    
    public ArrayList<CharacterTreeDef.Focus> focuses;
    public ArrayList<Spell.Detail> targetDetails;
    public ArrayList<Spell.Detail> effectDetails;
    public ArrayList<Spell.Detail> costDetails;
    public ArrayList<Spell.Detail> scalingDetails;
    public ArrayList<String> relations;
    public ArrayList<StatDescriptor> primaryAttributes;
    public ArrayList<StatDescriptor> secondaryAttributes;
    public ArrayList<StatDescriptor> debuffAttributes;
    public HashMap<String, Float> resistances;
    
    public Element(String name) {
        this.name = name;
        primaryAttributes = new ArrayList<>(); 
        secondaryAttributes = new ArrayList<>(); 
        debuffAttributes = new ArrayList<>();
        resistances = new HashMap<>();
        relations = new ArrayList<>();
        targetDetails = new ArrayList<>();
        effectDetails = new ArrayList<>();
        costDetails = new ArrayList<>();
        scalingDetails = new ArrayList<>();
        focuses = new ArrayList<>();
    }
}
