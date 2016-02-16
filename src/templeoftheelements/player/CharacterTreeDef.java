
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
    public static enum Detail {
        MAGIC, MARTIAL, LONG_COOLDOWNS, COSTS_ATTRIBUTES, MATERIAL_COMPONENTS, SPEED_BASED, 
        CHANNELING_TIMES, TOUGHNESS_BASED, LUCK_BASED, RITUALS, STAMINA_BASED, 
        COSTS_HP
    }
    
    public String name;
    
    public Focus primaryFocus;
    public ArrayList<Focus> secondaryFocuses;
    public ArrayList<Detail> details;
    public ArrayList<StatDescriptor> primaryAttributes;
    public ArrayList<StatDescriptor> secondaryAttributes;
    public ArrayList<Element> elements;
    public ProceduralGenerator<ClusterDefinition> clusterGenerator;
    public ProceduralGenerator<CharacterNode> nodeGenerator;
    public ArrayList<Ability> abilities;
    public ArrayList<NodeDefinition> capstones;
    
    public CharacterTreeDef(String name) {
        this.name = name;
        primaryAttributes = new ArrayList<>();
        secondaryAttributes = new ArrayList<>();
        secondaryFocuses = new ArrayList<>();
        details = new ArrayList<>();
        elements = new ArrayList<>();
        abilities = new ArrayList<>();
    }
    
    public static class AbilityDefinition {
        public ArrayList<Spell.Detail> details;
        public Ability ability;
        
        public AbilityDefinition(Ability ability) {
            this.ability = ability;
            details = new ArrayList<>();
        }
        
    }
    
    public static enum Requirement {
        SINGLE, OR, AND;
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
        public int layer;
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
