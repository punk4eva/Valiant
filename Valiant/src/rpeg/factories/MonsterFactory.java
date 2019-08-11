
package rpeg.factories;

import logic.Distribution;
import rpeg.entities.Monster;
import static rpeg.factories.WeaponFactory.*;
import rpeg.items.Amulet;
import rpeg.items.assets.LevelKey;
import rpeg.items.assets.VampireAmulet;
import rpeg.statusEffects.PermanentStatusEffect;

/**
 *
 * @author Adam Whittaker
 */
public class MonsterFactory{
    
    private static Distribution LV1_DIST = new Distribution(new int[]{2,1,1,2,2}),
            LV2_DIST = new Distribution(new int[]{2,3,3,2,2,2}),
            LV3_DIST = new Distribution(new int[]{1,3,2,1,2,2}),
            LV4_DIST = new Distribution(new int[]{2,1,3,3,3}),
            LV5_DIST = new Distribution(new int[]{2,2,3,2,1});
    

    public static Monster terror(){
        return new Monster("terror of the deep", 't', 5, 0, 0, 0.8, 1.2, 15);
    }
    
    public static Monster slime(){
        return new Monster("slime", 's', 7, slimeBite(), null, 0.6, 1, 7);
    }
    
    public static Monster hound(){
        return new Monster("hound", 'h', 12, houndBite(), null, 1.2, 1.2, 12);
    }
    
    public static Monster corpse(){
        return new Monster("corpse", 'c', 10, 1, 0, 0, 0.8, 5);
    }
    
    public static Monster bat(){
        return new Monster("evil bat", 'b', 8, batBite(), null, 1.4, 1.4, 10);
    }
    
    public static Monster undeadKnight(){
        return new Monster("undead knight", 'u', 18, 2, 2, 0.1, 1.1, 7);
    }
    
    public static Monster watcher(){
        Monster m = new Monster("watcher", 'w', 15, 3, 1, 0.8, 1, 20);
        m.asleep = false;
        return m;
    }
    
    public static Monster golem(){
        Monster g = new Monster("golem", 'G', 30, 3, 2, 0, 1, 15);
        g.asleep = false;
        g.drop = new LevelKey();
        return g;
    }
    
    public static Monster demon(){
        return new Monster("demon", 'd', 22, 3, 2, 1, 1, 12);
    }
    
    public static Monster visionHydra(){ //attack
        Monster m = new Monster("hydra of vision", 'H', 45, visionHydraBite(), null, 1.1, 1.1, 20);
        m.asleep = false;
        m.drop = new LevelKey();
        return m;
    }
    
    public static Monster touchHydra(){ //status effect backup
        Monster m = new Monster("hydra of touch", 'H', 30, touchHydraBite(), null, 0.9, 1, 20);
        m.asleep = false;
        return m;
    }
    
    public static Monster hearingHydra(){ //evasive diversion
        Monster m = new Monster("hydra of hearing", 'H', 35, hearingHydraBite(), null, 1.8, 1, 20);
        m.asleep = false;
        return m;
    }
    
    public static Monster occultProtector(){
        return new Monster("occult protector", 'o', 26, 3, 3, 1, 1, 13);
    }
    
    public static Monster vampireSlave(){
        return new Monster("vampire slave", 'v', 28, 3, 3, 1.2, 1.05, 14);
    }
    
    public static Monster corruptor(){
        Monster m = new Monster("corruptor", 'C', 55, 4, 4, 0.7, 0.6, 13);
        m.asleep = false;
        return m;
    }
    
    public static Monster oakenWorm(){
        Monster m = new Monster("oaken worm", 'W', 70, oakenWormBite(), null, 0, 1.6, 10);
        m.asleep = true;
        return m;
    }
    
    public static Monster dragon(){
        return new Monster("dragon", 'D', 50, dragonBite(), null, 1, 1.2, 15);
    }
    
    public static Monster vampire(){
        Monster m = new Monster("vampire overlord", 'V', 100, 5, 4, 1.1, 1.1, 40);
        m.asleep = false;
        m.drop = new VampireAmulet();
        return m;
    }
    
    public static Monster statue(boolean guardian, int level){
        Monster m = new Monster(guardian ? "stone ogre" : "animated statue", 'S', 5+7*level, getRarityNum(level), getRarityNum(level), 0.9, 0.95+0.05*level, 0);
        m.asleep = false;
        return m;
    }
    
    private static int getRarityNum(int level){
        switch(level){
            case 1: return 1 + new Distribution(new int[]{5,4,3,2,1}).next();
            case 2: return 1 + new Distribution(new int[]{4,5,3,2,1}).next();
            case 3: return 1 + new Distribution(new int[]{2,3,5,3,1}).next();
            case 4: return 1 + new Distribution(new int[]{1,2,4,5,2}).next();
            default: return 1 + new Distribution(new int[]{1,2,3,5,3}).next();
        }
    }
    
    public static Monster ghost(){
        Monster g = new Monster("ghost", 'g', 1, ghostBite(), null, 7, 1, 4);
        g.drop = new Amulet("ectoplasm", new PermanentStatusEffect.StealthLevelUp(1.2));
        g.asleep = false;
        return g;
    }
    
    
    public static Monster genRandomMonster(int level){
        switch(level){
            case 1: return genLv1Monster();
            case 2: return genLv2Monster();
            case 3: return genLv3Monster();
            case 4: return genLv4Monster();
            case 5: return genLv5Monster();
        }
        throw new IllegalStateException();
    }
    
    private static Monster genLv1Monster(){
        switch(LV1_DIST.next()){
            case 0: return corpse();
            case 1: return bat();
            case 2: return hound();
            case 3: return slime();
            case 4: return terror();
        }
        throw new IllegalStateException();
    }
    
    private static Monster genLv2Monster(){
        switch(LV2_DIST.next()){
            case 0: return corpse();
            case 1: return bat();
            case 2: return hound();
            case 3: return slime();
            case 4: return undeadKnight();
            case 5: return watcher();
        }
        throw new IllegalStateException();
    }
    
    private static Monster genLv3Monster(){
        switch(LV3_DIST.next()){
            case 0: return corpse();
            case 1: return undeadKnight();
            case 2: return watcher();
            case 3: return bat();
            case 4: return demon();
            case 5: return occultProtector();
        }
        throw new IllegalStateException();
    }
    
    private static Monster genLv4Monster(){
        switch(LV4_DIST.next()){
            case 0: return vampireSlave();
            case 1: return corruptor();
            case 2: return watcher();
            case 3: return demon();
            case 4: return occultProtector();
        }
        throw new IllegalStateException();
    }
    
    private static Monster genLv5Monster(){
        switch(LV5_DIST.next()){
            case 0: return demon();
            case 1: return dragon();
            case 2: return vampireSlave();
            case 3: return corruptor();
            case 4: return occultProtector();
        }
        throw new IllegalStateException();
    }
    
}
