package gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import logic.SoundSystem;
import rpeg.RPG;

/**
 *
 * @author Charlie Hands 
 * @author Adam Whittaker
 */
public class Window{
    
    public final JFrame frame;
    public static Main main;
    public static final SoundSystem soundSystem = new SoundSystem();
    public Pacemaker pacemaker;
    public static Window window;
    
    
    public Window(int width, int height, String title, Main m){
        frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width,height));
        frame.setMinimumSize(new Dimension(width,height));
        frame.setMaximumSize(new Dimension(width,height));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.add(m);
        main = m;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pacemaker = new Pacemaker(m);
        frame.setVisible(true);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Main m = new Main();
        Main.game = new RPG();
        window.frame.setTitle(Main.game.name);
        m.start();
    }
    
}
