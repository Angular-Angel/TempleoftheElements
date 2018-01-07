import templeoftheelements.creature.CreatureGenerationProcedure;
import templeoftheelements.creature.CreatureDefinition;
import templeoftheelements.display.Sprite;
import templeoftheelements.display.VectorCircle;   // the groovy script importing.
import templeoftheelements.controller.AttackAction;   // the groovy script importing.
import templeoftheelements.item.AttackDefinition;
import templeoftheelements.item.ItemDrop;
import stat.NumericStat;
import java.util.Random;
import com.samrj.devil.gl.Texture2D;
import static templeoftheelements.TempleOfTheElements.game;

/**
 *
 * @author angle
 */
class MonsterGenerator extends CreatureGenerationProcedure {
	
    int count = 0;
    Random random = new Random();
    
    public CreatureDefinition generate() {
        
        String name = "Monster " + count++; //set The Name
        CreatureDefinition ret = game.registry.creatureDefs.get("Base").clone();
        
        ret.name = name;
        
        ret.types.add(CreatureDefinition.Detail.MONSTROUS_HUMANOID);
        
        //Choose primary strength: Tough, Strong, Fast, Frenzied, Spined, Armored, Enduring, Regenerating.
        ArrayList<CreatureDefinition.Detail> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(CreatureDefinition.Detail.values()));
        arrayList = arrayList.subList(arrayList.indexOf(CreatureDefinition.Detail._PHYSICAL_) +1, arrayList.indexOf(CreatureDefinition.Detail._NATURAL_ATTACKS_));
        
        int rand = random.nextInt(arrayList.size());
        ret.details.add(arrayList.get(rand));
        
        //Determine secondary strengths and then choose them.
        
        //Not yet implemented.
        
        //Determine stats based on strengths
        
        int statTotal = 180; //Set the total amount of core attribute bonuses available.
        int tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.stats.getStat("Strength").modifyBase(tempStat-50);
        
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.stats.getStat("Dexterity").modifyBase(tempStat-50);
        
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.stats.getStat("Agility").modifyBase(tempStat-50);
        
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.stats.getStat("Vitality").modifyBase(tempStat-50);
        
        ret.stats.getStat("Endurance").modifyBase(statTotal-50);
        
        //Choose primary attack
        
        arrayList.clear();
        arrayList.addAll(Arrays.asList(CreatureDefinition.Detail.values()));
        arrayList = arrayList.subList(arrayList.indexOf(CreatureDefinition.Detail._NATURAL_ATTACKS_) +1, arrayList.indexOf(CreatureDefinition.Detail._END_));
        
        rand = random.nextInt(arrayList.size());
        ret.details.add(arrayList.get(rand));
        
        //Determine whether or not to give a secondary attack, and then choose it.
        
        //to be implemented
        
        //Determine any special qualities based on strengths
        
        //to be implemented
        
        //Determine XP and Loot
        
        Texture2D glTexture = game.registry.loadTextureRectangle(new File("RAW/Images/Character.png"));
        ret.setSprite(new Sprite(glTexture, 2, 2));
        
        ret.setControllerType(game.registry.controllers.get("Fighter.groovy"));
        ret.itemDrops.add(new ItemDrop(game.registry.itemPools.get("ItemRoller.groovy"), 2, 1));
        ret.stats.addStat("XP", new NumericStat(100));
        return ret;
    }
    
}

