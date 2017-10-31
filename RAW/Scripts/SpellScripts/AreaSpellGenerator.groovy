import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;
import templeoftheelements.item.*;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectSource;
import stat.*;
import generation.*;
import static templeoftheelements.TempleOfTheElements.game;
import generation.ProceduralGenerator;
import templeoftheelements.player.characterwheel.*;
import templeoftheelements.player.characterwheel.CharacterTreeDef.AbilityDefinition;

public class AreaSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilityDefinition modify(AbilityDefinition abilityDef) {
        AreaSpell spell = new AreaSpell("Area Spell", new VectorCircle(0.5));
        
        spell.addStat("Cast Time", new NumericStat(0));
        spell.addStat("Mana Cost", new NumericStat(0));
        spell.addStat("Cooldown", new NumericStat(0));
        
        abilityDef.ability = spell
        return abilityDef;
    }
}