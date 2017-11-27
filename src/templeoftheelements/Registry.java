
package templeoftheelements;

import templeoftheelements.spells.Spell;
import templeoftheelements.controller.BasicAI;
import templeoftheelements.controller.Controller;
import templeoftheelements.creature.CreatureDefinition;
import templeoftheelements.creature.CreatureTypeGenerator;
import com.samrj.devil.gl.DGL;
import com.samrj.devil.gl.Texture2D;
import generation.GenerationProcedure;
import generation.ProceduralGenerator;
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
import stat.StatDescriptor;
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
import templeoftheelements.creature.Ability;
import templeoftheelements.player.CharacterTree;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectListing;
import templeoftheelements.creature.AbilityGenerator;
import templeoftheelements.creature.StatusEffect;
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
    public HashMap<String, EffectListing> effects;
    public HashMap<String, StatusEffect> statusEffects;
    public HashMap<String, StatDescriptor> statDescriptors;
    public ArrayList<Element> elementList;
    public ArrayList<MagicItemDef> magicEffects;
    public ArrayList<CreatureDefinition> creatureList;
    public CreatureTypeGenerator creatureTypeGenerator;
    public ProceduralGenerator<CharacterTree> treeGenerator;
    public GenerationProcedure<CharacterTree> clusterGenerator;
    public AbilityGenerator abilityGenerator;
    
    public Registry() {
        creatureDefs = new HashMap<>();
        textures = new HashMap<>();
        itemDefs = new HashMap<>();
        controllers = new HashMap<>();
        magicEffects = new ArrayList<>();
        itemPools = new HashMap<>();
        creatureList = new ArrayList<>();
        elements = new HashMap<>();
        elementList = new ArrayList<>();
        spriteSheets = new HashMap<>();
        statusEffects = new HashMap<>();
        statDescriptors = new HashMap<>();
        effects = new HashMap<>();
        controllers.put("BasicAI.java", new BasicAI());
        creatureTypeGenerator = new CreatureTypeGenerator();
        abilityGenerator = new AbilityGenerator();
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
                   case "Stat Description": StatDescriptor stat = readStatDescriptor((JSONObject) obj);
                       statDescriptors.put(stat.identifier, stat);
                       break;
                   case "Magic Item Definition":
                   case "Magic Equipment Definition":
                   case "Magic Weapon Definition":
                       readMagicItemDef((JSONObject) obj);
                       break;
                   case "Effect":
                       readEffectListing((JSONObject) obj);
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
    
    public ItemGenerator loadItemPool(File file) {
        ItemGenerator ret = (ItemGenerator) readGroovyScript(file);
        
        ret.init();
        
        itemPools.put(file.getName(), ret);
        
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
        
        ja = (JSONArray) obj.get("PrimaryAttributes");
        if (ja != null)
        for (Object o : ja) {
            ret.primaryAttributes.add(statDescriptors.get((String) o));
        }
        
        ja = (JSONArray) obj.get("SecondaryAttributes");
        if (ja != null)
        for (Object o : ja) {
            ret.secondaryAttributes.add(statDescriptors.get((String) o));
        }
        
        ja = (JSONArray) obj.get("DebuffAttributes");
        if (ja != null)
        for (Object o : ja) {
            ret.debuffAttributes.add(statDescriptors.get((String) o));
        }
        
        ja = (JSONArray) obj.get("Details");
        if (ja != null) {
            JSONArray details = (JSONArray) ja.get(0);
            for (Object detail : details) {
                ret.targetDetails.add(Spell.Detail.valueOf((String) detail));

            }
            details = (JSONArray) ja.get(1);
            for (Object detail : details) {
                ret.effectDetails.add(Spell.Detail.valueOf((String) detail));

            }
            details = (JSONArray) ja.get(2);
            for (Object detail : details) {
                ret.costDetails.add(Spell.Detail.valueOf((String) detail));

            }
            details = (JSONArray) ja.get(3);
            for (Object detail : details) {
                ret.scalingDetails.add(Spell.Detail.valueOf((String) detail));

            }
        }
        
        ja = (JSONArray) obj.get("Focuses");
        if (ja != null)
        for (Object o : ja) {
            ret.focuses.add(CharacterTree.Focus.valueOf((String) o));
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
    
    public StatusEffect loadStatusEffect(File file) {
        StatusEffect ret = (StatusEffect) readGroovyScript(file);
        
        statusEffects.put(file.getName().replace(".groovy", ""), ret);
        
        return ret;
    }
    
    public AttackDefinition readAttackDef(JSONArray ja) {
        String name = (String) ja.get(0);
        String type = (String) ja.get(1);
        Renderable sprite = readSprite((JSONArray) ja.get(2));
        JSONArray stats = (JSONArray) ja.get(3); 
        AttackDefinition ret = new AttackDefinition(name, sprite, type);
        ret.stats.addAllStats(readJSONStats(stats)); //add the stats that the attack def will have.
        
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
        String parent = (String) obj.get("Parent");
        CreatureDefinition ret;
        if (parent != null) {
            ret = creatureDefs.get(parent).clone();
            ret.name = name;
        } else {
            ret = new CreatureDefinition(name); //initialize return variable
        }
        
        JSONArray stats = (JSONArray) obj.get("Stats"); 
        
        if (stats != null)
            ret.addAllStats(readJSONStats(stats)); //add the stats that the creature def will have.

        JSONArray bodyParts = (JSONArray) obj.get("Body Parts");

        if (bodyParts != null)
            for (Object o : bodyParts) {
                JSONArray bodyPart = (JSONArray) o;
                ret.addBodyPart((String) bodyPart.get(0), ((Long) bodyPart.get(1)).floatValue());
            }

        JSONArray itemDrops = (JSONArray) obj.get("Item Drops");

        if (itemDrops != null)
            for (Object o : itemDrops) {
                JSONArray itemDrop = (JSONArray) o;
                ret.itemDrops.add(readItemDrop(itemDrop));
            }

        ret.setControllerType(controllers.get((String) obj.get("Controller")));

        JSONArray abilities = (JSONArray) obj.get("Abilities");

        if (abilities != null)
            for (Object o : abilities) 
                ret.abilities.add((Ability) readGroovyScript((String) o));
        
        addCreatureDef(name, ret);
        
        return ret;
    }
    
    public void addCreatureDef(String name, CreatureDefinition def) {
        creatureDefs.put(name, def);
        creatureList.add(def);
    }
    
    public EffectListing readEffectListing(JSONObject obj) {
        String name = (String) obj.get("Name");
        EffectListing ret = new EffectListing(name);
        
        JSONArray variables = (JSONArray) obj.get("Variables"); 
        
        for (Object o : variables) ret.variables.add((String) o);
        
        ret.effect = (String) obj.get("Effect");
        
        effects.put(name, ret);
        
        return ret;
    }
    
}
