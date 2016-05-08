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
import templeoftheelements.player.CharacterTreeDef.AbilityDefinition;
import templeoftheelements.player.CharacterTreeDef.NodeDefinition;
import templeoftheelements.player.CharacterTreeDef.ClusterDefinition;
import org.jbox2d.common.Vec2;

public class AreaSpellGenerator implements GenerationProcedure<CharacterNode> {

     //our random number generator;
    Random random = new Random();

    public CharacterNode generate() {
        throw new UnsupportedOperationException();
    }

    public CharacterNode generate(Object o) {
        NodeDefinition nodeDef = (NodeDefinition) o; //the definition for the node we're making
        CharacterWheel.CharacterTree tree = (CharacterWheel.CharacterTree) nodeDef.tree; //the tree to which the node will belong
        
        //Create the effect that this spell will have.
        Effect effect = new Effect() {

            @Override
            public float effect(EffectSource source, Object obj) {
                Vec2 pos = (Vec2) obj;
                AreaEffect areaEffect = new AreaEffect((Creature) source, 200, 1, pos);
                areaEffect.ongoingEffects.add(new Effect() {

                    @Override
                    public float effect(EffectSource src, Object object) {
                        if (object instanceof Creature)
                        ((Creature) object).takeDamage(1, "Crushing");
                        return 0;
                    }
                });
                game.addActor(areaEffect);
                return 0;
            }
        }
        
        AreaSpell spell = new AreaSpell("Area Spell", new VectorCircle(0.5), effect);
        
        spell.addStat("Mana Cost", new NumericStat(1));
        
        CharacterNode node = new AbilityNode(null, tree, spell);

        for (StatDescriptor stat : nodeDef.stats) {
            node.addStat(stat.name, new NumericStat(stat.increase));
        }

        node.nodeDef = nodeDef;
        return node;
    }
    
    public CharacterNode modify(CharacterNode node) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isApplicable(CharacterNode node) {
        throw new UnsupportedOperationException();
    }
}