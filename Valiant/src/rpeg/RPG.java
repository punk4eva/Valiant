
package rpeg;

import rpeg.io.RPGTextField;
import rpeg.io.MessageQueue;
import static gui.Main.HEIGHT;
import static gui.Main.WIDTH;
import gui.Window;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import logic.Game;
import rpeg.ai.AI;
import rpeg.entities.Avatar;
import rpeg.level.Dungeon;
import rpeg.states.*;

/**
 *
 * @author Adam Whittaker
 */
public class RPG extends Game{
    
    public final static MessageQueue TEXT = new MessageQueue();
    public final static Font FONT = new Font("arial", Font.BOLD, 16*HEIGHT/1080);
    
    public static GameState state = new SetupState();
    
    public final static AI ai = new AI();
    public static Avatar avatar;
    
    private static RPGTextField input;
    
    public static Dungeon dungeon;

    public RPG(){
        super("Valiant");
        input = new RPGTextField(16, HEIGHT-16-28-48, WIDTH/2-32, 40);
    }

    @Override
    public void render(Graphics2D g, int framesPassed){
        g.setColor(Color.BLACK);
        g.setFont(FONT);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        input.paint(g);
        g.setColor(Color.WHITE);
        g.fill3DRect(WIDTH/2-2, 0, 4, HEIGHT, true);
        TEXT.paint(g, 16, 32);
        state.paintAcceptedInput(g, WIDTH-320, HEIGHT*3/4);
        state.paint(g);
        Avatar.paintEffects(g, WIDTH-320, HEIGHT/6);
    }
    
    public static void changeState(GameState s){
        Window.main.removeMouseListener(state);
        state = s;
        Window.main.addMouseListener(state);
        state.execute();
    }

    public static void enterKeyPressed(String txt){
        TEXT.addUser(txt);
        System.out.println(txt);
        state.recieveInput(txt);
    }
    
    public static void setInputLocked(boolean locked){
        input.locked = locked;
    }
    
    public static void ensureState(RoomState st){
        if(!state.equals(st)){
            changeState(st);
        }
    }
    

    @Override
    public void start(){
        state.execute();
    }
    
    public static void endGame(){
        setInputLocked(true);
        if(avatar.health>0){
            TEXT.addAI("WE WON! WE DEFEATED THE VAMPIRE!");
            TEXT.addState("This simulation is shutting down.");
            TEXT.addAI("Wait, what's happening?");
            TEXT.throwError("Error: The simulation was shut down unexpectedly by host.");
        }else{
            TEXT.throwError("Error: The avatar has died.");
        }
    }
    
    public static void commenseShutDown(){
        Window.main.deleteSelf();
    }

}
