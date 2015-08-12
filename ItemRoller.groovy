
import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;
import templeoftheelements.item.*;
import stat.NumericStat;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;

/**
 *
 * @author angle
 */
class ItemRoller implements ItemGenerator {
    
    private ArrayList<ItemDefinition> itemDefs = new ArrayList<>();
    private ArrayList<Integer> itemLevelMarks = new ArrayList<>();
    private ArrayList<MagicItemDef> magicItemDefs = new ArrayList<>();
    private ArrayList<Integer> magicLevelMarks = new ArrayList<>();
    //levelmarks stores the indexes of the the various item levels.
    private Random random = new Random();
    
    public void init() {
        int level = 0;
        for (ItemDefinition i : game.registry.itemDefs.values()) {
            if (i.getLevel() > level) {
                itemLevelMarks.add(itemDefs.size());
                level = i.getLevel();
            }
            for (int j = 0; j < i.getRarity(); j++) itemDefs.add(i);
        }
        itemLevelMarks.add(itemDefs.size());
        level = 0;
        for (MagicItemDef i : game.registry.magicEffects) {
            if (i.getLevel() > level) {
                magicLevelMarks.add(magicItemDefs.size());
                level = i.getLevel();
            }
            for (int j = 0; j < i.getRarity(); j++) magicItemDefs.add(i);
        }
        magicLevelMarks.add(magicItemDefs.size());
    }
    
    public Item generate(int level, int variance) {
        
        if (itemLevelMarks.size() < level - variance) throw new IllegalArgumentException();
        
        
        int start = itemLevelMarks.get(level - variance -1);
        int count;
        if (itemLevelMarks.size() > level + variance) count = (itemLevelMarks.get(level + variance) - start);
        else count = itemLevelMarks.get(itemLevelMarks.size() -1);
        int ticket = random.nextInt(count);
        
        Item item = itemDefs.get(start + ticket).generate();
        
        if (item.getLevel() < level) {
            start = magicLevelMarks.get(item.getLevel() -1);
            if (magicLevelMarks.size() > level + variance) count = magicLevelMarks.get(level) - start;
            else count = magicLevelMarks.get(magicLevelMarks.size() -1);
            ticket = random.nextInt(count);
            magicItemDefs.get(start + ticket).apply(item);
        }
        
        return item;
    }
}