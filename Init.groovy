import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;
import templeoftheelements.item.*;
import static templeoftheelements.TempleOfTheElements.game;
import stat.*;
import java.util.Random;
import com.samrj.devil.graphics.Texture2DData;
import com.samrj.devil.graphics.Texture2DParams;
import com.samrj.devil.graphics.GLTextureRectangle;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;

/**
 *
 * @author angle
 */
class Initiator implements InitScript {

    public void Init() {
        
        game.registry.wandererGenerator = game.registry.readGroovyScript(new File("WandererGenerator.groovy"));
        game.registry.readRaw(new File("Creatures.json"));
        GLTextureRectangle glTexture = game.registry.loadTextureRectangle(new File("Character.png"));
        game.registry.loadTextureRectangle(new File("Icons.png"));
        game.registry.loadTextureRectangle(new File("Items.png"));
        game.registry.loadTexture2D(new File("Stone Floor.png"));
        game.registry.loadControllerScript(new File("Wanderer.groovy"));
        game.registry.readRaw(new File("MagicEffects.json"));
        game.registry.readRaw(new File("Weapons.json"));
        game.registry.readRaw(new File("Elements.json"));
        game.registry.loadItemPool("ItemRoller.groovy", );
        CharacterTreeGenerator treeGen = game.registry.readGroovyScript(new File("MagicalStyleGenerator.groovy"));
        
        treeGen.genCharacterTreeDef();
        treeGen.genCharacterTreeDef();
        treeGen.genCharacterTreeDef();
        
        for (int i = 0; i < 10; i++) {
           game.registry.wandererGenerator.genType();
        }
        
        game.room = new Room(22, 24, game.registry.textures.get("Stone Floor.png"));
        Creature creature = game.registry.creatureDefs.get("Human").genCreature();
        creature.setSprite(new Sprite(glTexture, 2, 2));
        game.player = new Player(creature);
//        
//        glTexture = game.registry.textures.get("Icons.png");
        
        Weapon weapon = game.registry.itemDefs.get("Spear").generate();
        weapon.setPosition(new Vec2(3, 2));
        game.registry.magicEffects.get(0).apply(weapon);
        game.room.add(weapon);
        
        Equipment item = new Equipment("Helm of Speed", "Head", new VectorCircle(0.3f), 1);
        item.addStat("Size", new NumericStat(0.3f));
        item.setPosition(new Vec2(2, 3));
        item.playerStats.addStat("Dexterity", new NumericStat(20));
        game.room.add(item);
        
        for (int i = 0; i < 10; i++) {
            item = game.registry.itemPools.get("ItemRoller.groovy").generate(2, 1);
            item.setPosition(new Vec2(4, 2 + i));
            game.room.add(item);
        }
        
        Effect effect = new Effect() {
            public float effect(EffectSource source, Collidable c) {
                ((Creature) c).getStat("Mana").modifyBase(20);
                return 20;
            }
        };
        Consumable consumable = new Consumable("Mana Crystal", effect, new VectorCircle(0.4f), 1);
        consumable.addStat("Size", new NumericStat(0.3f));
        game.room.add(consumable);
        
        Obstacle obstacle = new Obstacle(3, 4, new CircleShape(), new VectorCircle(3), 3);
        game.room.add(obstacle);
        
        Room room2 = new Room(12, 24, game.registry.textures.get("Stone Floor.png"));
        Room.Door door = game.room.createDoor(9, 1, 11, 1);
        Room.Door door2 = room2.createDoor(5, 10, 5, 12);
        door.setDestination(door2);
        door2.setDestination(door);
        game.room.enter();
        
    }
    
//    public void genMagicEffect() {
//        MagicItemEffect e = new MagicItemEffect();
//        
//        game.registry.magicEffects.add()
//    }
    
}