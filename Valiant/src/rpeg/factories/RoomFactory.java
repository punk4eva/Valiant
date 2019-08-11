
package rpeg.factories;

import java.util.LinkedList;
import logic.Distribution;
import static logic.Utils.R;
import rpeg.entities.Monster;
import static rpeg.factories.MonsterFactory.*;
import rpeg.level.Room;
import static rpeg.procgen.DescriptionBuilder.*;

/**
 *
 * @author Adam Whittaker
 */
public class RoomFactory{
    
    private final static Distribution NUMBER_DISTRIBUTION = new Distribution(new int[]{2,2,3,3,2,1});
    
    private final static Distribution lv1Dist = new Distribution(new int[]{3,1,1,1}),
            lv2Dist = new Distribution(new int[]{5,1,1,2,3}), 
            lv3Dist = new Distribution(new int[]{5,1,3,1,3}), 
            lv4Dist = new Distribution(new int[]{3,1,1,2}), 
            lv5Dist = new Distribution(new int[]{4,1,2,1,3});
    
    
    public static Room genRoom(int level){
        switch(level){
            case 1: return genLv1Room();
            case 2: return genLv2Room();
            case 3: return genLv3Room();
            case 4: return genLv4Room();
            case 5: return genLv5Room();
        }
        throw new IllegalStateException();
    }
    
    private static Room misc(int level){
        LinkedList<Monster> monsters = new LinkedList<>();
        int creatureNum = NUMBER_DISTRIBUTION.next();
        for(int n=0;n<creatureNum;n++) 
            monsters.add(genRandomMonster(level));
        return new Room(getMiscRoomDescription(level), level, monsters);
    }
    
    private static Room massGrave(double level){
        LinkedList<Monster> monsters = new LinkedList<>();
        int creatureNum = NUMBER_DISTRIBUTION.next();
        for(int n=0;n<creatureNum;n++) 
            monsters.add(R.nextDouble()<(level-1D)/3D ? undeadKnight() : corpse());
        String desc = getMassGraveDescription((int)level);
        return new Room(desc, (int)level, monsters);
    }
    
    private static Room uwuRoom(int level){
        Room r = new Room(getUwuDescription(level), level, new LinkedList<>());
        int x = r.width/2, y = r.height/2;
        Monster u1 = undeadKnight(), u2 = undeadKnight(), w = watcher();
        u1.x = x-1;
        u1.y = y;
        u2.x = x+1;
        u2.y = y;
        w.x = x;
        w.y = y;
        r.monsters.add(u1);
        r.monsters.add(w);
        r.monsters.add(u2);
        return r;
    }
    
    public static Room golemRoom(){
        Room r = new Room(getGolemRoomDescription(), 2, new LinkedList<>());
        Monster g = golem();
        g.x = r.width/2;
        g.y = r.height/2;
        r.monsters.add(g);
        return r;
    }
    
    private static Room batCave(int level){
        LinkedList<Monster> monsters = new LinkedList<>();
        int creatureNum = NUMBER_DISTRIBUTION.next();
        for(int n=0;n<creatureNum;n++) 
            monsters.add(R.nextDouble()<0.75 ? bat() : genRandomMonster(level));
        String desc = getBatCaveDescription(level);
        return new Room(desc, level, monsters);
    }
    
    private static Room slimeCave(int level){
        LinkedList<Monster> monsters = new LinkedList<>();
        int creatureNum = NUMBER_DISTRIBUTION.next();
        for(int n=0;n<creatureNum;n++) 
            monsters.add(R.nextDouble()<0.75 ? slime() : hound());
        String desc = getSlimeCaveDescription(level);
        return new Room(desc, level, monsters);
    }
    
    private static Room demonPit(int level){
        Room r = new Room(getDemonPitDescription(level), level, new LinkedList<>());
        int creatureNum = NUMBER_DISTRIBUTION.next();
        for(int n=1;n<creatureNum;n++) 
            r.monsters.add(occultProtector());
        Monster d = demon();
        d.x = r.width/2;
        d.y = r.height/2;
        r.monsters.add(d);
        return r;
    }
    
    public static Room hydraPit(){
        Room r = new Room(getHydraRoomDescription(), 3, new LinkedList<>());
        int x = r.width/2, y = r.height/2;
        Monster v = visionHydra(), h = hearingHydra(), t = touchHydra();
        v.x = x;
        v.y = y+1;
        h.x = x+1;
        h.y = y-1;
        t.x = x-1;
        t.y = y-1;
        r.monsters.add(v);
        r.monsters.add(h);
        r.monsters.add(t);
        return r;
    }
    
    private static Room corruptorRoom(int level){
        Room r = new Room(getCorruptorRoomDescription(level), level, new LinkedList<>());
        Monster g = corruptor();
        g.x = r.width/2;
        g.y = r.height/2;
        return r;
    }
    
    private static Room dragonPit(){
        Room r = new Room(getDragonPitDescription(), 5, new LinkedList<>());
        Monster g = dragon();
        g.x = r.width/2;
        g.y = r.height/2;
        return r;
    }
    
    private static Room owoRoom(int level){
        Room r = new Room(getOwoDescription(level), level, new LinkedList<>());
        int x = r.width/2, y = r.height/2;
        Monster u1 = occultProtector(), w = oakenWorm(), u2 = occultProtector();
        u1.x = x-1;
        u1.y = y;
        u2.x = x+1;
        u2.y = y;
        w.x = x;
        w.y = y;
        r.monsters.add(u1);
        r.monsters.add(w);
        r.monsters.add(u2);
        return r;
    }
    
    private static Room genLv1Room(){
        switch(lv1Dist.next()){
            case 0: return misc(1);
            case 1: return massGrave(1);
            case 2: return slimeCave(1);
            case 3: return batCave(1);
        }
        throw new IllegalStateException();
    }
    
    private static Room genLv2Room(){
        switch(lv2Dist.next()){
            case 0: return misc(2);
            case 1: return massGrave(2);
            case 2: return slimeCave(2);
            case 3: return batCave(2);
            case 4: return uwuRoom(2);
        }
        throw new IllegalStateException();
    }
    
    private static Room genLv3Room(){
        switch(lv3Dist.next()){
            case 0: return misc(3);
            case 1: return massGrave(3);
            case 2: return demonPit(3);
            case 3: return batCave(3);
            case 4: return uwuRoom(3);
        }
        throw new IllegalStateException();
    }
    
    private static Room genLv4Room(){
        switch(lv4Dist.next()){
            case 0: return misc(4);
            case 1: return corruptorRoom(4);
            case 2: return demonPit(4);
            case 3: return owoRoom(4);
        }
        throw new IllegalStateException();
    }
    
    private static Room genLv5Room(){
        switch(lv5Dist.next()){
            case 0: return misc(5);
            case 1: return corruptorRoom(5);
            case 2: return demonPit(5);
            case 3: return owoRoom(5);
            case 4: return dragonPit();
        }
        throw new IllegalStateException();
    }

}
