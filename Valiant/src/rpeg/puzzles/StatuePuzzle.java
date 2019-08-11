
package rpeg.puzzles;

import static gui.Main.HEIGHT;
import static gui.Main.WIDTH;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import logic.Utils;
import rpeg.RPG;
import static rpeg.RPG.avatar;
import rpeg.entities.Monster;
import rpeg.level.Room;
import static rpeg.RPG.TEXT;

/**
 *
 * @author Adam Whittaker
 */
public abstract class StatuePuzzle extends Puzzle{
    
    private final String solution = getRandomArrangement();
    private String current = "";
    private int attempts = 5;
    protected final Monster statue;
    protected final Room room;
    
    private final Button[] buttons = new Button[4];
    
    private static final int sqLen = 48;
    private static final int renderX = WIDTH*3/4-sqLen*2, renderY = HEIGHT/2-sqLen/2;

    
    public StatuePuzzle(String na, String[] desc, boolean l, Monster s, Room r){
        super(na, desc, l);
        statue = s;
        room = r;
        options = new String[]{"PRESS BUTTON 1", "PRESS BUTTON 2", "PRESS BUTTON 3", "PRESS BUTTON 4"};
        map.put("PRESS BUTTON 1", () -> click(1));
        map.put("PRESS BUTTON 2", () -> click(2));
        map.put("PRESS BUTTON 3", () -> click(3));
        map.put("PRESS BUTTON 4", () -> click(4));
        for(int n=0;n<buttons.length;n++) buttons[n] = new Button(n);
    }

    private static String getRandomArrangement(){
        String str = "";
        int[] ary = new int[]{4,1,2,3};
        Utils.shuffle(ary);
        for(int i : ary){
            str += i;
        }
        return str;
    }
    
    public void click(int num){
        if(current.contains(""+num))
            TEXT.addAI("You can't press that button as it is already clicked down.");
        else{
            current += num;
            if(solution.startsWith(current)){
                buttons[num-1].clicked = true;
                TEXT.addState(avatar.name + " presses the button and it clicks in place.");
                if(current.length()==4) win();
            }else{
                attempts--;
                current = "";
                for(int n=0;n<buttons.length;n++) buttons[n].clicked = false;
                TEXT.addState(avatar.name + " presses the button. All the buttons unclick and the pulley releases a link of the chain. There are " + attempts + " links remaining.");
                if(attempts==0){
                    fail();
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getY()>renderY && e.getY()<renderY+sqLen &&
                e.getX()>renderX && e.getX()<renderX+4*sqLen){
            RPG.enterKeyPressed("PRESS BUTTON " + (1+(e.getX()-renderX)/sqLen));
        }
    }
    
    @Override
    public void paint(Graphics2D g){
        g.setColor(Color.WHITE);
        g.fillRect(renderX-1, renderY-1, sqLen*4+2, 2);
        g.fillRect(renderX-1, renderY+sqLen-1, sqLen*4+2, 2);
        for(int n=0;n<4;n++){
            g.setColor(Color.WHITE);
            g.fillRect(renderX+sqLen*n-1, renderY, 2, sqLen);
            buttons[n].paint(g, renderX+sqLen*n, renderY);
        }
        g.setColor(Color.WHITE);
        g.fillRect(renderX+sqLen*4-1, renderY, 2, sqLen);
    }
    
    
    private static class Button{
        
        int num;
        boolean clicked = false;
        
        public Button(int n){
            num = n;
        }
        
        public void drawClicked(Graphics2D g, int x, int y){
            g.setColor(Color.WHITE);
            g.fillOval(x+sqLen/6, y+sqLen/2, sqLen*2/3, sqLen/3);
            g.setColor(Color.BLACK);
            g.fillOval(x+sqLen/6+2, y+sqLen/2+2, sqLen*2/3-4, sqLen/3-4);
            g.setColor(Color.WHITE);
            g.fillOval(x+sqLen/6+4, y+sqLen/2+4, sqLen*2/3-8, sqLen/3-8);
        }
        
        public void addUnclicked(Graphics2D g, int x, int y){
            g.fillOval(x+sqLen/6+4, y+sqLen/6+4, sqLen*2/3-8, sqLen/3-8);
            g.fillRect(x+sqLen/6+4, y+sqLen/3, sqLen*2/3-8, sqLen/3);
        }
        
        public void paint(Graphics2D g, int x, int y){
            drawClicked(g, x, y);
            if(!clicked) addUnclicked(g, x, y);
        }
        
    }

}
