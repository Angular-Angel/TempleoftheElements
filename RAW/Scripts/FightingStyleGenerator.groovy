import templeoftheelements.spells.Spell;
import templeoftheelements.Element;
import stat.StatDescriptor;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import generation.ProceduralGenerator;
import templeoftheelements.player.CharacterTree;
import templeoftheelements.player.CharacterWheel;
import templeoftheelements.creature.Ability;

/**
 *
 * @author angle
 */
class FightingStyleGenerator implements ProceduralGenerator<CharacterTree> {
    
    //our random number generator;
    Random random = new Random();
    
    //the main method. This generates the description of the character tree that 
    //the generator uses to generate the substance.
    public CharacterTree generate(Object o) {
        CharacterWheel wheel = (CharacterWheel) o;
        
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
        attributePool.add(game.registry.statDescriptors.get("Strength"));
        attributePool.add(game.registry.statDescriptors.get("Vitality"));
        attributePool.add(game.registry.statDescriptors.get("Endurance"));
        attributePool.add(game.registry.statDescriptors.get("Dexterity"));
        attributePool.add(game.registry.statDescriptors.get("Agility"));
        
        //adds the attributes associated with each element.
        
        attributePool.addAll(element.primaryAttributes);
        
        //generates a new CharacterTreeDef to hold all this information.
        CharacterTree ret = new CharacterTree(name, wheel);
        
        ret.element = element;
        
        //now we need to pick out what kind of things this style focuses on doing.
        //First, we pick out all our options, weighting in favor of some things...
        ArrayList<CharacterTree.Focus> focusPool = new ArrayList<>();
        
        //first, we have a chance to get any focus there is.
        focusPool.addAll(CharacterTree.Focus.values());
        
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
        
        
//        Spell.Detail[] values = Arrays.copyOfRange(Spell.Detail.values(), Spell.Detail._TARGETING_.ordinal()+1, Spell.Detail._COMMON_.ordinal());
        
        Ability.Detail detail;
        
//        for (int i = 0; i < values.length/2; i++) {
//            detail = values[random.nextInt(values.length)];
//            ret.details.add(detail);
//        }

        
            ret.targetDetails.add(Ability.Detail.PROJECTILE);
            ret.targetDetails.add(Ability.Detail.ENEMY_TARGET);
            
            ret.effectDetails.add(Ability.Detail.DAMAGE);
            ret.effectDetails.add(Ability.Detail.DEBUFF);
            
//            ArrayList<Spell.Detail> arrayList = new ArrayList<Spell.Detail>();
//            arrayList.addAll(Arrays.asList(Spell.Detail.values()));
//            arrayList = arrayList.subList(arrayList.indexOf(Spell.Detail._COSTS_) +1, arrayList.indexOf(Spell.Detail._TARGETING_));
//        
//            ret.costDetails.addAll(arrayList);
            ret.costDetails.addAll(Ability.Detail.MANA_COST);
            ret.costDetails.addAll(Ability.Detail.CAST_TIME);
            ret.costDetails.addAll(Ability.Detail.COOLDOWN);
        
        
        //while (ret.details.contains(detail)) detail = Spell.Detail.values()[random.nextInt(Spell.Detail.values().length)];
        
        //ret.details.add(detail);
        
        ret.secondaryAttributes.add(game.registry.statDescriptors.get("Max Mana"));
        
        if (ret.scalingDetails.contains(Ability.Detail.SPEED_BASED)) 
            ret.secondaryAttributes.add(game.registry.statDescriptors.get("Max Speed"));
        
        if (ret.scalingDetails.contains(Ability.Detail.STAMINA_BASED) ||
            ret.scalingDetails.contains(Ability.Detail.STAMINA_COST)) 
            ret.secondaryAttributes.add(game.registry.statDescriptors.get("Max Stamina"));
        
        if (ret.scalingDetails.contains(Ability.Detail.HP_BASED) ||
            ret.scalingDetails.contains(Ability.Detail.HP_COST)) 
            ret.secondaryAttributes.add(game.registry.statDescriptors.get("Max HP"));
        
        if (ret.scalingDetails.contains(Ability.Detail.VITALITY_BASED))
            ret.secondaryAttributes.add(game.registry.statDescriptors.get("Vitality"));
        
        
        attributePool.clear();
        attributePool.addAll(element.secondaryAttributes);
            
        while (ret.secondaryAttributes.size() < 3) {
            attribute = attributePool.get(random.nextInt(attributePool.size()));
            while(attributePool.remove(attribute));
            ret.secondaryAttributes.add(attribute);
        }
        
        return ret;
    }
    
    //an unused method from the interface. Sloppy, I know.
    public CharacterTree generate() {
        throw new UnsupportedOperationException();
    }
    
    public CharacterTree modify(CharacterTree tree) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isApplicable(CharacterTree tree) {
        throw new UnsupportedOperationException();
    }
    
}