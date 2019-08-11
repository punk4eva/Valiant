
package rpeg.items.assets;

import rpeg.items.Consumable;
import rpeg.statusEffects.StatusEffect.FightingBoost;

/**
 *
 * @author Adam Whittaker
 */
public class ScrollOfBattle extends Consumable{

    public ScrollOfBattle(double dam, double def, double hp){
        super(new FightingBoost("scroll of battle", dam, def, hp));
    }

}
