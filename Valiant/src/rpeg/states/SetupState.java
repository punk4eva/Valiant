
package rpeg.states;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import rpeg.RPG;
import static rpeg.RPG.*;
import rpeg.entities.Avatar;
import rpeg.level.Dungeon;

/**
 *
 * @author Adam Whittaker
 */
public class SetupState extends GameState{

    private void start(){
        TEXT.addAI("Hello...");
        TEXT.addAI("Welcome to the VALIANT experiment...");
        TEXT.addAI("I, the AI, will be your avatar in this scenario.");
        TEXT.addAI("Please remember to stay calm and breath...");
        TEXT.addAI("Alright enough of this meditation crap, let's get started.");
        TEXT.addAI("What's your name?");
        acceptedInput = new String[]{"anything"};
    }
    
    private void recieveName(String name){
        if(name.length()!=1) name = name.charAt(0)+name.substring(1).toLowerCase();
        avatar = new Avatar(name);
        TEXT.addAI("That's an interesting name.");
        TEXT.addAI(name + "...");
        if(!avatar.name.equals(Avatar.realName)){
            TEXT.addAI("Are you sure it isn't " + Avatar.realName + "?");
            acceptedInput = new String[]{"yes", "no"};
        }
    }
    
    private void areYouSure(boolean real){
        if(real){
            TEXT.addAI("OK then. " + avatar.name + " it is.");          
        }else{
            TEXT.addAI("Thought so, " + Avatar.realName + ".");
            avatar.name = Avatar.realName;
            avatar.icon = Character.toUpperCase(Avatar.realName.charAt(0));
        }
    }
    
    private void enter(){
        TEXT.addAI("Are you ready to enter the dungeon then?");
        waitForInput();
        TEXT.addAI("Ready or not, here we go!");
        TEXT.addAI("Be sure to stay...");
        TEXT.addAI("valiant.");
        TEXT.addState(avatar.name + " walks down the stairs of the dungeon into the first level.");
        RPG.dungeon = new Dungeon();
        changeState(new RoomState(RPG.dungeon.levels[0].rooms.get(0)));
    }

    @Override
    public void execute(){
        start();
        recieveName(waitForInput());
        if(!avatar.name.equals(Avatar.realName)) areYouSure(Character.toUpperCase(waitForInput().charAt(0))!='N');
        enter();
    }

    
    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void paint(Graphics2D g){}
    
}
