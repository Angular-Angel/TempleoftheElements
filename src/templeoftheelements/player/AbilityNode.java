
package templeoftheelements.player;

/**
 *
 * @author angle
 */


public class AbilityNode extends CharacterNode {

    private final Object ability;
    
    public AbilityNode(Requirement requirements, CharacterWheel.CharacterTree tree, boolean automatic, Object ability) {
        super(requirements, tree, automatic);
        this.ability = ability;
    }
    
    public void acquire() {
        super.acquire();
        tree.getPlayer().addAbility(ability);
    }
    
}
