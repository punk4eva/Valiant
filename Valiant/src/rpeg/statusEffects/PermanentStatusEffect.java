
package rpeg.statusEffects;

import static rpeg.RPG.TEXT;
import static rpeg.RPG.avatar;
import static rpeg.RPG.endGame;
import rpeg.entities.Avatar;
import rpeg.entities.Entity;

/**
 *
 * @author Adam Whittaker
 */
public class PermanentStatusEffect extends StatusEffect{
    
    public String text;

    public PermanentStatusEffect(String n, String t, double dam, double def, double dod, double acc, double hp){
        super(n, dam, def, dod, acc, hp);
        text = t;
    }
    
    @Override
    public void apply(Entity e){
        TEXT.addState(text);
        super.apply(e);
    }
    
    @Override
    public void unapply(Entity e){}
    
    
    public static class DexterityLevelUp extends PermanentStatusEffect{
        
        public DexterityLevelUp(String n, double dod, double acc){
            super(n, avatar.name + "'s dexterity is increasing.", 0, 0, dod, acc, 0);
        }
        
    }
    
    public static class StealthLevelUp extends PermanentStatusEffect{
        
        double stealth;
        
        public StealthLevelUp(double d){
            super("ectoplasm", avatar.name + " has become harder to notice.", 0, 0, 0, 0, 0);
            stealth = d;
        }
        
        @Override
        public void apply(Entity e){
            TEXT.addState(text);
            ((Avatar) e).stealth += stealth;
        }
        
    }
    
    public static class LevelUpEffect extends PermanentStatusEffect{
    
        public LevelUpEffect(){
            super("scroll of mind honing", avatar.name + " has become more focused. Their health, damage, and dexterity have increased.", 0.5, 0.5, 0.05, 0.05, 5);
        }
        
        @Override
        public void apply(Entity e){
            super.apply(e);
            TEXT.addAI("Wow, you leveled up.");
            avatar.maxHealth += 5;
        }
    
    }
    
    public static class WinEffect extends PermanentStatusEffect{
        
        public WinEffect(){
            super("Error", avatar.name + " has obtained the amulet of life and death.", 0, 0, 0, 0, 0);
        }
        
        @Override
        public void apply(Entity e){
            TEXT.addState(text);
            endGame();
        }
        
    }

}
