
import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import stat.*;
import java.util.Random;
import static templeoftheelements.TempleOfTheElements.game;

/**
 *
 * @author angle
 */
class WandererGenerator implements GenerationProcedure<CreatureDefinition> {
	
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
        
        String name = "Wanderer " + count++; //set The Name
        CreatureDefinition ret = new CreatureDefinition(name); //Create the return variable.
        int statTotal = 12; //Set the total amount of core attribute bonuses available.
        int tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Constitution", new NumericStat(10 + tempStat)); //generate the core attrbutes
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Dexterity", new NumericStat(12 + tempStat));
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Strength", new NumericStat(10 + tempStat));
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Perception", new NumericStat(10 + tempStat));
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Spirit", new NumericStat(10 + tempStat));
        tempStat = random.nextInt(statTotal);
        statTotal -= tempStat;
        ret.addStat("Luck", new NumericStat(10 + tempStat));
        tempStat = statTotal;
        ret.addStat("Intelligence", new NumericStat(10 + tempStat));
        
        //generate the secondary attributes.
        float size = ((float) ret.getScore("Strength") + ret.getScore("Constitution")) / (2 * ret.getScore("Dexterity"));
        ret.addStat("Size", new NumericStat(size));
        ret.addStat("Acceleration", new NumericStat((float) ret.getScore("Dexterity") * 4));
        ret.addStat("Max Speed", new NumericStat((float) ret.getScore("Dexterity") * 80));
        ret.addStat("Turning Speed", new EquationStat("0.5 + [Dexterity] / 18"));
        ret.addStat("Max HP", new EquationStat("[Strength] * 2"));
        ret.addStat("Max Stamina", new EquationStat("[Constitution] * 2"));
        ret.addStat("Max Mana", new EquationStat("[Spirit] * 2"));
        ret.addStat("Damage", new NumericStat((float) ret.getScore("Strength") / 2));
        ret.addStat("Sight Range", new EquationStat("-10 + [Perception] + [Intelligence]"));
        ret.addStat("Attack Speed", new EquationStat("1 - (([Dexterity] - 10) / 50)"));
        ret.addStat("Dodge", new EquationStat("[Dexterity] - 10"));
        ret.addStat("Accuracy", new EquationStat("[Dexterity] - 10"));
        
        ret.addStat("XP", new NumericStat(10));
        
        ret.setControllerType(game.registry.controllers.get("Wanderer.groovy"));
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

