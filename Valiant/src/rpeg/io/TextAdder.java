
package rpeg.io;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class TextAdder{
        
    private final Color color;
    protected String current;
    protected final String target;

    public TextAdder(Color col, String txt, boolean autocomplete){
        target = txt;
        color = col;
        if(autocomplete) current = target;
        else current = "";
    }
    
    public void increment(){
        current += target.charAt(current.length());
    }
    
    public boolean isComplete(){
        return current.length()==target.length();
    }
    
    public void paint(Graphics2D g, int x, int y){
        g.setColor(color);
        g.drawString(current, x, y);
    }
        
}
