
package rpeg.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import static rpeg.factories.WeaponFactory.genWeapon;
import rpeg.items.Armor;
import rpeg.items.Consumable;
import rpeg.items.Key;
import rpeg.items.Weapon;
import rpeg.items.assets.DefensePotion;
import rpeg.items.assets.DexterityPotion;
import rpeg.items.assets.HealthPotion;
import rpeg.statusEffects.StatusEffect;

/**
 *
 * @author Adam Whittaker
 */
public class Avatar extends Entity{
    
    public double stealth = 6, maxHealth = 30;
    public final LinkedList<Consumable> consumables = new LinkedList<>();
    public final LinkedList<Key> keys = new LinkedList<>();
    
    public static final LinkedList<StatusEffect> statusEffects = new LinkedList<>();
    
    public final static String realName = System.getProperty("user.name");
    
    
    public Avatar(String n){
        super(n, Character.toUpperCase(n.charAt(0)), 30, genWeapon(0), new Armor(), 1, 1);
        consumables.add(new HealthPotion());
        consumables.add(new DefensePotion());
        consumables.add(new DexterityPotion());
    }

    
    public static void paintEffects(Graphics2D g, int x, int y){
        g.setColor(Color.YELLOW);
        g.drawString("CURRENT STATUS EFFECTS:", x, y);
        for(StatusEffect e : statusEffects){
            y += 2 + g.getFontMetrics().getHeight();
            g.drawString(e.name, x, y);
        }
    }
    
    public boolean hasKey(String keyName){
        Iterator<Key> iter = keys.iterator();
        while(iter.hasNext()){
            if(iter.next().name.equals(keyName)){
                iter.remove();
                return true;
            }
        }
        return false;
    }
    
}
