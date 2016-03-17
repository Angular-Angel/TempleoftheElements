import templeoftheelements.*;
import templeoftheelements.collision.*; // these are used for
import templeoftheelements.display.*;   // the groovy script importing.
import templeoftheelements.player.*;   // the groovy script importing.
import templeoftheelements.item.*;
import stat.*;
import generation.*;
import java.util.Random;
import java.util.ArrayList;
import static templeoftheelements.TempleOfTheElements.game;
import generation.ProceduralGenerator;
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;
import templeoftheelements.player.CharacterTreeDef.NodeDefinition;
import templeoftheelements.player.CharacterTreeDef.ClusterDefinition;

public class DebuffSpellGenerator implements GenerationProcedure<CharacterNode> {

     //our random number generator;
    Random random = new Random();

    public CharacterNode generate() {
        throw new UnsupportedOperationException();
    }

    public CharacterNode generate(Object o) {
        throw new UnsupportedOperationException();
    }
    
    public CharacterNode modify(CharacterNode node) {
        AbilityNode abilityNode = (AbilityNode) node;
        Ability ability = abilityNode.ability;
        
        String name = "Debuff";
        
        StatusEffect debuff = new StatusEffect(name);
        
        Effect effect = new Effect() {
            @Override
            public float effect(EffectSource source, Object o) {
                if (o instanceof Creature)
                    ((Creature) o).addStatusEffect(debuff);
            }
        };
        
        if (ability instanceof MissileSpell) {
            AttackDefinition missile = ((MissileSpell) ability).missile;
        }
    }
    
    public boolean isApplicable(CharacterNode node) {
        throw new UnsupportedOperationException();
    }
}