import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import stat.*;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;

/**
 *
 * @author angle
 */
class MagicalStyleGenerator implements CharacterTreeGenerator {
    
    Random random = new Random();
    
    public CharacterTreeDef genCharacterTreeDef() {
        
        ArrayList<Element> elements = new ArrayList<>();
        
        int numElements = game.registry.elements.size();
        for (int i = 0; i < 2; i++) {
            Element e = game.registry.elementList.get(random.nextInt(numElements));
            if (!elements.contains(e))
                elements.add(e);
            else i--;
        }
        
        String name = "Path of " + elements.get(0).name + " and " + elements.get(1).name;
        
        ArrayList<String> attributePool = new ArrayList<>();
        
        attributePool.add("Intelligence");
        attributePool.add("Spirit");
        attributePool.add("Perception");
        
        for (Element e : elements) {
            attributePool.addAll(e.attributes);
        }
        
        CharacterTreeDef ret = new CharacterTreeDef(name);
        
        ret.elements = elements;
        
        ArrayList<CharacterTreeDef.Focus> focusPool = new ArrayList<>();
        
        focusPool.addAll(CharacterTreeDef.Focus.values());
        
        for (Element e : elements) {
            focusPool.addAll(e.focuses);
        }
        
        ret.primaryFocus = focusPool.get(random.nextInt(focusPool.size()));
        
        ret.secondaryFocuses.add(focusPool.get(random.nextInt(focusPool.size())));
        
        String attribute = attributePool.get(random.nextInt(attributePool.size()));
        while(attributePool.remove(attribute));
        ret.primaryAttributes.add(attribute);
        attribute = attributePool.get(random.nextInt(attributePool.size()));
        while(attributePool.remove(attribute));
        ret.primaryAttributes.add(attribute);
        
        ret.details.add(CharacterTreeDef.Detail.values()[random.nextInt(CharacterTreeDef.Detail.values().length)]);
        
        CharacterTreeDef.Detail detail = CharacterTreeDef.Detail.values()[random.nextInt(CharacterTreeDef.Detail.values().length)];
        
        while (ret.details.contains(detail)) detail = CharacterTreeDef.Detail.values()[random.nextInt(CharacterTreeDef.Detail.values().length)];
        
        ret.details.add(detail);
        
        return ret;
    }
    
}