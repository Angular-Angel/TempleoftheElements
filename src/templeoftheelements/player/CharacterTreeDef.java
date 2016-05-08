
package templeoftheelements.player;

import generation.ProceduralGenerator;
import java.util.ArrayList;
import stat.StatContainer;
import stat.StatDescriptor;
import templeoftheelements.Element;
import templeoftheelements.Spell;

/**
 *
 * @author angle
 */


public class CharacterTreeDef {
    
    //This defines the prupose of the character tree.
    public static enum Focus {
        OFFENSE, DEFENSE, UTILITY, MINIONS, PROJECTILES, BUFFS, DEBUFFS, HEALING,
        TERRAIN_ALTERATION, TELEPATHY, DIVINATION, ITEM_MANIPULATION, MOVEMENT
    }
    
    //these define any extra details common on abilities of this style.
    
    
    public String name;
    
    public Focus primaryFocus;
    public ArrayList<Focus> secondaryFocuses;
    public ArrayList<Spell.Detail> targetDetails;
    public ArrayList<Spell.Detail> effectDetails;
    public ArrayList<Spell.Detail> costDetails;
    public ArrayList<Spell.Detail> scalingDetails;
    public ArrayList<StatDescriptor> primaryAttributes;
    public ArrayList<StatDescriptor> secondaryAttributes;
    public Element element;
    public ProceduralGenerator<ClusterDefinition> clusterGenerator;
    public ProceduralGenerator<CharacterNode> nodeGenerator;
    public ArrayList<NodeDefinition> capstones;
    
    public CharacterTreeDef(String name) {
        this.name = name;
        primaryAttributes = new ArrayList<>();
        secondaryAttributes = new ArrayList<>();
        secondaryFocuses = new ArrayList<>();
        targetDetails = new ArrayList<>();
        effectDetails = new ArrayList<>();
        costDetails = new ArrayList<>();
        scalingDetails = new ArrayList<>();
    }
    
    public static class AbilityDefinition extends StatContainer {
        public Spell.Detail usage;
        public CharacterWheel.CharacterTree tree;
        public ArrayList<Spell.Detail> details;
        public Ability ability;
        
        public AbilityDefinition() {
            details = new ArrayList<>();
        }
        
    }
    
    public static class Requirement {
        public int number;
        public boolean and;
        
        public Requirement(int number) {
            this(number, false);
        }
        
        public Requirement(int number, boolean and) {
            this.number = number;
            this.and = and;
        }
    }
    
    public static enum Position {
        RADIAL, CLOCKWISE20, COUNTERCLOCKWISE20;
    }
    
    public class NodeDefinition {
        public String description;
        public boolean free;
        public AbilityDefinition ability;
        public ArrayList<StatDescriptor> stats;
        public CharacterWheel.CharacterTree tree;
        public int layer, cluster;
        public Requirement requirement;
        public Position position;
        
        public NodeDefinition() {
            stats = new ArrayList<>();
        }
    }
    
    public NodeDefinition newNode(CharacterWheel.CharacterTree tree) {
        NodeDefinition ret = new NodeDefinition();
        ret.tree = tree;
        return ret;
    }

    public static class ClusterDefinition {
        public NodeDefinition capstone, entry;
        public ArrayList<NodeDefinition> bulk;
        public int layer;
        public int length;
        
        public ClusterDefinition() {
            bulk = new ArrayList<>();
        }
    }
    
}
