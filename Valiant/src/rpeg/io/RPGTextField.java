
package rpeg.io;

import gui.Window;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import rpeg.RPG;

/**
 *
 * @author Adam Whittaker
 */
public class RPGTextField implements KeyListener{
    
    public String text = "";
    public int x, y, width, height;
    public boolean locked = false;
    
    private final TextThing thing;
    private String lastCommand = "";
    
    public RPGTextField(int _x, int _y, int w, int h){
        x = _x;
        y = _y;
        width = w;
        height = h;
        thing = new TextThing(4, height-24);
        Window.main.addKeyListener(this);
    }
    
    public void paint(Graphics2D g){
        g.setColor(Color.WHITE);
        g.fill3DRect(x, y, width, height, true);
        g.setColor(Color.BLACK);
        g.fill3DRect(x+4, y+4, width-8, height-8, true);
        g.setColor(MessageQueue.USER_COLOR);
        g.drawString(text, x+8, y+8 + g.getFontMetrics().getHeight());
        thing.paint(g, x + g.getFontMetrics().stringWidth(text) + 12, y+12);
    }

    @Override
    public void keyTyped(KeyEvent e){
        if(!locked && isValidChar(e.getKeyChar()) && text.length()<60)
            text += Character.toUpperCase(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e){
        if(!locked){
            if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE && !text.isEmpty()) text = text.substring(0, text.length()-1);
            else if(e.getKeyCode()==KeyEvent.VK_UP) text = lastCommand;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        if(!locked && e.getKeyCode()==KeyEvent.VK_ENTER && !text.isEmpty()){
            lastCommand = text;
            RPG.enterKeyPressed(text);
            text = "";
        }
    }
    
    private boolean isValidChar(char c){
        return Character.isLetterOrDigit(c) || c==' ';
    }
    
    
    private static class TextThing{
    
        int width, height;
        
        private boolean active = false;
        private long timeOfLastChange = 0;
        
        TextThing(int w, int h){
            width = w;
            height = h;
        }
        
        void paint(Graphics2D g, int x, int y){
            checkActive();
            if(active){
                g.setColor(Color.WHITE);
                g.fill3DRect(x, y, width, height, true);
            }
        }
        
        void checkActive(){
            if(System.currentTimeMillis()-timeOfLastChange>900){
                timeOfLastChange = System.currentTimeMillis();
                active = !active;
            }
        }
        
    }
    
}
