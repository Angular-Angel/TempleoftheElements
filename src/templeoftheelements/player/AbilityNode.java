
package templeoftheelements.player;

/**
 *
 * @author angle
 */


public class AbilityNode extends CharacterNode {

    public final Ability ability;
    
    public AbilityNode(Requirement requirements, CharacterWheel.CharacterTree tree, boolean automatic, Ability ability) {
        super(requirements, tree, automatic);
        this.ability = ability;
    }
    
    public AbilityNode(Requirement requirements, CharacterWheel.CharacterTree tree, Ability ability) {
        this(requirements, tree, false, ability);
    }
    
    public void acquire() {
        super.acquire();
        tree.getPlayer().addAbility(ability);
    }
    
}
