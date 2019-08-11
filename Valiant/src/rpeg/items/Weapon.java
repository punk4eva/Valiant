
package rpeg.items;

import static logic.Utils.R;
import rpeg.statusEffects.StatusEffect.*;
import rpeg.statusEffects.StatusEffect;

/**
 *
 * @author Adam Whittaker
 */
public class Weapon extends Item{

    public double maxDamage;
    public final double effectChance;
    public final StatusEffect effect;
    public final boolean drops;
    
    public Weapon(String n, double maxD, boolean d){
        super(n);
        maxDamage = maxD;
        effectChance = 0;
        drops = d;
        effect = null;
    }
    
    public Weapon(String n, double maxD, boolean d, double effC, StatusEffect e){
        super(n);
        maxDamage = maxD;
        effectChance = effC;
        drops = d;
        effect = e;
    }

}
