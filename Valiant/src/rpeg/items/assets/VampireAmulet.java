
package rpeg.items.assets;

import rpeg.items.Amulet;
import rpeg.statusEffects.PermanentStatusEffect.WinEffect;

/**
 *
 * @author Adam Whittaker
 */
public class VampireAmulet extends Amulet{

    public VampireAmulet(){
        super("vampire heart", new WinEffect());
    }

}
