
package rpeg.items.assets;

import rpeg.items.Consumable;
import rpeg.statusEffects.StatusEffect.DefenseBoost;

/**
 *
 * @author Adam Whittaker
 */
public class DefensePotion extends Consumable{

    public DefensePotion(){
        super(new DefenseBoost("defense potion", 2));
    }

}
