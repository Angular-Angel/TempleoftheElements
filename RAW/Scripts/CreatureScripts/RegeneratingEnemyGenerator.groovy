
import templeoftheelements.CreatureGenerationProcedure;
import templeoftheelements.CreatureDefinition;
import templeoftheelements.collision.Creature;
import templeoftheelements.player.Ability;
import templeoftheelements.player.PassiveAbility;
import templeoftheelements.player.characterwheel.CharacterTreeDef.AbilityDefinition;

/**
 *
 * @author angle
 */
class RegeneratingEnemyGenerator extends CreatureGenerationProcedure {
    
    public CreatureDefinition modify(CreatureDefinition definition) {
        //This script makes an enemy stronger.
        
        PassiveAbility regeneration = new PassiveAbility() {
            
            Creature creature;
            
            public String getDescription() {
                return "This ability heals it's owner very quickly.";
            }

            public Ability copy() {
                return this.getClass().newInstance();
            }

            public AbilityDefinition getDef() {
                throw new UnsupportedOperationException();
            }

            public void setDef(AbilityDefinition abilityDef) {
                throw new UnsupportedOperationException();
            }

            public void init(Creature c) {
                creature = c;
            }
            
            public void step(float dt) {
                if (creature.getScore("HP") < creature.getScore("Max HP")) {
                    creature.getStat("HP").modifyBase(dt);
                    System.out.println(creature.getScore("HP"));
                }
            }
        }
        
        definition.addAbility(regeneration);
        
        return definition;
    }
    
}

