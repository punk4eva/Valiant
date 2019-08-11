
package rpeg.io;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rpeg.RPG;

/**
 *
 * @author Adam Whittaker
 */
public class MessageQueue extends LinkedList<TextAdder>{

    public static final int CAPACITY = 30;
    
    public static final Color USER_COLOR = new Color(45, 208, 229);
    private static final long DELAY = 23;
    
    private long timeOfLastTick = 0;
    
    
    private synchronized void add(Color col, String message){
        add(new TextAdder(col, message, false));
        if(size()>CAPACITY) removeFirst();
    }
    
    public void addAI(String message){
        add(Color.WHITE, message);
        RPG.setInputLocked(true);
    }
    
    public void addState(String message){
        add(Color.YELLOW, message);
        RPG.setInputLocked(true);
    }
    
    public synchronized void addUser(String message){
        add(new TextAdder(USER_COLOR, message, true));
        if(size()>CAPACITY) removeFirst();
    }
    
    public synchronized void paint(Graphics2D g, int x, int y){
        tickAdders();
        for(TextAdder adder : this){
            adder.paint(g, x, y);
            y += 2 + g.getFontMetrics().getHeight();
        }
    }
    
    public void tickAdders(){
        if(System.currentTimeMillis()-timeOfLastTick>DELAY){
            timeOfLastTick = System.currentTimeMillis();
            boolean stillTicking = false;
            for(TextAdder adder : this){
                if(!adder.isComplete()){
                    adder.increment();
                    stillTicking = true;
                    break;
                }
            }
            if(!stillTicking) RPG.setInputLocked(false);
        }
    }

    public void throwError(String m){
        add(new TextAdder(Color.RED, m, true));
        add(new TextAdder(Color.RED, "Caused by: java.NullPointerException", true));
        add(new TextAdder(Color.RED, "     at valiant.logic.Main.runSimulation(line 192)", true));
        add(new TextAdder(Color.RED, "     at valiant.logic.Main.constructMain(line 78)", true));
        add(new TextAdder(Color.RED, "     at valiant.logic.Main.main(line 256)", true));
        add(new TextAdder(Color.RED, "Exiting in 10 seconds...", true));
        try{
            Thread.sleep(10000);
        }catch(InterruptedException ex){}
        while(size()>CAPACITY) removeFirst();
        RPG.commenseShutDown();
    }
    
}
