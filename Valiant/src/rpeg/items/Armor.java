
package rpeg.items;

import static logic.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Armor extends Item{

    public double maxDefense;

    public Armor(String n, double maxDef){
        super(n);
        maxDefense = maxDef;
        maxDefense += (R.nextDouble()*0.2 - 0.1)*maxDefense;
    }
    
    public Armor(){
        super("casual shirt");
        maxDefense = 0;
    }
    
    public static Armor genArmor(int tier){
        switch(tier){
            case 0: return new Armor();
            case 1: return new Armor("gambeson", 1.5);
            case 2: return new Armor("hardened leather jerkin", 3);
            case 3: return new Armor("mail tunic", 4.5);
            case 4: return new Armor("plate armor", 6);
            case 5: return new Armor("divine chestplate", 7.5);
        }
        throw new IllegalStateException();
    }

}
