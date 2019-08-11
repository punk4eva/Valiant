
package gui;

import static gui.Window.window;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.File;
import logic.Game;
import logic.Utils.ThreadUsed;
import logic.Utils.Unfinished;
import static logic.Utils.exceptionStream;

/**
 *
 * @author Adam Whittaker
 */
public class Main extends Canvas implements Runnable{
    
    public static final int WIDTH, HEIGHT;
    static{
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int)screen.getWidth();
        HEIGHT = (int)screen.getHeight();
    }

    protected volatile boolean running = false;
    protected Thread runThread;

    //public final static Animator animator = new Animator();
    //protected static final GuiManager gui = new GuiManager();
    protected static Game game; 
    
    /**
     * Creates an instance.
     */
    public Main(){
        window = new Window(WIDTH, HEIGHT, "ERROR", this);
    }
    
    
    /**
     * Starts the pacemaker and initializes mouse input.
     */
    @Override
    @ThreadUsed("Render")
    public void run(){
        this.requestFocus();
        window.pacemaker.start();
    }

    /**
     * Renders the game.
     * @param frames
     * @thread render
     */
    public void render(int frames){
        BufferStrategy bs = this.getBufferStrategy();
        Graphics2D bsg = (Graphics2D) bs.getDrawGraphics();
        
        game.render(bsg, frames);
        
        bsg.dispose();
        bs.show();
    }
    
    
    @Unfinished("Need to complete")
    public void click(int x, int y){}
    
    /**
     * Starts the game.
     */
    public final synchronized void start(){
        runThread = new Thread(this, "Run Thread");
        runThread.setDaemon(true);
        runThread.start();
        running = true;
        game.start();
    }

    /**
     * Stops the game.
     */
    public final synchronized void stop(){
        try{
            runThread.join();
            running = false;
        }catch(InterruptedException e){
            e.printStackTrace(exceptionStream);
            System.err.println("Fail in stop() method of Main");
        }
    }

    public void deleteSelf(){
        if(new File("dist/GMKTGameJam.jar").delete()){
            System.out.println("The game has been corrupted!");
        }else{
            System.out.println("Failed to delete jar.");
        }
        System.exit(-1);
    }
    
}
