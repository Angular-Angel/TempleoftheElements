
package templeoftheelements.player;

/**
 *
 * @author angle
 */


public interface CharacterTreeGenerator {
    
    public CharacterTreeDef genCharacterTreeDef();
    
    public CharacterNode generateNode(CharacterWheel.CharacterTree tree);
    
}
