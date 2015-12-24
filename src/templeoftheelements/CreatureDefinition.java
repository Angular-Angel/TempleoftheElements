
package templeoftheelements;

import java.util.ArrayList;
import java.util.HashMap;
import org.jbox2d.common.Vec2;
import stat.NoSuchStatException;
import stat.NumericStat;
import stat.StatContainer;
import templeoftheelements.collision.Creature;
import templeoftheelements.display.Renderable;
import templeoftheelements.item.ItemDrop;

/**
 *
 * @author angle
 */


public class CreatureDefinition extends StatContainer {
    
    private String name;
    private Controller controllerType;
    private Renderable sprite;
    
    public ArrayList<BodyPartDefinition> bodyParts;
    private HashMap<String, Float> resistances;
    public ArrayList<ItemDrop> itemDrops;
    public ArrayList<Object> abilities;
    
    public CreatureDefinition(String name) {
        this.name = name;
        bodyParts = new ArrayList<>();
        resistances = new HashMap<>();
        itemDrops = new ArrayList<>();
        abilities = new ArrayList<>();
    }
    
    public void addResistance(String type, float f) {
        resistances.put(type, f);
    }
    
    public void addAbility(Object a) {
        abilities.add(a);
    }
    
    public CreatureDefinition clone() {
        CreatureDefinition ret = new CreatureDefinition(name);
        ret.addAllStats(viewStats());
        for (BodyPartDefinition b : bodyParts) ret.addBodyPart(b.name, b.position);
        for (String s : resistances.keySet()) ret.addResistance(s, resistances.get(s));
        for (ItemDrop i : itemDrops) ret.itemDrops.add(i);
        return ret;
    }
    
    public Creature genCreature() throws NoSuchStatException {
        return genCreature(0, 0);
    }
    
    public Creature genCreature(float x, float y) throws NoSuchStatException {
        Creature ret = new Creature(new Vec2(x, y), this);
        if (sprite != null) ret.setSprite(sprite);
        for (BodyPartDefinition b : bodyParts) b.genBodyPart(ret);
        for (String type : resistances.keySet()) ret.addResistance(type, resistances.get(type));
        ret.addStat("HP", new NumericStat(ret.getScore("Max HP")));
        ret.addStat("Mana", new NumericStat(ret.getScore("Max Mana")));
        ret.addStat("Stamina", new NumericStat(ret.getScore("Max Stamina")));
        for (Object o : abilities) ret.addAbility(o);
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
