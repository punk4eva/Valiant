
package rpeg.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import logic.Utils.ThreadUsed;
import static logic.Utils.exceptionStream;

/**
 *
 * @author Adam Whittaker
 */
public abstract class GameState implements MouseListener{
    
    protected String[] acceptedInput = null;
    
    private volatile String input = null;
    
    @ThreadUsed("Run Thread")
    public synchronized String waitForInput(){
        input = null;
        while(input==null) try{
            this.wait();
        }catch(InterruptedException e){
            e.printStackTrace();
            e.printStackTrace(exceptionStream);
        }
        return input;
    }
    
    @ThreadUsed("AWT Thread")
    public synchronized void recieveInput(String inp){
        input = inp;
        this.notify();
    }
    
    
    public void paintAcceptedInput(Graphics2D g, int x, int y){
        if(acceptedInput!=null){
            g.setColor(Color.YELLOW);
            g.drawString("ACCEPTED RESPONCES: ", x, y);
            for(String inp : acceptedInput){
                y += 2 + g.getFontMetrics().getHeight();
                g.drawString(inp, x, y);
            }
        }
    }
    
    
    public abstract void execute();
    
    public abstract void paint(Graphics2D g);
    
    
    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}
    
}
