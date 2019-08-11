
package rpeg.items.assets;

import rpeg.items.Consumable;
import rpeg.statusEffects.StatusEffect.DexterityBoost;

/**
 *
 * @author Adam Whittaker
 */
public class DexterityPotion extends Consumable{

    public DexterityPotion(){
        super(new DexterityBoost("dexterity potion", 0.2, 0.2));
    }

}
