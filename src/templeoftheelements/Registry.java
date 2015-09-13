
package templeoftheelements;

import com.samrj.devil.gl.DGL;
import com.samrj.devil.gl.Texture2D;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import templeoftheelements.display.Renderable;
import templeoftheelements.display.Sprite;
import templeoftheelements.display.VectorCircle;
import templeoftheelements.item.AttackDefinition;
import templeoftheelements.item.ItemDefinition;
import templeoftheelements.item.ItemDrop;
import templeoftheelements.item.ItemGenerator;
import templeoftheelements.item.MagicEquipmentDef;
import templeoftheelements.item.MagicItemDef;
import templeoftheelements.item.MagicWeaponDef;
import templeoftheelements.item.WeaponDefinition;
import templeoftheelements.player.CharacterTreeDef;
import templeoftheelements.player.CharacterTreeGenerator;
import templeoftheelements.player.Effect;
import util.RawReader;

/**
 *
 * @author angle
 */


public class Registry extends RawReader {
    
    public HashMap<String, CreatureDefinition> creatureDefs;
    public HashMap<String, ItemDefinition> itemDefs;
    public HashMap<String, Texture2D> textures;
    public HashMap<String, Texture2D> spriteSheets;
    public HashMap<String, Controller> controllers;
    public HashMap<String, ItemGenerator> itemPools;
    public HashMap<String, Element> elements;
    public ArrayList<Element> elementList;
    public ArrayList<MagicItemDef> magicEffects;
    public CreatureTypeGenerator wandererGenerator;
    public CharacterTreeGenerator treeGenerator;
    
    public Registry() {
        creatureDefs = new HashMap<>();
        textures = new HashMap<>();
        itemDefs = new HashMap<>();
        controllers = new HashMap<>();
        magicEffects = new ArrayList<>();
        itemPools = new HashMap<>();
        elements = new HashMap<>();
        elementList = new ArrayList<>();
        spriteSheets = new HashMap<>();
        controllers.put("BasicAI.java", new BasicAI());
    }
    
