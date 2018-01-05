import templeoftheelements.Element;
import templeoftheelements.spells.MissileSpell;
import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.creature.AbilityDefinition;
import templeoftheelements.creature.Ability;
import templeoftheelements.display.VectorCircle;
import templeoftheelements.item.AttackDefinition;
import stat.Stat;
import stat.EquationStat;
import stat.NumericStat;
import java.util.Random;
import java.util.Collection;
import java.util.HashMap;
import templeoftheelements.player.CharacterWheel;
import templeoftheelements.player.CharacterTree;
import templeoftheelements.player.AbilitySkill;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectContainer;

public class MissileSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilitySkill modify(AbilitySkill abilitySkill) {
        CharacterTree tree = (CharacterTree) abilitySkill.tree; //the tree to which the node will belong

        String name;
        
        Element element = tree.element;

        int sizeValue = 1, speedValue = 1, pool = abilitySkill.stats.getScore("Pool")/4 + random.nextInt((int) (abilitySkill.stats.getScore("Pool") / 4));

        abilitySkill.stats.getStat("Pool").modify("Missile Spell", -pool);

        //Stats: Range, Size, Speed, Damage, Cast Speed, Mana Cost
        Stat size, speed;

        sizeValue = random.nextInt(pool);
        
        speedValue = pool - sizeValue;

        speedValue = 100 + (speedValue -1) * 10;
        speed = new EquationStat("" + speedValue + " * [Source@Spell Speed Multiplier]");

        sizeValue = 0.3 + (sizeValue -1) * 0.03;
        size = new NumericStat(sizeValue);

        name = element.name;

        AttackDefinition missile;


        missile = new AttackDefinition(name, new VectorCircle(1), element.name);
        missile.stats.addStat("Ranged Attack", new NumericStat(1));
        missile.stats.addStat("Size", size);
        missile.stats.addStat("Speed", speed);
        
        AbilityDefinition ret = new AbilityDefinition("Missile Spell") {
            AttackDefinition missileDefinition = missile;
            
            public String getDescription() {
                return "This spell shoots a missile!";
            }
            public Ability getAbility() {
                MissileSpell spell = new MissileSpell(this, missile.copy());
       
                spell.stats.addAllStats(stats.viewStats());
                
                return spell;
            }
            
            @Override
            public void addAllEffects(EffectContainer effects) {
                missileDefinition.addAllEffects(effects);
            }

            @Override
            public Collection<Effect> getAllEffects() {
                return missileDefinition.getAllEffects();
            }
        }

        ret.stats.addStat("Cast Time", new NumericStat(0));
        ret.stats.addStat("Mana Cost", new NumericStat(0));
        ret.stats.addStat("Cooldown", new NumericStat(0));

        abilitySkill.abilityDef = ret;
        
        return abilitySkill;
    }
}