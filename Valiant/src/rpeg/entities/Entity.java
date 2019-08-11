
package rpeg.entities;

import static logic.Utils.R;
import rpeg.RPG;
import rpeg.items.Armor;
import rpeg.items.Weapon;
import static rpeg.RPG.TEXT;
import static rpeg.RPG.ai;

/**
 *
 * @author Adam Whittaker
 */
public class Entity implements Killer{
    
    public static final double ACC_ADVANTAGE = 1.5;

    public Weapon weapon;
    public Armor armor;
    public double dodge;
    public double accuracy;
    public String name;
    public char icon;
    public double health;
    public double damBoost = 0, defBoost = 0;
    public boolean alive = true;
    
    public int x = -1, y = -1;
    
    public Entity(String n, char c, double h, Weapon w, Armor ar, double d, double a){
        name = n;
        weapon = w;
        health = h;
        armor = ar == null ? new Armor("nothing", 0) : ar;
        dodge = d;
        icon = c;
        accuracy = a;
    }
    
    public void attack(Entity defender){
        TEXT.addState(name + " attacks " + defender.name);
        if(ACC_ADVANTAGE*R.nextDouble()*accuracy>R.nextDouble()*defender.dodge){
            double dam = (weapon.maxDamage+damBoost)*R.nextDouble() + 1, def = (defender.armor.maxDefense+defender.defBoost)*R.nextDouble();
            if(def>dam) TEXT.addState(defender.name + "'s armor completely absorbed " + name + "'s attack.");
            else{
                dam -= def;
                if(Math.round(dam)<1) TEXT.addState(name + "'s attack was almost negligable.");
                else TEXT.addState(name + "'s attack dealt " + Math.round(dam) + " damage to " + defender.name + ".");
                if(dam>0){
                    defender.health -= dam;
                    defender.checkDeath(this);
                }
                if(defender.health>1) TEXT.addState(defender.name + " now has " + (int)defender.health + " health.");
                else if(defender.health>0) TEXT.addState(defender.name + " now has almost no health.");
                if(defender instanceof Avatar && defender.alive) ai.takeHit();
            }
            if(R.nextDouble()<weapon.effectChance) weapon.effect.apply(defender);
        }else TEXT.addState(defender.name + " dodged " + name + "'s attack.");
    }
    
    public void checkDeath(Killer k){
        if(health<=0){
            TEXT.addState(name + k.deathMessage());
            alive = false;
            if(this instanceof Avatar) RPG.endGame();
            else ai.killEnemy();
        }
    }

    @Override
    public String deathMessage(){
        return " was killed by " + name + " using their " + weapon.name + ".";
    }
    
}
