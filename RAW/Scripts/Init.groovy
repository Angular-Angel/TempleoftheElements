import templeoftheelements.InitScript;
import templeoftheelements.Floor;
import templeoftheelements.spells.Spell;
import templeoftheelements.creature.CreatureDefinition;
import templeoftheelements.creature.Creature;
import templeoftheelements.creature.Ability;
import templeoftheelements.display.Sprite;
import templeoftheelements.display.VectorCircle;
import templeoftheelements.player.Player;
import templeoftheelements.item.Item;
import templeoftheelements.item.Consumable;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectSource;
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
        game.registry.creatureTypeGenerator.addProcedure(CreatureDefinition.Detail.MONSTROUS_HUMANOID, game.registry.readGroovyScript(new File("RAW/Scripts/CreatureScripts/MonsterGenerator.groovy")));
        game.registry.creatureTypeGenerator.addProcedure(CreatureDefinition.Detail.STRONG, game.registry.readGroovyScript(new File("RAW/Scripts/CreatureScripts/StrongEnemyGenerator.groovy")));
        game.registry.creatureTypeGenerator.addProcedure(CreatureDefinition.Detail.FAST, game.registry.readGroovyScript(new File("RAW/Scripts/CreatureScripts/FastEnemyGenerator.groovy")));
        game.registry.creatureTypeGenerator.addProcedure(CreatureDefinition.Detail.TOUGH, game.registry.readGroovyScript(new File("RAW/Scripts/CreatureScripts/ToughEnemyGenerator.groovy")));
        game.registry.creatureTypeGenerator.addProcedure(CreatureDefinition.Detail.FRENZIED, game.registry.readGroovyScript(new File("RAW/Scripts/CreatureScripts/FrenziedEnemyGenerator.groovy")));
        game.registry.creatureTypeGenerator.addProcedure(CreatureDefinition.Detail.SPINED, game.registry.readGroovyScript(new File("RAW/Scripts/CreatureScripts/SpinedEnemyGenerator.groovy")));
        game.registry.creatureTypeGenerator.addProcedure(CreatureDefinition.Detail.REGENERATING, game.registry.readGroovyScript(new File("RAW/Scripts/CreatureScripts/RegeneratingEnemyGenerator.groovy")));
        game.registry.creatureTypeGenerator.addProcedure(CreatureDefinition.Detail.ENDURING, game.registry.readGroovyScript(new File("RAW/Scripts/CreatureScripts/EnduringEnemyGenerator.groovy")));
        game.registry.creatureTypeGenerator.addProcedure(CreatureDefinition.Detail.CLAW, game.registry.readGroovyScript(new File("RAW/Scripts/CreatureScripts/ClawAttackEnemyGenerator.groovy")));
        game.registry.creatureTypeGenerator.addProcedure(CreatureDefinition.Detail.BITE, game.registry.readGroovyScript(new File("RAW/Scripts/CreatureScripts/BiteAttackEnemyGenerator.groovy")));
        game.registry.abilityGenerator.addProcedure(Ability.Detail.PROJECTILE, game.registry.readGroovyScript(new File("RAW/Scripts/SpellScripts/MissileSpellGenerator.groovy")));
        game.registry.abilityGenerator.addProcedure(Ability.Detail.AREA_TARGET, game.registry.readGroovyScript(new File("RAW/Scripts/SpellScripts/AreaSpellGenerator.groovy")));
        game.registry.abilityGenerator.addProcedure(Ability.Detail.ENEMY_TARGET, game.registry.readGroovyScript(new File("RAW/Scripts/SpellScripts/EnemyTargetSpellGenerator.groovy")));
        game.registry.abilityGenerator.addProcedure(Ability.Detail.MANA_COST, game.registry.readGroovyScript(new File("RAW/Scripts/SpellScripts/ManaCostSpellGenerator.groovy")));
        game.registry.abilityGenerator.addProcedure(Ability.Detail.COOLDOWN, game.registry.readGroovyScript(new File("RAW/Scripts/SpellScripts/CooldownSpellGenerator.groovy")));
        game.registry.abilityGenerator.addProcedure(Ability.Detail.CAST_TIME, game.registry.readGroovyScript(new File("RAW/Scripts/SpellScripts/CastTimeSpellGenerator.groovy")));
        game.registry.abilityGenerator.addProcedure(Ability.Detail.DAMAGE, game.registry.readGroovyScript(new File("RAW/Scripts/SpellScripts/DamageSpellGenerator.groovy")));
        game.registry.abilityGenerator.addProcedure(Ability.Detail.DEBUFF, game.registry.readGroovyScript(new File("RAW/Scripts/SpellScripts/DebuffSpellGenerator.groovy")));
//        game.registry.creatureTypeGenerator.addBaseProcedure(game.registry.readGroovyScript(new File("FighterGenerator.groovy")));
//        game.registry.creatureTypeGenerator.addBaseProcedure(game.registry.readGroovyScript(new File("WandererGenerator.groovy")));
        game.registry.loadControllerScript(new File("RAW/Scripts/AIScripts/Wanderer.groovy"));
        game.registry.loadControllerScript(new File("RAW/Scripts/AIScripts/Fighter.groovy"));
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
        
        Effect effect = new Effect("Mana Crystal") {
            public float effect(EffectSource source, Object o) {
                ((Creature) o).getStat("Mana").modifyBase(200);
                return 200;
            }
           
            public String getDescription() {
                return "Gives the user 200 mana."
            }
        };
        Consumable consumable = new Consumable("Mana Crystal", effect, new VectorCircle(0.4f), 1);
        consumable.stats.addStat("Size", new NumericStat(0.3f));
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