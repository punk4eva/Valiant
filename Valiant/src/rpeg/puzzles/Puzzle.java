
package rpeg.puzzles;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import static logic.Utils.R;
import rpeg.level.Room;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Puzzle{

    public String name;
    public String[] description;
    public boolean lock;
    public boolean puzzleIncomplete = true;
    
    public String[] options;
    public final HashMap<String, Runnable> map = new HashMap<>();
    
    private static final double PUZZLE_CHANCE = 0.3;
    
    public Puzzle(String n, String[] desc, boolean l){
        name = n;
        description = desc;
        lock = l;
    }
    
    public abstract void win();
    
    public abstract void fail();
    
    public abstract void paint(Graphics2D g);
    
    public abstract void mouseClicked(MouseEvent e);
    
    
    public static Puzzle genPuzzle(Room r, int level){
        if(R.nextDouble()<PUZZLE_CHANCE){
            switch(R.nextInt(3)){
                case 0: return new WhistlePuzzle(r);
                case 1: return new AnimatedPuzzle(r, level);
                case 2: return new GuardianPuzzle(level, r);
            }
            throw new IllegalStateException();
        }else return null;
    }
    
}
