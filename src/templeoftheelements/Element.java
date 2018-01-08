
package templeoftheelements;

import java.util.ArrayList;
import java.util.HashMap;
import stat.StatDescriptor;
import templeoftheelements.creature.Ability;
import templeoftheelements.player.CharacterTree;

/**
 *
 * @author angle
 */


public class Element {
    public final String name;
    
    public ArrayList<CharacterTree.Focus> focuses;
    public ArrayList<Ability.Detail> targetDetails;
    public ArrayList<Ability.Detail> effectDetails;
    public ArrayList<Ability.Detail> costDetails;
    public ArrayList<Ability.Detail> scalingDetails;
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
