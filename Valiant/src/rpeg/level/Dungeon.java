
package rpeg.level;

import static rpeg.RPG.*;
import rpeg.entities.Avatar;
import rpeg.states.RoomState;

/**
 *
 * @author Adam Whittaker
 */
public class Dungeon{
    
    public final Level[] levels = new Level[6];
    private int currentLevel = 1;
    
    public Dungeon(){
        for(int n=0;n<levels.length;n++) levels[n] = new Level(n+1);
    }

    public void progress(){
        currentLevel++;
        TEXT.addState(avatar.name + " descends down the flight of stairs to depth " + currentLevel + " of the dungeon.");
        ai.progressDepth();
        TEXT.addState(avatar.name + "'s health and status effects have been restored.");
        avatar.health = avatar.maxHealth;
        Avatar.statusEffects.forEach((e) -> {
            e.unapply(avatar);
        });
        Avatar.statusEffects.clear();
        changeState(new RoomState(levels[currentLevel-1].rooms.get(0)));
    }
    
}
