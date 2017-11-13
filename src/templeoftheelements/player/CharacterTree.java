
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
    public int numLayers, abilities, spammables, situationals;
    public double number; //This determines which section of the character tree this skill tree gets.
        
    
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
        nodes = new ArrayList<>();
        abilities = 0;
        numLayers = 0;
        spammables = 0;
        situationals = 0;
    }
    
    public Creature getCreature() {
        return wheel.getCreature();
    }
    
    public void addNode(CharacterNode node) {
        nodes.add(node);
        wheel.addNode(node);
    }
    
}
