
package templeoftheelements.player;

import java.util.ArrayList;
import templeoftheelements.Element;

/**
 *
 * @author angle
 */


public class CharacterTreeDef {
    
    public static enum Focus {
        OFFENSE, DEFENSE, UTILITY, MINIONS, PROJECTILES, BUFFS, DEBUFFS, HEALING,
        TERRAIN_ALTERATION, MIND_CONTROL, KNOWLEDGE, ITEM_MANIPULATION, MOVEMENT
    }
    
    public static enum Detail {
        LONG_COOLDOWNS, COSTS_ATTRIBUTES, MATERIAL_COMPONENTS, SPEED_BASED, 
        CHANNELING_TIMES, TOUGHNESS_BASED, LUCK_BASED, RITUALS, STAMINA_BASED, 
        COSTS_HP
    }
    
    public String name;
    
    public Focus primaryFocus;
    public ArrayList<Focus> secondaryFocuses;
    public ArrayList<Detail> details;
    public ArrayList<String> primaryAttributes;
    public ArrayList<String> secondaryAttributes;
    public ArrayList<Element> elements;
    
    public CharacterTreeDef(String name) {
        this.name = name;
        primaryAttributes = new ArrayList<>();
        secondaryAttributes = new ArrayList<>();
        secondaryFocuses = new ArrayList<>();
        details = new ArrayList<>();
        elements = new ArrayList<>();
    }
}
