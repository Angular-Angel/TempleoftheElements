import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import stat.*;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import generation.ProceduralGenerator;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;
import templeoftheelements.player.CharacterTreeDef.NodeDefinition;
import templeoftheelements.player.CharacterTreeDef.ClusterDefinition;

/**
 *
 * @author angle
 */
class MagicalStyleGenerator implements ProceduralGenerator<CharacterTreeDef> {
    
    //our random number generator;
    Random random = new Random();
    
    //an unused method from the interface. Sloppy, I know.
    public CharacterTreeDef generate(Object o) {
//        CharacterWheel wheel = (CharacterWheel) o;
        throw new UnsupportedOperationException();
    }
    
    //the main method. This generates the description of the character tree that 
    //the generator uses to generate the substance.
    public CharacterTreeDef generate() {
        
        //the elements this style uses for it's magic. 
        Element element;
        
        //this block picks out two elements for the style to use.
        int numElements = game.registry.elements.size();
        element = game.registry.elementList.get(random.nextInt(numElements));
        
        //assigns the name of the style.
        String name = "Path of " + element.name;
        
        //a list of possible attributes for the style to be based off of.
        ArrayList<StatDescriptor> attributePool = new ArrayList<>();
        
        //the three basic mental attributes.
        attributePool.add(game.registry.statDescriptors.get("Intelligence"));
        attributePool.add(game.registry.statDescriptors.get("Spirit"));
        attributePool.add(game.registry.statDescriptors.get("Perception"));
        
        //adds the attributes associated with each element.
        
        attributePool.addAll(element.primaryAttributes);
        
        //generates a new CharacterTreeDef to hold all this information.
        CharacterTreeDef ret = new CharacterTreeDef(name);
        
        ret.element = element;
        
        //now we need to pick out what kind of things this style focuses on doing.
        //First, we pick out all our options, weighting in favor of some things...
        ArrayList<CharacterTreeDef.Focus> focusPool = new ArrayList<>();
        
        //first, we have a chance to get any focus there is.
        focusPool.addAll(CharacterTreeDef.Focus.values());
        
        //Then theres an adfitional chance to get a focus associated with one of 
        //the elements used for this magical style. 
        focusPool.addAll(element.focuses);
        
        //first, pick out a primary focus
        ret.primaryFocus = focusPool.get(random.nextInt(focusPool.size()));
        
        //then pick out a secondary focus
        ret.secondaryFocuses.add(focusPool.get(random.nextInt(focusPool.size())));
        
        //pick out attributes
        StatDescriptor attribute = attributePool.get(random.nextInt(attributePool.size()));
        while(attributePool.remove(attribute));
        ret.primaryAttributes.add(attribute);
        attribute = attributePool.get(random.nextInt(attributePool.size()));
        while(attributePool.remove(attribute));
        ret.primaryAttributes.add(attribute);
        
        Spell.Detail detail = Spell.Detail.values()[random.nextInt(Spell.Detail.values().length)];
        
        ret.details.add(detail);
        
        detail = Spell.Detail.values()[random.nextInt(Spell.Detail.values().length)];
        
        
        while (ret.details.contains(detail)) detail = Spell.Detail.values()[random.nextInt(Spell.Detail.values().length)];
        
        ret.details.add(detail);
        
        ret.secondaryAttributes.add(game.registry.statDescriptors.get("Added Mana"));
        
        if (ret.details.contains(Spell.Detail.SPEED_BASED)) 
        ret.secondaryAttributes.add(game.registry.statDescriptors.get("Added Speed"));
        
        if (ret.details.contains(Spell.Detail.STAMINA_BASED)) 
        ret.secondaryAttributes.add(game.registry.statDescriptors.get("Added Stamina"));
        
        if (ret.details.contains(Spell.Detail.TOUGHNESS_BASED) ||
            ret.details.contains(Spell.Detail.HP_COST)) 
        ret.secondaryAttributes.add(game.registry.statDescriptors.get("Added HP"));
        
        attributePool.clear();
        attributePool.addAll(element.secondaryAttributes);
            
        while (ret.secondaryAttributes.size() < 3) {
            attribute = attributePool.get(random.nextInt(attributePool.size()));
            while(attributePool.remove(attribute));
            ret.secondaryAttributes.add(attribute);
        }
        
        ret.nodeGenerator = game.registry.nodeGenerator;
        ret.clusterGenerator = game.registry.clusterGenerator;
        
        return ret;
    }
    
    public CharacterTreeDef modify(CharacterTreeDef tree) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isApplicable(CharacterTreeDef tree) {
        throw new UnsupportedOperationException();
    }
    
}