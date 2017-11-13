
package templeoftheelements.player;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;
import stat.NoSuchStatException;
import static templeoftheelements.TempleOfTheElements.game;
import templeoftheelements.display.CharacterScreen;
import templeoftheelements.creature.Ability;



public class SkillNode extends CharacterNode {

    public final Skill skill;
    
    public SkillNode(Requirement requirements, CharacterTree tree, boolean automatic, Skill skill) {
        super(requirements, tree, automatic);
        this.skill = skill;
    }
    
    public SkillNode(Requirement requirements, CharacterTree tree, Skill skill) {
        this(requirements, tree, false, skill);
    }
    
    @Override
    public void acquire() {
        super.acquire();
        skill.beAcquired(tree.getCreature());
        tree.getCreature().getController().refactorActions();
    }
    
    @Override
    public void showDescription(CharacterScreen.StatScreen screen) {
        float i = screen.height - 12;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor3f(255, 0, 0);
        game.font.getTexture().bind();i -= 20;
        game.font.draw(skill.getName(), new com.samrj.devil.math.Vec2(screen.x +2, screen.y + i));
        String[] split = skill.getDescription().split("\n");
        for (String s : split) {
            i -= 20;
            game.font.draw(s, new com.samrj.devil.math.Vec2(screen.x +2, screen.y + i));
//            screen.height += 20;
        }
        
//        if(ability instanceof MissileSpell) {
//            Attack attack = ((MissileSpell) ability).getMissile().generate(game.player.getCreature());
//            for (String s : attack.getStatList()) {
//                try {
//                    i -= 20;
//                    game.font.draw(s + ": " + attack.getScore(s), new com.samrj.devil.math.Vec2(screen.x +2, screen.y + i));
//                } catch (NoSuchStatException ex) {
//                    Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
        
        for (String s : getStatList()) {
            try {
                i -= 20;
                game.font.draw(s + ": " + getScore(s), new com.samrj.devil.math.Vec2(screen.x +2, screen.y + i));
            } catch (NoSuchStatException ex) {
                Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
