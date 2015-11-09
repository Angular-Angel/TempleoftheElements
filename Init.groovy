import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;
import templeoftheelements.item.*;
import static templeoftheelements.TempleOfTheElements.game;
import stat.*;
import java.util.Random;
import com.samrj.devil.gl.Texture2D;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;

/**
 *
 * @author angle
 */
class Initiator implements InitScript {

    public void Init() {
        
        game.registry.creatureTypeGenerator.addBaseProcedure(game.registry.readGroovyScript(new File("FighterGenerator.groovy")));
        game.registry.creatureTypeGenerator.addBaseProcedure(game.registry.readGroovyScript(new File("WandererGenerator.groovy")));
        game.registry.loadControllerScript(new File("Wanderer.groovy"));
        game.registry.loadControllerScript(new File("Fighter.groovy"));
        Texture2D glTexture = game.registry.loadTextureRectangle(new File("Character.png"));
        game.registry.loadTextureRectangle(new File("Icons.png"));
        game.registry.loadTextureRectangle(new File("Items.png"));
        game.registry.loadTexture2D(new File("Stone Floor.png"));
        game.registry.readRaw(new File("MagicEffects.json"));
        game.registry.readRaw(new File("Weapons.json"));
        game.registry.readRaw(new File("Elements.json"));
        game.registry.loadItemPool("ItemRoller.groovy", );
        game.registry.treeGenerator = game.registry.readGroovyScript(new File("MagicalStyleGenerator.groovy"));
        Floor.RoomSchematic.procedures.add(game.registry.readGroovyScript(new File("RockyRoom.groovy")));
        game.registry.readRaw(new File("Creatures.json"));
        
        for (int i = 0; i < 10; i++) {
            CreatureDefinition definition = game.registry.creatureTypeGenerator.generate();
            game.registry.addCreatureDef(definition.getName(), definition);
        }
        
        Floor.FloorSchematic schematic = new Floor.FloorSchematic(30, 30);
        Floor floor = schematic.generate();
        
        game.room = floor.getEntrance();
        Creature creature = game.registry.creatureDefs.get("Human").genCreature();
        creature.setSprite(new Sprite(glTexture, 2, 2));
        game.player = new Player(creature);
        
        Effect effect = new Effect() {
            public float effect(EffectSource source, Collidable c) {
                ((Creature) c).getStat("Mana").modifyBase(20);
                return 20;
            }
        };
        Consumable consumable = new Consumable("Mana Crystal", effect, new VectorCircle(0.4f), 1);
        consumable.addStat("Size", new NumericStat(0.3f));
        game.room.add(consumable);
        
        game.room.enter();
        
    }
    
//    public void genMagicEffect() {
//        MagicItemEffect e = new MagicItemEffect();
//        
//        game.registry.magicEffects.add()
//    }
    
}