
package templeoftheelements.creature;

import java.util.ArrayList;
import java.util.HashMap;
import stat.NoSuchStatException;
import stat.NumericStat;
import stat.StatContainer;
import templeoftheelements.controller.Controller;
import templeoftheelements.collision.Position;
import templeoftheelements.display.Renderable;
import templeoftheelements.item.ItemDrop;

/**
 *
 * @author angle
 */


public class CreatureDefinition extends StatContainer {
    
    public String name;
    public Controller controllerType;
    public Renderable sprite;
    
    public ArrayList<BodyPartDefinition> bodyParts;
    private final HashMap<String, Float> resistances;
    public ArrayList<ItemDrop> itemDrops;
    public ArrayList<Ability> abilities;
    public ArrayList<Detail> types;
    public ArrayList<Detail> details;
    
    public static enum Detail {
        //types:
        
        _TYPES_, HUMANOID, BEAST, MONSTROUS_HUMANOID, ELEMENTAL, CONSTRUCT, DEMON, 
        
        //physical details
        _PHYSICAL_, TOUGH, STRONG, FAST, FRENZIED, SPINED, ENDURING, REGENERATING, //ARMOURED,
        
        //Natural Attacks
        
        _NATURAL_ATTACKS_, CLAW, BITE, //PINCER, STING, TENTACLE,
        
        _END_
        
        ;
    }
    
    public CreatureDefinition(String name) {
        this.name = name;
        bodyParts = new ArrayList<>();
        resistances = new HashMap<>();
        itemDrops = new ArrayList<>();
        abilities = new ArrayList<>();
        details = new ArrayList<>();
        types = new ArrayList<>();
    }
    
    public void addResistance(String type, float f) {
        resistances.put(type, f);
    }
    
    public void addAbility(Ability a) {
        abilities.add(a);
    }
    
    public CreatureDefinition clone() {
        CreatureDefinition ret = new CreatureDefinition(name);
        ret.addAllStats(viewStats());
        for (BodyPartDefinition b : bodyParts) ret.addBodyPart(b.name, b.position);
        for (String s : resistances.keySet()) ret.addResistance(s, resistances.get(s));
        for (ItemDrop i : itemDrops) ret.itemDrops.add(i);
        for (Ability a :abilities) {
            ret.addAbility(a.copy());
        }
        return ret;
    }
    
    public Creature genCreature() throws NoSuchStatException {
        return genCreature(0, 0);
    }
    
    public Creature genCreature(float x, float y) {
        Creature ret = new Creature(new Position(x, y), this);
        if (sprite != null) ret.setSprite(sprite);
        for (BodyPartDefinition b : bodyParts) b.genBodyPart(ret);
        for (String type : resistances.keySet()) ret.addResistance(type, resistances.get(type));
        ret.stats.addStat("HP", new NumericStat(ret.stats.getScore("Max HP")));
        ret.stats.addStat("Mana", new NumericStat(ret.stats.getScore("Max Mana")));
        ret.stats.addStat("Stamina", new NumericStat(ret.stats.getScore("Max Stamina")));
        for (Ability a : abilities) ret.addAbility(a.copy());
        for (ItemDrop i : itemDrops) ret.itemDrops.add(i);
        ret.setController(controllerType.clone(ret));
        return ret;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param controllerType the controllerType to set
     */
    public void setControllerType(Controller controllerType) {
        this.controllerType = controllerType;
    }
    
    public void addBodyPart(String name, float position) {
        bodyParts.add(new BodyPartDefinition(name, position));
    }

    /**
     * @return the sprite
     */
    public Renderable getSprite() {
        return sprite;
    }

    /**
     * @param sprite the sprite to set
     */
    public void setSprite(Renderable sprite) {
        this.sprite = sprite;
    }
    
    public class BodyPartDefinition {
        private String name;
        private float position;
        
        public BodyPartDefinition(String name, float position) {
            this.name = name;
            this.position = position;
        }
        
        public void genBodyPart(Creature c) {
            if (name.equals("Hand")) c.addHand(position);
            else c.addBodyPart(name, position);
        }
    }
}
