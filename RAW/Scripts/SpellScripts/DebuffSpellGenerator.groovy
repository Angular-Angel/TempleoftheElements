import templeoftheelements.spells.Spell;
import templeoftheelements.creature.AbilityGenerationProcedure;
import templeoftheelements.creature.Ability;
import templeoftheelements.creature.Creature; // these are used for
import templeoftheelements.creature.Debuff;   // the groovy script importing.
import stat.StatDescriptor;
import stat.NumericStat;
import java.util.Random;
import templeoftheelements.player.CharacterWheel;
import templeoftheelements.player.CharacterTree;
import templeoftheelements.player.AbilitySkill;
import templeoftheelements.effect.Effect;
import templeoftheelements.effect.EffectSource;

public class DebuffSpellGenerator extends AbilityGenerationProcedure {

     //our random number generator;
    Random random = new Random();
    
    @Override
    public AbilitySkill modify(AbilitySkill abilitySkill) {
        CharacterTree tree = (CharacterTree) abilitySkill.tree; //the tree to which the node will belong
        
        Spell spell = (Spell) abilitySkill.ability;
        
        final ArrayList<StatDescriptor> debuffAttributes = tree.element.debuffAttributes;
        
        int pool = Math.min(20, (abilitySkill.getScore("Pool"))), i = 0;
        
        Effect effect;
        
        String name = "Debuff";
        
        if (spell.containsEffect(name)) {
            
            effect = spell.getEffect(name);
            
        } else {
        
            effect = new Effect(name) {

                @Override
                public float effect(EffectSource source, Object obj) {
                    if (source instanceof Creature && obj instanceof Creature) {
                        Debuff debuff = new Debuff(name, 30, null);
                        debuff.addAllStats(this);
                        ((Creature) obj).addStatusEffect(debuff);
                    } 

                    return 0;
                }

                public String getDescription() {
                    String ret = "Debuffs the target:";

                    for (String s : getStatList()) {
                        ret += "\n" + s + ": -" + getScore(s);
                    }

                    return ret;
                }
            };
        
        }
        
        while (pool > Ability.Detail.DEBUFF.cost && i < 10) {
            i++;
            StatDescriptor debuffStat = debuffAttributes.get(random.nextInt(debuffAttributes.size()));
            int debuffValue = 1 + random.nextInt((int) (pool / Ability.Detail.DEBUFF.cost) - 1);
            effect.addStat(debuffStat.name, new NumericStat(debuffValue));
            abilitySkill.getStat("Pool").modifyBase(-debuffValue * Ability.Detail.DEBUFF.cost);
//            ((Spell) abilityDef.ability).description += "\nDebuff: " + debuffStat.name + ", " + debuffValue;
        }
        
        spell.addEffect(effect);
        
        return abilitySkill;
    }
}