
package rpeg.factories;

import static logic.Utils.R;
import rpeg.items.Weapon;
import rpeg.statusEffects.StatusEffect;

/**
 *
 * @author Adam Whittaker
 */
public class WeaponFactory{
    
     public static Weapon genWeapon(int level){
        switch(level){
            case 0: return new Weapon("bare hands", 2.5, false);
            case 1: return getLv1Weapon();
            case 2: return getLv2Weapon();
            case 3: return getLv3Weapon();
            case 4: return getLv4Weapon();
            case 5: return getLv5Weapon();
        }
        throw new IllegalStateException();
    }
    
    private static Weapon getLv1Weapon(){
        switch(R.nextInt(3)){
            case 0: return shiv();
            case 1: return dagger();
            case 2: return nunchuck();
        }
        throw new IllegalStateException();
    }
    
    private static Weapon getLv2Weapon(){
        switch(R.nextInt(3)){
            case 0: return mace();
            case 1: return quarterstaff();
            case 2: return lichBlade();
        }
        throw new IllegalStateException();
    }
    
    private static Weapon getLv3Weapon(){
        switch(R.nextInt(3)){
            case 0: return sword();
            case 1: return spear();
            case 2: return poisonAxe();
        }
        throw new IllegalStateException();
    }
    
    private static Weapon getLv4Weapon(){
        switch(R.nextInt(3)){
            case 0: return enchantedGodentag();
            case 1: return warhammer();
            case 2: return katana();
        }
        throw new IllegalStateException();
    }
    
    private static Weapon getLv5Weapon(){
        switch(R.nextInt(3)){
            case 0: return kusarigama();
            case 1: return scythe();
            case 2: return greatsword();
        }
        throw new IllegalStateException();
    }
    
    
    public static Weapon slimeBite(){
        return new Weapon("swallow", 3, false);
    }
    
    public static Weapon houndBite(){
        return new Weapon("bite", 4, false);
    }
    
    public static Weapon batBite(){
        return new Weapon("suck", 5, false);
    }
    
    public static Weapon visionHydraBite(){
        return new Weapon("chomp", 7, false);
    }
    
    public static Weapon hearingHydraBite(){
        return new Weapon("bite", 4, false, 0.22, new StatusEffect.DefenseBoost("hydra's kiss", -0.5));
    }
    
    public static Weapon touchHydraBite(){
        return new Weapon("venom", 2, false, 0.6, new StatusEffect.DexterityBoost("hydra's touch", -0.1, -0.05));
    }
    
    public static Weapon oakenWormBite(){
        return new Weapon("smash", 7, false, 0.35, new StatusEffect.FightingBoost("oaken ooze", -1, -0.5, -1.7));
    }
    
    public static Weapon dragonBite(){
        return new Weapon("talons", 13, false, 0.4, new StatusEffect.FightingBoost("dragon fire", 0, -0.25, -2.5));
    }
    
    public static Weapon ghostBite(){
        return new Weapon("touch", 4, false, 1, new StatusEffect.HealthBoost("ghost caress", -5));
    }
    
    
    public static Weapon shiv(){
        return new Weapon("shiv", 4, true);
    }
    
    public static Weapon dagger(){
        return new Weapon("dagger", 5, true);
    }
    
    public static Weapon nunchuck(){
        return new Weapon("nunchuck", 3.5, true, 0.2, new StatusEffect.HealthBoost("nunchuck attack", -1));
    }
    
    
    public static Weapon mace(){
        return new Weapon("mace", 6, true, 0.2, new StatusEffect.FightingBoost("armor damage", 0, -0.5, 0));
    }
    
    public static Weapon quarterstaff(){
        return new Weapon("quarterstaff", 6.5, true);
    }
    
    public static Weapon lichBlade(){
        return new Weapon("lich blade", 5.7, true, 0.35, new StatusEffect.DexterityBoost("lich's cast", -0.15, -0.2));
    }
    
    
    public static Weapon sword(){
        return new Weapon("sword", 9, true);
    }
    
    public static Weapon spear(){
        return new Weapon("spear", 8, true, 0.1, new StatusEffect.FightingBoost("spear critical strike", 0, -0.3, -4));
    }
    
    public static Weapon poisonAxe(){
        return new Weapon("poisoned axe", 7, true, 0.4, new StatusEffect.DexterityBoost("watcher's poison", -0.15, -0.1));
    }
    
    
    public static Weapon enchantedGodentag(){
        return new Weapon("enchanted godentag", 11, true, 0.3, new StatusEffect.FightingBoost("weakness", -0.7, 0, -1));
    }
    
    public static Weapon warhammer(){
        return new Weapon("warhammer of energy", 14, true, 0.23, new StatusEffect.DexterityBoost("energized", 0.2, 0.15));
    }
    
    public static Weapon katana(){
        return new Weapon("katana", 11.5, true, 0.2, new StatusEffect.DexterityBoost("unbalanced", -0.3, -0.07));
    }
    
    
    public static Weapon kusarigama(){
        return new Weapon("kusarigama", 10, true, 0.6, new StatusEffect.DexterityBoost("tied up", -0.1, -0.3));
    }
    
    public static Weapon scythe(){
        return new Weapon("vampiric scythe", 14, true, 0.2, new StatusEffect.FightingBoost("bloodloss", -0.1, -0.7, 0));
    }
    
    public static Weapon greatsword(){
        return new Weapon("nightfall greatsword", 15.5, true, 0.15, new StatusEffect.FightingBoost("cleaved", -0.8, -0.1, 0));
    }

}
