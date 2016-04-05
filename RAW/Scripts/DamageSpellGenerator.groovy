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
import org.jbox2d.common.Vec2;

public class DamageSpellGenerator implements GenerationProcedure<CharacterNode> {

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
        
        Spell spell = (Spell) node.ability;
        
        Effect e = new Effect() {

            @Override
            public float effect(EffectSource src, Object object) {
                if (!(object instanceof Creature && src instanceof Creature)) return 0;
                
                Creature source = (Creature) src;
                
                float damage = source.getStat("Spirit") / 5; 
                
                return ((Creature) object).takeDamage(damage, "Fire");
            }
        };
        
        spell.addEffect(e)
        
        return node;
    }
    
    public boolean isApplicable(CharacterNode node) {
        if (!(node instanceof AbilityNode)) return false;
        AbilityNode abilityNode = (AbilityNode) node;
        
        return (node.ability instanceof Spell);
    }
}