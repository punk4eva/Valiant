
package rpeg.items.assets;

import rpeg.items.Amulet;
import rpeg.statusEffects.PermanentStatusEffect.DexterityLevelUp;

/**
 *
 * @author Adam Whittaker
 */
public class YendorAmulet extends Amulet{
    
    public YendorAmulet(double d, double a){
        super("amulet of yendor", new DexterityLevelUp("amulet of yendor", d, a));
    }
    
}
