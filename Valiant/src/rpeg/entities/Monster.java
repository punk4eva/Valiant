
package rpeg.entities;

import static logic.Utils.R;
import static rpeg.RPG.TEXT;
import static rpeg.RPG.avatar;
import rpeg.factories.WeaponFactory;
import rpeg.items.Armor;
import rpeg.items.Item;
import rpeg.items.Weapon;
import rpeg.level.Room;

/**
 *
 * @author Adam Whittaker
 */
public class Monster extends Entity{
    
    public Item drop;
    public boolean asleep;
    public final double attentiveness;

    public Monster(String n, char c, double h, int we, int ar, double d, double a, double att){
        super(n, c, h, WeaponFactory.genWeapon(we), Armor.genArmor(ar), d, a);
        asleep = R.nextDouble()<0.5;
        attentiveness = att;
    }
    
    public Monster(String n, char c, double h, Weapon w, Armor ar, double d, double a, double att){
        super(n, c, h, w, ar, d, a);
        asleep = R.nextDouble()<0.5;
        attentiveness = att;
    }
    

    public void sleepCheck(double advantage){
        double dist = Math.min(Math.abs(avatar.x-x), Math.abs(avatar.y-y));
        if(R.nextDouble()*avatar.stealth*advantage<R.nextDouble()*attentiveness*(1D-dist/5D)){
            asleep = false;
            TEXT.addState(avatar.name + " has woken up " + name + ".");
        }
    }

    public void turn(Room r){
        if(Room.nextToAvatar(x, y)){
            attack(avatar);
        }else{
            if(Math.abs(avatar.x-x)>Math.abs(avatar.y-y)){
                if(avatar.x>x) attemptMove(r, 1, 0);
                else attemptMove(r, -1, 0);
            }else{
                if(avatar.y>y) attemptMove(r, 0, 1);
                else attemptMove(r, 0, -1);
            }
        }
    }
    
    private void attemptMove(Room r, int dx, int dy){
        if(r.getMonster(x+dx, y+dy)==null){
            x += dx;
            y += dy;
        }
    }
    
}
