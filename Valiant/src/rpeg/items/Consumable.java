
package rpeg.items;

import rpeg.statusEffects.StatusEffect;

/**
 *
 * @author Adam Whittaker
 */
public class Consumable extends Item{

    public StatusEffect effect;
    
    public Consumable(StatusEffect e){
        super(e.name);
        effect = e;
    }

}
