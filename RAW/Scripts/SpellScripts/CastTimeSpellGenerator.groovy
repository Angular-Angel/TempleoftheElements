import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;
import templeoftheelements.item.*;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectSource;
import stat.*;
import generation.*;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import generation.ProceduralGenerator;
import templeoftheelements.player.characterwheel.*;
import templeoftheelements.player.characterwheel.CharacterTreeDef.AbilityDefinition;
import org.jbox2d.common.Vec2;

public class CastTimeSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
        int pool = Math.min(10, (abilityDef.getScore("Cost Pool")));
        if (pool == 0) {
            abilityDef.getStat("Cost Pool").modifyBase(-1);
            return abilityDef.ability;
        }
        
        int castTime = 1 + random.nextInt(pool) / Spell.Detail.CAST_TIME.cost;
        
        abilityDef.ability.addStat("Cooldown", new NumericStat(castTime));
        
        abilityDef.getStat("Cost Pool").modifyBase(-castTime * Spell.Detail.CAST_TIME.cost);
        abilityDef.getStat("Pool").modifyBase(castTime * Spell.Detail.CAST_TIME.cost);
        
        return abilityDef;
    }

}