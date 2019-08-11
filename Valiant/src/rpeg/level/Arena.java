
package rpeg.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static rpeg.RPG.avatar;
import rpeg.entities.Avatar;
import rpeg.entities.Entity;
import rpeg.entities.Monster;
import static rpeg.io.MessageQueue.USER_COLOR;

/**
 *
 * @author Adam Whittaker
 */
public class Arena{

    public final Room room;
    public final int sqLen;
    
    public final int width, height;
    
    public Arena(Room r, int sq){
        room = r;
        sqLen = sq;
        width = room.width*sqLen;
        height = room.height*sqLen;
        room.prepareForBattle();
    }
    
    public void paint(Graphics2D g, int x, int y){
        g.setColor(Color.WHITE);
        for(int n=0;n<room.width+1;n++)
            g.fillRect(x+sqLen*n, y, 2, sqLen*room.height);
        for(int n=0;n<room.height+1;n++)
            g.fillRect(x, y+sqLen*n, sqLen*room.width, 2);
        room.monsters.stream().forEach(m -> {
            setColor(g, m);
            g.drawString(""+m.icon, x + m.x*sqLen + sqLen/4 + 2, y + m.y*sqLen + sqLen/6 + g.getFontMetrics().getHeight() + 2);
        });
        setColor(g, avatar);
        g.drawString(""+avatar.icon, x + avatar.x*sqLen + sqLen/4 + 2, y + avatar.y*sqLen + sqLen/6 + g.getFontMetrics().getHeight() + 2);
    }
    
    private void setColor(Graphics g, Entity m){
        if(m instanceof Avatar) g.setColor(USER_COLOR);
        else if(((Monster) m).asleep) g.setColor(Color.YELLOW);
        else g.setColor(Color.RED);
    }
    
}
