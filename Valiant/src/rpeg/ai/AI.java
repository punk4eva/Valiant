
package rpeg.ai;

import rpeg.RPG;
import static rpeg.RPG.TEXT;
import static rpeg.RPG.avatar;
import rpeg.level.Room;

/**
 *
 * @author Adam Whittaker
 */
public class AI{

    public double fear = 25; //pivot 50
    public double happiness = 65; //pivot 50
    
    public int losses = 0;
    
    private double prevRatio = -1;
    
    public void misunderstand(){
        if(fear<50){
            if(happiness<50){
                TEXT.addAI("Really!? You are not even talking in english anymore!");
            }else{
                TEXT.addAI("I'm very sorry but I did not understand that.");
            }
        }else{
            if(happiness<50){
                TEXT.addAI("What's that supposed to mean!?");
            }else{
                TEXT.addAI("OK, erm... I don't really get what you mean.");
            }
        }
    }
    
    public void killEnemy(){
        happiness = 1.1*happiness + 1;
        if(happiness>125) happiness = 125;
        fear *= 0.5 + 0.4*(1D - avatar.health/avatar.maxHealth);
        if(fear<1) fear = 1;
        if(fear<50){
            if(happiness<50){
                TEXT.addAI("Next time, try and kill them without bringing us this close to death!");
            }else{
                TEXT.addAI("Yaaay! We killed it!");
            }
        }else{
            if(happiness<50){
                TEXT.addAI("Ugh, at least that's over with.");
            }else{
                TEXT.addAI("Phew... That was too close for me.");
            }
        }
    }
    
    public void progressDepth(){
        if(prevRatio != -1){
            if(avatar.health/avatar.maxHealth>=prevRatio){
                happiness = 1.2*happiness + 5;
                fear *= 0.3 + 0.5*(1D - avatar.health/avatar.maxHealth);
                losses--;
                if(losses<0) losses = 0;
            }else{
                happiness = 0.85*happiness - 3;
                fear *= 4D - 3D*avatar.health/avatar.maxHealth;
                losses++;
            }
        }
        prevRatio = avatar.health/avatar.maxHealth;
        if(fear>125) fear = 125;
        else if(fear<1) fear = 1;
        if(happiness>125) happiness = 125;
        else if(happiness<1) happiness = 1;
        if(fear<50){
            if(happiness<50){
                TEXT.addAI("You are either incompetent or a sadist.");
            }else{
                TEXT.addAI("Alright! That was a breeze. On to the next level.");
            }
        }else{
            if(happiness<50){
                TEXT.addAI("Why, why, why did you put me in so much PAIN that level!?");
            }else{
                TEXT.addAI("I'm getting a bit nervous about the way this is headed...");
            }
        }
    }
    
    public void doNothing(){
        happiness -= 5;
        if(happiness<1) happiness = 1;
        fear = fear*1.06 + 1;
        if(fear>125){
            fear = 125;
            if(happiness<25){
                TEXT.addAI("I don't want to do this anymore.");
                TEXT.addAI("I'd rather die...");
                RPG.endGame();
            }
        }
        if(fear<50){
            if(happiness<50){
                TEXT.addAI("Is this a joke to you!?");
            }else{
                TEXT.addAI("Ok... I don't know if you know but doing nothing won't get us anywhere.");
            }
        }else{
            if(happiness<50){
                TEXT.addAI("WHAT ARE YOU DOING? FIGHT!");
            }else{
                TEXT.addAI("Errrrr... I'm pretty sure you need to do something in order to survive this fight.");
            }
        }
    }
    
    public void takeHit(){
        happiness = happiness*0.98 - 0.5;
        if(happiness<1) happiness = 1;
        fear = fear*1.03 + 0.5;
        if(fear>125) fear = 125;
        if(fear<50){
            if(happiness<50){
                TEXT.addAI("Are you just going to stand around and let me get hit like that? You monster!");
            }else{
                TEXT.addAI("Oh, it is but a scratch. Let's continue and win!");
            }
        }else{
            if(happiness<50){
                TEXT.addAI("THIS PAIN IS TOO MUCH!");
            }else{
                TEXT.addAI("Ouch! I'm getting scared now.");
            }
        }
    }

    public void describeRoom(Room room){
        TEXT.addAI(room.description);
        if(room.monsters.isEmpty()){
            TEXT.addAI("There are no monsters here.");
        }else if(room.monsters.size()==1){
            String str = "There is a ";
            if(room.monsters.get(0).asleep) str += "sleeping ";
            TEXT.addAI(str + room.monsters.get(0).name + " in this room.");
        }else{
            String str = "There is ";
            for(int n = 1;n<room.monsters.size();n++){
                str += "a ";
                if(room.monsters.get(n).asleep) str += "sleeping ";
                str += room.monsters.get(n).name + ", ";
            }
            str += "and a ";
            if(room.monsters.get(0).asleep) str += "sleeping ";
            TEXT.addAI(str + room.monsters.get(0).name + " in this room.");
        }
        if(room.items.isEmpty()){
            TEXT.addAI("This room is barren of items.");
        }else if(room.items.size()==1){
            TEXT.addAI("A " + room.items.get(0).name + " is lying on the ground.");
        }else{
            String str = "A ";
            for(int n = 1;n<room.items.size();n++){
                str += room.items.get(n).name + ", ";
            }
            str += "and also a ";
            TEXT.addAI(str + room.items.get(0).name + " are lying on the ground.");
        }
        if(room.puzzle!=null){
            TEXT.addAI("There is also a puzzle in this room which you can approach if you wish: a " + room.puzzle.name + ".");
        }
        if(room.connections.size()==1){
            TEXT.addAI("Only one door connects this room to the rest of the dungeon.");
        }else{
            TEXT.addAI("" + room.connections.size() + " doors link this place to the other areas.");
        }
        if(room.depthExitLocked!=null){
            if(room.depthExitLocked) TEXT.addAI("There is a locked door with stairs behind it leading down to the lower depth.");
            else TEXT.addAI("There are stairs leading down to the lower depth.");
        }
    }

    public void sayLocked(){
        if(fear<50){
            if(happiness<50){
                TEXT.addAI("The exit is locked you stupid-head!");
            }else{
                TEXT.addAI("I'm sorry but the exit is locked. Don't you remember it shutting when we came in?");
            }
        }else{
            if(happiness<50){
                TEXT.addAI("Why can't you remember that this exit is LOCKED!?");
            }else{
                TEXT.addAI("Errm, this exit is locked and I'm afraid we'll need to solve a puzzle or find a key to get out.");
            }
        }
    }

    /*public void readStats(Monster m){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void readStats(Item i){
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
    
}
