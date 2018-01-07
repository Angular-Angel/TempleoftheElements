import templeoftheelements.creature.CreatureGenerationProcedure;
import templeoftheelements.creature.CreatureDefinition;
import templeoftheelements.display.VectorCircle;   // the groovy script importing.
import templeoftheelements.controller.AttackAction;   // the groovy script importing.
import templeoftheelements.item.AttackDefinition;
import stat.NumericStat;
import stat.BinaryStat;
import java.util.Random;
import com.samrj.devil.gl.Texture2D;
import static templeoftheelements.TempleOfTheElements.game;

/**
 *
 * @author angle
 */
class FighterGenerator extends CreatureGenerationProcedure {
	
    int count = 0;
    Random random = new Random();
    
    public CreatureDefinition generate() {
        int rand = random.nextInt(2);
        String type = null;
        switch (rand) {
            case 0: 
                type = "Melee";
                break;
            case 1: 
                type = "Ranged";
                break;
        }
        
        String name = "Fighter " + count++; //set The Name
        CreatureDefinition ret = game.registry.creatureDefs.get("Base").clone(); //Create the return variable.
        ret.name = name;
        int statTotal = 180; //Set the total amount of core attribute bonuses available.
        int tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Vitality", new NumericStat(tempStat)); //generate the core attributes
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Endurance", new NumericStat(tempStat));
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Dexterity", new NumericStat(tempStat));
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Agility", new NumericStat(tempStat));
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Strength", new NumericStat(2 + tempStat));
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Perception", new NumericStat(tempStat));
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Spirit", new NumericStat(tempStat));
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Luck", new NumericStat(tempStat));
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Acumen", new NumericStat(tempStat));
        tempStat = statTotal;
        ret.addStat("Intellect", new NumericStat(tempStat));
        
        AttackDefinition attack = new AttackDefinition(name, new VectorCircle(1), "Fire");
        attack.addStat("Melee Attack", new BinaryStat());
        attack.addStat("Size", new NumericStat(0.35));
        attack.addStat("Duration", new NumericStat(13));
        attack.addStat("Reach", new NumericStat(1.3)); 
        attack.addStat("Angular Travel",  new NumericStat(70));
        attack.addStat("Distance Travel", new NumericStat(0));
        attack.addStat("Recovery Time", new NumericStat(15));
        attack.addStat("Damage Multiplier", new NumericStat(0));
        attack.addStat("Stamina Cost", new NumericStat(4));
        
        ret.addAbility(new AttackAction(attack));
        
        Texture2D glTexture = game.registry.loadTextureRectangle(new File("Character.png"));
        ret.setSprite(new Sprite(glTexture, 2, 2));
        
        ret.setControllerType(game.registry.controllers.get("Fighter.groovy"));
        ret.itemDrops.add(new ItemDrop(game.registry.itemPools.get("ItemRoller.groovy"), 2, 1));
        game.registry.creatureDefs.put(name, ret);
        return ret;
    }
    
}

