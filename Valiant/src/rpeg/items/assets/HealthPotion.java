
package rpeg.items.assets;

import rpeg.items.Consumable;
import rpeg.statusEffects.StatusEffect.HealthBoost;

/**
 *
 * @author Adam Whittaker
 */
public class HealthPotion extends Consumable{

    public HealthPotion(){
        super(new HealthBoost("health potion", 30));
    }

}
