
package rpeg.level;

import rpeg.puzzles.Puzzle;
import java.util.LinkedList;
import logic.Utils;
import static logic.Utils.R;
import static rpeg.RPG.avatar;
import rpeg.entities.Monster;
import static rpeg.factories.MonsterFactory.vampire;
import static rpeg.factories.MonsterFactory.vampireSlave;
import rpeg.items.Item;
import static rpeg.procgen.DescriptionBuilder.*;

/**
 *
 * @author Adam Whittaker
 */
public class Room{    
    
    public final int width, height;
    public boolean locked = false, visited = false;
    public Boolean depthExitLocked = null; //null if no depth exit.
    public String description;
    public final LinkedList<Room> connections = new LinkedList<>();
    
    public final LinkedList<Monster> monsters;
    public final Puzzle puzzle;
    public final LinkedList<Item> items = new LinkedList<>();
    
    public Room(String desc, int level, LinkedList<Monster> m){
        width = R.nextInt(8)+3;
        height = R.nextInt(8)+3;
        description = desc;
        monsters = m;
        puzzle = Puzzle.genPuzzle(this, level);
        Item.addRandomItems(items);
    }
    
    public Room(){
        description = getVampireBossDescription();
        width = 7;
        height = 14;
        monsters = new LinkedList<>();
        Monster vB = vampire(), v1 = vampireSlave(), v2 = vampireSlave(), 
                v3 = vampireSlave(), v4 = vampireSlave();
        vB.x = 3;
        vB.y = 3;
        v1.x = 2;
        v1.y = 2;
        v2.x = 4;
        v2.y = 4;
        v3.x = 4;
        v3.y = 2;
        v4.x = 2;
        v4.y = 4;
        v1.asleep = false;
        v2.asleep = false;
        v3.asleep = false;
        v4.asleep = false;
        monsters.add(vB);
        monsters.add(v1);
        monsters.add(v2);
        monsters.add(v3);
        monsters.add(v4);
        puzzle = null;
    }
    

    public void prepareForBattle(){
        LinkedList<int[]> coords = new LinkedList<>();
        for(Monster m : monsters){
            if(m.x==-1){
                do{
                    m.x = R.nextInt(width);
                    m.y = R.nextInt(height);
                }while(Utils.listContainsArray(coords, new int[]{m.x, m.y}));
                coords.add(new int[]{m.x, m.y});
            }
        }
        do{
            if(R.nextDouble()<0.5){
                avatar.x = R.nextDouble()<0.5 ? 0 : width-1;
                avatar.y = R.nextInt(height);
            }else{
                avatar.y = R.nextDouble()<0.5 ? 0 : height-1;
                avatar.x = R.nextInt(width);
            }
        }while(Utils.listContainsArray(coords, new int[]{avatar.x, avatar.y}));
    }

    public Monster getMonster(int x, int y){
        for(Monster m : monsters){
            if(m.x==x && m.y==y) return m;
        }
        return null;
    }

    public boolean withinBounds(int x, int y){
        return x>=0 && y>=0 && x<width && y<height;
    }
    
    public static boolean nextToAvatar(int x, int y){
        return Math.max(Math.abs(x-avatar.x), Math.abs(y-avatar.y))==1;
    }
    
}
