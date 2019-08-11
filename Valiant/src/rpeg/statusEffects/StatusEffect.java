
package rpeg.statusEffects;

import rpeg.entities.Avatar;
import rpeg.entities.Entity;
import rpeg.entities.Killer;

/**
 *
 * @author Adam Whittaker
 */
public class StatusEffect implements Killer{
    
    public final String name;
    public final double damage, defense, dodge, accuracy, health;
    
    public StatusEffect(String n, double dam, double def, double dod, double acc, double hp){
        name = n;
        damage = dam;
        defense = def;
        dodge = dod;
        accuracy = acc;
        health = hp;
    }
    
    public void apply(Entity e){
        e.damBoost += damage;
        e.defBoost += defense;
        e.dodge += dodge;
        e.accuracy += accuracy;
        e.health += health;
        if(e instanceof Avatar && !(this instanceof PermanentStatusEffect)) ((Avatar) e).statusEffects.add(this);
        e.checkDeath(this);
    }
    
    public void unapply(Entity e){
        e.damBoost -= damage;
        e.defBoost -= defense;
        e.dodge -= dodge;
        e.accuracy -= accuracy;
    }
    
    @Override
    public String deathMessage(){
        return " succumbed to " + name + ".";
    }
    
    public static class DamageBoost extends StatusEffect{
    
        public DamageBoost(String n, double dam){
            super(n,dam,0,0,0,0);
        }
    
    }
    
    public static class DefenseBoost extends StatusEffect{
    
        public DefenseBoost(String n, double dam){
            super(n,0,dam,0,0,0);
        }
    
    }
    
    public static class DodgeBoost extends StatusEffect{
    
        public DodgeBoost(String n, double dam){
            super(n,0,0,dam,0,0);
        }
    
    }
    
    public static class AccuracyBoost extends StatusEffect{
    
        public AccuracyBoost(String n, double dam){
            super(n,0,0,0,dam,0);
        }
    
    }
    
    public static class HealthBoost extends StatusEffect{
    
        public HealthBoost(String n, double dam){
            super(n,0,0,0,0,dam);
        }
    
    }
    
    public static class FightingBoost extends StatusEffect{
    
        public FightingBoost(String n, double dam, double def, double hp){
            super(n,dam,def,0,0,hp);
        }
    
    }
    
    public static class DexterityBoost extends StatusEffect{
    
        public DexterityBoost(String n, double dod, double acc){
            super(n,0,0,dod,acc,0);
        }
    
    }
    
}
