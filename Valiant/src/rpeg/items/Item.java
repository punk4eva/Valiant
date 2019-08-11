
package rpeg.items;

import java.util.LinkedList;
import logic.Distribution;
import static logic.Utils.R;
import static rpeg.factories.WeaponFactory.genWeapon;
import static rpeg.items.Armor.genArmor;
import rpeg.items.assets.*;

/**
 *
 * @author Adam Whittaker
 */
public class Item{
    
    private static final Distribution NUM_DIST = new Distribution(new int[]{5,4,2,1});
    private static final Distribution ITEM_DIST = new Distribution(new int[]{12,10,8,4,3,2,1,4,3,2,1,9,2,2});

    public String name;
    
    public Item(String n){
        name = n;
    }
    
    public static void addRandomItems(LinkedList<Item> items){
        int num = NUM_DIST.next();
        for(int n=0;n<num;n++){
            items.add(genRandomItem());
        }
    }
    
    private static Item genRandomItem(){
        switch(ITEM_DIST.next()){
            case 0: return new HealthPotion();
            case 1: return new DefensePotion();
            case 2: return new DexterityPotion();
            case 3: return genWeapon(1);
            case 4: return genWeapon(2);
            case 5: return genWeapon(3);
            case 6: return genWeapon(4);
            case 7: return genArmor(1);
            case 8: return genArmor(2);
            case 9: return genArmor(3);
            case 10: return genArmor(4);
            case 11: return new LevelUpScroll();
            case 12: return new ScrollOfBattle(0.1+0.4*R.nextDouble(), 0.1+0.4*R.nextDouble(), 5);
            case 13: return new YendorAmulet(0.02+0.08*R.nextDouble(), 0.02+0.08*R.nextDouble());
        }
        throw new IllegalStateException();
    }
    
}
