
import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import stat.*;
import generation.*;
import java.util.Random;
import com.samrj.devil.gl.Texture2D;
import static templeoftheelements.TempleOfTheElements.game;

/**
 *
 * @author angle
 */
class MonsterGenerator implements GenerationProcedure<CreatureDefinition> {
	
    int count = 0;
    Random random = new Random();
    
    public CreatureDefinition generate(Object o) {
        throw new UnsupportedOperationException();
    }
    
    public CreatureDefinition generate() {
        int rand = random.nextInt(2);
        
        String name = "Monster " + count++; //set The Name
        CreatureDefinition ret = game.registry.creatureDefs.get("Base").clone();
        
        ret.types.add(CreatureDefinition.Detail.MONSTROUS_HUMANOID);
        
        //Choose primary strength: Tough, Strong, Fast, Frenzied, Spined, Armored, Enduring, Regenerating.
        ArrayList<CreatureDefinition.Detail> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(CreatureDefinition.Detail.values()));
        arrayList = arrayList.subList(arrayList.indexOf(CreatureDefinition.Detail._PHYSICAL_) +1, arrayList.indexOf(CreatureDefinition.Detail._NATURAL_ATTACKS_));
        
        rand = random.nextInt(arrayList.size());
        ret.details.add(arrayList.get(rand));

        
//        for (CreatureDefinition.Detail detail : ret.details) {
//            System.out.println(detail);
//        }
        
        //Determine secondary strengths and then choose them.
        
        //Not yet implemented.
        
        //Determine stats based on strengths
        
        int statTotal = 120; //Set the total amount of core attribute bonuses available.
        int tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.getStat("Strength").modifyBase(tempStat-50);
        
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.getStat("Dexterity").modifyBase(tempStat-50);
        
        ret.getStat("Constitution").modifyBase(statTotal-50);
        
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
        game.registry.creatureDefs.put(name, ret);
        return ret;
    }
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        return definition;
    }
    
    public boolean isApplicable(CreatureDefinition definition) {
        throw new UnsupportedOperationException();
    }
    
}

