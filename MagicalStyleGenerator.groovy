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
        
        CharacterTreeDef.Detail detail = CharacterTreeDef.Detail.values()[random.nextInt(CharacterTreeDef.Detail.values().length)];
        
        ret.details.add(detail);
        
        detail = CharacterTreeDef.Detail.values()[random.nextInt(CharacterTreeDef.Detail.values().length)];
        
        
        while (ret.details.contains(detail)) detail = CharacterTreeDef.Detail.values()[random.nextInt(CharacterTreeDef.Detail.values().length)];
        
        ret.details.add(detail);
        
        
        if (ret.details.contains(CharacterTreeDef.Detail.SPEED_BASED)) 
        ret.secondaryAttributes.add("Dexterity");
        
        if (ret.details.contains(CharacterTreeDef.Detail.TOUGHNESS_BASED) ||
            ret.details.contains(CharacterTreeDef.Detail.STAMINA_BASED)) 
        ret.secondaryAttributes.add("Constitution");
        
        if (ret.details.contains(CharacterTreeDef.Detail.COSTS_HP)) 
        ret.secondaryAttributes.add("Max HP");
        
        return ret;
    }
    
    public CharacterNode generateNode(CharacterWheel.CharacterTree tree) {
        
        double num = (tree.curLayerNodes.size() / (double) tree.layerSize) * tree.prevLayerNodes.size();
        Requirement req;
        if (tree.prevLayerNodes.size() == 0) {
            req = new AndRequirement();
        } else if (num < tree.prevLayerNodes.size() - 1)
            req = new OrRequirement(tree.prevLayerNodes.get((int) num), tree.prevLayerNodes.get(((int) num) + 1));
        else req = tree.prevLayerNodes.get((int) num);
        
        CharacterNode node = new CharacterNode(req, tree);
        
        String stat;
        
        if (tree.def.secondaryAttributes.size() > 0) {
        
            if (random.nextInt(4) < 3)
                stat = tree.def.primaryAttributes.get(random.nextInt(tree.def.primaryAttributes.size()));
            else stat = tree.def.secondaryAttributes.get(random.nextInt(tree.def.secondaryAttributes.size()));
        
        }
        else stat = tree.def.primaryAttributes.get(random.nextInt(tree.def.primaryAttributes.size()));
        
        node.addStat(stat, new NumericStat(1));
        
        return node;
    }
    
}