    public void readRaw(File file) {
        JSONParser parser = new JSONParser();
        
        try {
            JSONArray objs = (JSONArray) parser.parse(new FileReader(file));
            for (Object obj : objs) {
                String type = (String) ((JSONObject) obj).get("Type");
                switch (type) {
                    case "Creature Definition": readCreatureDef((JSONObject) obj);
                        break;
                    case "Element": readElement((JSONObject) obj);
                        break;
                   case "Weapon Definition": readWeaponDef((JSONObject) obj);
                       break;
                   case "Magic Item Definition":
                   case "Magic Equipment Definition":
                   case "Magic Weapon Definition":
                       readMagicItemDef((JSONObject) obj);
                       break;
                        
                }
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Texture2D loadTextureRectangle(File file) throws IOException {
        Texture2D glTexture = DGL.loadTex2D(file.getPath());
        spriteSheets.put(file.getName(), glTexture);
        return glTexture;
        
    }
    
    public ItemGenerator loadItemPool(String name) {
        ItemGenerator ret = (ItemGenerator) readGroovyScript(new File(name));
        
        ret.init();
        
        itemPools.put(name, ret);
        
        return ret;
    }
    
    public Element readElement(JSONObject obj) {
        String name = (String) obj.get("Name");
        Element ret = new Element(name);
        
        JSONArray ja = (JSONArray) obj.get("Resistances");
        for (Object o : ja) {
            JSONArray resist = (JSONArray) o;
            ret.resistances.put((String) resist.get(0), ((Double) resist.get(1)).floatValue());
        }
        
        ja = (JSONArray) obj.get("Attributes");
        if (ja != null)
        for (Object o : ja) {
            ret.attributes.add((String) o);
        }
        
        ja = (JSONArray) obj.get("Details");
        if (ja != null)
        for (Object o : ja) {
            ret.details.add(Spell.Detail.valueOf((String) o));
        }
        
        ja = (JSONArray) obj.get("Focuses");
        if (ja != null)
        for (Object o : ja) {
            ret.focuses.add(CharacterTreeDef.Focus.valueOf((String) o));
        }
        
        elements.put(name, ret);
        elementList.add(ret);
        
        return ret;
    }
    
    public ItemDrop readItemDrop(JSONArray ja) {
        ItemGenerator pool = itemPools.get(ja.get(0));
        
        int level = ((Long)ja.get(1)).intValue();
        int variance = ((Long)ja.get(2)).intValue();
        
        return new ItemDrop(pool, level, variance);
    }
    
    public Texture2D loadTexture2D(File file) throws IOException {
        Texture2D glTexture = DGL.loadTex2D(file.getPath());
        textures.put(file.getName(), glTexture);
        return glTexture;
    }
    
    public WeaponDefinition readWeaponDef(JSONObject obj) {
        String name = (String) obj.get("Name");
        Renderable sprite = readSprite((JSONArray) obj.get("Sprite"));
        
        int level = ((Long) obj.get("Level")).intValue();
        int rarity = ((Long) obj.get("Rarity")).intValue();
        
        WeaponDefinition ret = new WeaponDefinition(name, sprite, level, rarity);
        JSONArray stats = (JSONArray) obj.get("Stats"); 
        ret.addAllStats(readJSONStats(stats)); //add the stats that the item def will have.
        
        JSONArray attacks = (JSONArray) obj.get("Attacks"); 
        ArrayList<AttackDefinition> attackDefs = readAttackDefs(attacks);
        for (AttackDefinition a : attackDefs) 
            ret.addAttack(a);
        
        itemDefs.put(name, ret);
        return ret;
    }
    
    public ArrayList<AttackDefinition> readAttackDefs(JSONArray ja) {
        ArrayList<AttackDefinition> ret = new ArrayList<>();
        
        for (Object o : ja) {
            ret.add(readAttackDef((JSONArray) o));
        }
        
        return ret;
    }
    
    public MagicItemDef readMagicItemDef(JSONObject jo) {
        
        MagicItemDef ret = null;
        
        String name = (String) jo.get("Name");
        
        String type = (String) jo.get("Type");
        
        int level = ((Long) jo.get("Level")).intValue();
        
        int rarity = ((Long) jo.get("Rarity")).intValue();
        
        switch (type) {
            case "Magic Weapon Definition": 
                ret = new MagicWeaponDef(name, level, rarity);
                ((MagicEquipmentDef) ret).playerBonuses.addAllStats(readJSONStats((JSONArray) jo.get("PlayerStats")));
                if (jo.containsKey("Effects")) {
                    JSONArray ja = (JSONArray) jo.get("Effects");
                
                    for (Object o : ja) 
                        ((MagicWeaponDef)ret).onHitEffects.add((Effect) readGroovyScript((String) o));
                            
                }
                break;
            case "Magic Equipment Definition": 
                ret = new MagicEquipmentDef(name, level, rarity); 
                ((MagicEquipmentDef) ret).playerBonuses.addAllStats(readJSONStats((JSONArray) jo.get("PlayerStats")));
                break;
            default:
                ret = new MagicItemDef(name, level, rarity); 
                break;
        }
        
        ret.bonuses.addAllStats(readJSONStats((JSONArray) jo.get("Stats")));
        
        magicEffects.add(ret);
        
        return ret;
    }
    
    public Controller loadControllerScript(File file) {
        Controller ret = (Controller) readGroovyScript(file);
        
        controllers.put(file.getName(), ret);
        
        return ret;
    }
    
    public AttackDefinition readAttackDef(JSONArray ja) {
        String name = (String) ja.get(0);
        String type = (String) ja.get(1);
        Renderable sprite = readSprite((JSONArray) ja.get(2));
        JSONArray stats = (JSONArray) ja.get(3); 
        AttackDefinition ret = new AttackDefinition(name, sprite, type);
        ret.addAllStats(readJSONStats(stats)); //add the stats that the attack def will have.
        
        return ret;
    }
    
    public Renderable readSprite(JSONArray ja) {
        if (ja.size() == 1) {
            return new VectorCircle(((Double) ja.get(0)).floatValue());
        } else {
            String name = (String) ja.get(0);
            float x = ((Long) ja.get(1)).floatValue();
            float y = ((Long) ja.get(2)).floatValue();
            float texWidth = ((Long) ja.get(3)).floatValue();
            float texHeight = ((Long) ja.get(4)).floatValue();
            float width = ((Long) ja.get(5)).floatValue();
            float height = ((Long) ja.get(6)).floatValue();

            return new Sprite(spriteSheets.get(name), x, y, texWidth, texHeight, width, height);
        }
    }
    
    public CreatureDefinition readCreatureDef(JSONObject obj) {
        
        String name = (String) obj.get("Name");
        
        CreatureDefinition ret = new CreatureDefinition(name); //initialize return variable
        JSONArray stats = (JSONArray) obj.get("Stats"); 
        ret.addAllStats(readJSONStats(stats)); //add the stats that the creature def will have.
        
        JSONArray bodyParts = (JSONArray) obj.get("Body Parts");
        
        for (Object o : bodyParts) {
            JSONArray bodyPart = (JSONArray) o;
            ret.addBodyPart((String) bodyPart.get(0), ((Long) bodyPart.get(1)).floatValue());
        }
        
        JSONArray itemDrops = (JSONArray) obj.get("Item Drops");
        
        for (Object o : itemDrops) {
            JSONArray itemDrop = (JSONArray) o;
            ret.itemDrops.add(readItemDrop(itemDrop));
        }
        
        ret.setControllerType(controllers.get((String) obj.get("Controller")));
        
        JSONArray ja = (JSONArray) obj.get("Abilities");
                
        for (Object o : ja) 
            ret.abilities.add(readGroovyScript((String) o));
        
        creatureDefs.put(name, ret);
        
        return ret;
    }
    
}
