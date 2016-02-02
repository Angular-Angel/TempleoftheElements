
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
    
    public static class NodeDefinition extends StatContainer {
        public String description;
        public boolean free;
        public Ability ability;
    }

    public static class ClusterDefinition {
        public NodeDefinition capstone;
        public ArrayList<NodeDefinition> bulk;
        public int length;
        
        public ClusterDefinition(NodeDefinition cap, int length) {
            capstone = cap;
            this.length = length;
            bulk = new ArrayList<>();
        }
    }
    
}
