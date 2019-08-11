
package gui;

import logic.Utils;
/**
 *
 * @author Adam Whittaker
 */
public class Pacemaker implements Runnable{

    private final Main main;
    private final Thread renderThread;
    private long now;
    
    private final static int BUFFER_NUM = 4;
    private final static long DELAY = 1000L/60L;
    
    /**
     * Creates a new instance.
     * @param v The main.
     */
    public Pacemaker(Main v){
        main = v;
        renderThread = new Thread(this, "Render Thread");
    }
    
    /**
     * Starts rendering.
     */
    public void start(){
        renderThread.start();
    }
    
    @Override
    public void run(){
        long n, timer = System.currentTimeMillis();
        int frames, f=0;
        main.createBufferStrategy(BUFFER_NUM);
        while(main.running){
            n = System.currentTimeMillis();
            if(n-now>DELAY){
                frames = (int)((n-now)/DELAY);
                now = n;
                main.render(frames);
                f++;
            }
            if(System.currentTimeMillis() - timer > 10000){
                timer+=10000;
                Utils.performanceStream.println("FPS: " + (double)f/10d);
                f=0;
            }
            
        }
    }
    
}
