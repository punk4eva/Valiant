
package rpeg.items.assets;

import rpeg.items.Consumable;
import rpeg.statusEffects.PermanentStatusEffect.LevelUpEffect;

/**
 *
 * @author Adam Whittaker
 */
public class LevelUpScroll extends Consumable{

    public LevelUpScroll(){
        super(new LevelUpEffect());
    }

}
