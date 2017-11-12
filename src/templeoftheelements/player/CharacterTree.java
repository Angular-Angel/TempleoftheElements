
package templeoftheelements.player;

import generation.ProceduralGenerator;
import generation.GenerationProcedure;
import java.util.ArrayList;
import stat.StatContainer;
import stat.StatDescriptor;
import templeoftheelements.Element;
import templeoftheelements.spells.Spell;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.Creature;

/**
 *
 * @author angle
 */


public class CharacterTree {
    
    //This defines the prupose of the character tree.
    public static enum Focus {
        OFFENSE, DEFENSE, UTILITY, MINIONS, PROJECTILES, BUFFS, DEBUFFS, HEALING,
        TERRAIN_ALTERATION, TELEPATHY, DIVINATION, ITEM_MANIPULATION, MOVEMENT
    }
    
    //these define any extra details common on abilities of this style.
    
    
    public String name;
    
    public Focus primaryFocus;

    /**
     *
     */
    public Element element;
    private final CharacterWheel wheel;
    public ArrayList<Focus> secondaryFocuses;
    public ArrayList<Spell.Detail> targetDetails;
    public ArrayList<Spell.Detail> effectDetails;
    public ArrayList<Spell.Detail> costDetails;
    public ArrayList<Spell.Detail> scalingDetails;
    public ArrayList<StatDescriptor> primaryAttributes;
    public ArrayList<StatDescriptor> secondaryAttributes;
    public ArrayList<CharacterNode> capstones;
    public ArrayList<CharacterNode> nodes;
    public ArrayList<Ability> abilities;
    public int numLayers, spammables, situationals;
        
    
    public CharacterTree(String name, CharacterWheel wheel) {
        this.name = name;
        this.wheel = wheel;
        primaryAttributes = new ArrayList<>();
        secondaryAttributes = new ArrayList<>();
        secondaryFocuses = new ArrayList<>();
        targetDetails = new ArrayList<>();
        effectDetails = new ArrayList<>();
        costDetails = new ArrayList<>();
        scalingDetails = new ArrayList<>();
    }
    
    public Creature getCreature() {
        return wheel.getCreature();
    }
    
}
