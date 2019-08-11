
package rpeg.items;

import rpeg.statusEffects.StatusEffect;

/**
 *
 * @author Adam Whittaker
 */
public class Amulet extends Item{

    public final StatusEffect effect;
    
    public Amulet(String n, StatusEffect eff){
        super(n);
        effect = eff;
    }

}
