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
        
        game.registry.readRaw(new File("RAW/JSON/StatDescriptions.json"));
        game.registry.creatureTypeGenerator.addBaseProcedure(game.registry.readGroovyScript(new File("RAW/Scripts/MonsterGenerator.groovy")));
        game.registry.nodeGenerator.addProcedure(Spell.Detail.PROJECTILE, game.registry.readGroovyScript(new File("RAW/Scripts/MissileGenerator.groovy")));
        game.registry.nodeGenerator.addProcedure(Spell.Detail.AREA_TARGET, game.registry.readGroovyScript(new File("RAW/Scripts/AreaSpellGenerator.groovy")));
        game.registry.nodeGenerator.addProcedure(Spell.Detail.ENEMY_TARGET, game.registry.readGroovyScript(new File("RAW/Scripts/EnemyTargetSpellGenerator.groovy")));
        game.registry.nodeGenerator.addProcedure(Spell.Detail.DAMAGE, game.registry.readGroovyScript(new File("RAW/Scripts/DamageSpellGenerator.groovy")));
//        game.registry.creatureTypeGenerator.addBaseProcedure(game.registry.readGroovyScript(new File("FighterGenerator.groovy")));
//        game.registry.creatureTypeGenerator.addBaseProcedure(game.registry.readGroovyScript(new File("WandererGenerator.groovy")));
        game.registry.loadControllerScript(new File("RAW/Scripts/Wanderer.groovy"));
        game.registry.loadControllerScript(new File("RAW/Scripts/Fighter.groovy"));
        game.registry.loadStatusEffect(new File("RAW/Scripts/Fatigue.groovy"));
        Texture2D glTexture = game.registry.loadTextureRectangle(new File("RAW/Images/Character.png"));
        game.registry.loadTextureRectangle(new File("RAW/Images/Icons.png"));
        game.registry.loadTextureRectangle(new File("RAW/Images/Items.png"));
        game.registry.loadTextureRectangle(new File("RAW/Images/Sprites.png"));
        game.registry.loadTexture2D(new File("RAW/Images/Stone Floor.png"));
        game.registry.readRaw(new File("RAW/JSON/MagicEffects.json"));
        game.registry.readRaw(new File("RAW/JSON/Weapons.json"));
        game.registry.readRaw(new File("RAW/JSON/Elements.json"));
        game.registry.loadItemPool(new File("RAW/Scripts/ItemRoller.groovy"));
        game.registry.treeGenerator = game.registry.readGroovyScript(new File("RAW/Scripts/MagicalStyleGenerator.groovy"));
        game.registry.clusterGenerator = game.registry.readGroovyScript(new File("RAW/Scripts/ClusterGenerator.groovy"));
        Floor.RoomSchematic.procedures.add(game.registry.readGroovyScript(new File("RAW/Scripts/RockyRoom.groovy")));
        game.registry.readRaw(new File("RAW/JSON/Creatures.json"));
        
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
        
        Item item = game.registry.itemPools.get("ItemRoller.groovy").generate(1, 0);
        game.room.add(item);
        
        Effect effect = new Effect() {
            public float effect(EffectSource source, Object o) {
                ((Creature) o).getStat("Mana").modifyBase(20);
                return 20;
            }
        };
        Consumable consumable = new Consumable("Mana Crystal", effect, new VectorCircle(0.4f), 1);
        consumable.addStat("Size", new NumericStat(0.3f));
        game.room.add(consumable);
        
        game.player.gainExperience(999999);
        
        game.room.enter();
        
    }
    
//    public void genMagicEffect() {
//        MagicItemEffect e = new MagicItemEffect();
//        
//        game.registry.magicEffects.add()
//    }
    
}