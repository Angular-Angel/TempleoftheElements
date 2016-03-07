
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
    
    public CreatureDefinition generate() {
        int rand = random.nextInt(2);
        
        String name = "Monster " + count++; //set The Name
        CreatureDefinition ret = game.registry.creatureDefs.get("Base").clone();
        
        //Choose primary strength: Tough, Strong, Fast, Frenzied, Spined, Armored, Enduring, Regenerating.
        rand = random.nextInt(4);
        switch (rand) {
            case 0: 
                ret.details.add(CreatureDefinition.Detail.Tough);
                break;
            case 1: 
                ret.details.add(CreatureDefinition.Detail.Strong);
                break;
            case 2: 
                ret.details.add(CreatureDefinition.Detail.Fast);
                break;
            case 3: 
                ret.details.add(CreatureDefinition.Detail.Frenzied);
                break;
            case 4: 
                ret.details.add(CreatureDefinition.Detail.Spined);
                break;
            case 5: 
                ret.details.add(CreatureDefinition.Detail.Armored);
                break;
            case 6: 
                ret.details.add(CreatureDefinition.Detail.Enduring);
                break;
            case 7: 
                ret.details.add(CreatureDefinition.Detail.Regenerating);
                break;
                
        }
        
//        for (CreatureDefinition.Detail detail : ret.details) {
//            System.out.println(detail);
//        }
        
        //Determine secondary strengths and then choose them.
        
        //Not yet implemented.
        
        //Determine stats based on strengths
        
        int statTotal = 12; //Set the total amount of core attribute bonuses available.
        int tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.getStat("Strength").modifyBase(tempStat);
        
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.getStat("Dexterity").modifyBase(tempStat);
        
        ret.getStat("Constitution").modifyBase(statTotal);
        
        if (ret.details.contains(CreatureDefinition.Detail.Tough))
            ret.getStat("Constitution").modifyBase(5);
            
        if (ret.details.contains(CreatureDefinition.Detail.Strong))
            ret.getStat("Strength").modifyBase(5);
            
        if (ret.details.contains(CreatureDefinition.Detail.Fast))
            ret.getStat("Dexterity").modifyBase(5);
            
        if (ret.details.contains(CreatureDefinition.Detail.Frenzied)) {
            String equation = ((EquationStat) ret.getStat("Attack Speed")).equation;
            equation += " * 0.80";
            ((EquationStat) ret.getStat("Attack Speed")).equation = equation;
        }
        
        //Choose primary attack: Claws, Bite, later:Sting.
        
        rand = random.nextInt(2);
        AttackDefinition attack;
        switch (rand) {
            case 0:
                attack = new AttackDefinition("Claw", new VectorCircle(1), "Slashing");
                attack.addStat("Melee Attack", new BinaryStat());
                attack.addStat("Size", new NumericStat(0.43));
                attack.addStat("Duration", new NumericStat(13));
                attack.addStat("Reach", new NumericStat(0.8)); 
                attack.addStat("Angular Travel",  new NumericStat(70));
                attack.addStat("Distance Travel", new NumericStat(0));
                attack.addStat("Recovery Time", new NumericStat(13));
                attack.addStat("Damage Multiplier", new NumericStat(0));
                attack.addStat("Stamina Cost", new NumericStat(4));
                break;
            case 1: 
                attack = new AttackDefinition("Bite", new VectorCircle(1), "Piercing");
                attack.addStat("Melee Attack", new BinaryStat());
                attack.addStat("Size", new NumericStat(0.43));
                attack.addStat("Duration", new NumericStat(2));
                attack.addStat("Reach", new NumericStat(0.8)); 
                attack.addStat("Angular Travel",  new NumericStat(0));
                attack.addStat("Distance Travel", new NumericStat(5));
                attack.addStat("Recovery Time", new NumericStat(50));
                attack.addStat("Damage Multiplier", new NumericStat(0));
                attack.addStat("Stamina Cost", new NumericStat(4));
                break;
        }
        
        ret.addAbility(new AttackAction(attack));
        
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
        throw new UnsupportedOperationException();
    }
    
    public boolean isApplicable(CreatureDefinition definition) {
        throw new UnsupportedOperationException();
    }
    
}

