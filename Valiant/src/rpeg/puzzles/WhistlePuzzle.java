
package rpeg.puzzles;

import static gui.Main.HEIGHT;
import static gui.Main.WIDTH;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import static logic.Utils.R;
import static rpeg.RPG.avatar;
import static rpeg.RPG.changeState;
import static rpeg.factories.MonsterFactory.ghost;
import rpeg.level.Room;
import rpeg.states.BattleState;
import static rpeg.RPG.TEXT;
import static rpeg.RPG.enterKeyPressed;
import rpeg.items.Amulet;
import rpeg.statusEffects.PermanentStatusEffect;

/**
 *
 * @author Adam Whittaker
 */
public class WhistlePuzzle extends Puzzle{
    
    private enum Dial{
        UP ("up", 0), RIGHT ("right", 1), DOWN ("down", 2), LEFT ("left", 3);
        
        final String name;
        private final int num;
        
        private final static int width = 24*1080/HEIGHT, height = 64*1080/HEIGHT;
        
        
        Dial(String n, int nu){
            name = n;
            num = nu;
        }
        
        public Dial clockwise(){
            return Dial.values()[(num+1)%4];
        }
        
        public Dial antiClockwise(){
            return Dial.values()[(num+3)%4];
        }
        
        public static Dial getRandom(){
            return Dial.values()[R.nextInt(4)];
        }
        
        public void paint(Graphics2D g, int x, int y){
            g.setColor(Color.WHITE);
            g.fill3DRect(x-1, y-1, width+2, height+2, true);
            g.setColor(Color.BLACK);
            g.fill3DRect(x+1, y+1, width-2, height-2, true);
            g.setColor(Color.WHITE);
            g.drawString(""+num, x+width/2-g.getFontMetrics().stringWidth(""+num)/2, y+height/2-g.getFontMetrics().getHeight()/2);
        }
        
    }
    

    private final Room room;
    private final Dial[] dials = new Dial[]{Dial.UP, Dial.UP, Dial.UP, Dial.UP},
            unlock = new Dial[]{Dial.getRandom(), Dial.getRandom(), 
                Dial.getRandom(), Dial.getRandom()};
    
    private final static int buffer = 24, height = 48, renderX = WIDTH*3/4 - 5*buffer/2 - 2*Dial.width, renderY = (HEIGHT-height)/2;

    public WhistlePuzzle(Room r){
        super("antique whistle contraption", 
                new String[]{"This mysterious machine consists of a brass tube with four dials",
                "emitting a quiet, high-pitch sound. The tube is blowing into an amplifier",
                "with a lever to control whether to amplify the sound or not (currently off).",
                "Mysterious ghostly energy is pulsating through the contraption."}, true);
        room = r;
        options = new String[9];
        for(int n=0;n<4;n++){
            options[2*n] = "TWIST DIAL " + (n+1) + " CLOCKWISE";
            options[2*n+1] = "TWIST DIAL " + (n+1) + " ANTICLOCKWISE";
            final int i = n; //effective finalization.
            map.put(options[2*n], () -> rotateDial(i, true));
            map.put(options[2*n+1], () -> rotateDial(i, false));
        }
        options[8] = "AMPLIFY";
        map.put("AMPLIFY", () -> amplify());
    }

    @Override
    public void win(){
        room.locked = false;
        puzzleIncomplete = false;
        TEXT.addState("The contraption makes a clicking sound and a spirit caged within the tube is released. The doors of the room unlock.");
        TEXT.addAI("I think the spirit left some of it's precious ectoplasm in the tube!");
        room.items.add(new Amulet("ectoplasm", new PermanentStatusEffect.StealthLevelUp(1)));
    }

    @Override
    public void fail(){
        room.monsters.add(ghost());
        puzzleIncomplete = false;
        TEXT.addState("The cap on the rear of the brass tube blows open violently and an angry dungeon spirit is released.");
        changeState(new BattleState(room));
        TEXT.addState("As a result of the vengent spirit being vanquished, the room's doors unlock.");
        room.locked = false;
    }
    
    public void rotateDial(int dialNum, boolean clock){
        dials[dialNum] = clock ? dials[dialNum].clockwise() : 
                dials[dialNum].antiClockwise();
        TEXT.addState(avatar.name + " rotates a dial. The tube emits a" + loudness() + " sound");
    }
    
    private String loudness(){
        int score = 0;
        for(int n=0;n<dials.length;n++){
            score += (dials[n].num - unlock[n].num + 4)%4;
        }
        if(score==0) return " deafening";
        else if(score<3) return " roaring";
        else if(score<5) return " loud";
        else if(score<8) return " considerable";
        else if(score<11) return " quiet";
        else return " barely audible";
    }
    
    private void amplify(){
        TEXT.addState(avatar.name + " amplifies the sound coming from the brass tube.");
        int score = 0;
        for(int n=0;n<dials.length;n++){
            score += (dials[n].num - unlock[n].num + 4)%4;
        }
        if(score==0) win();
        else fail();
    }
    
    @Override
    public void paint(Graphics2D g){
        g.setColor(Color.WHITE);
        g.fill3DRect(renderX, renderY, buffer*5+Dial.width*dials.length, height, true);
        int y = renderY + height/2 - Dial.height/2;
        for(int n=0;n<dials.length;n++)
            dials[n].paint(g, renderX+buffer*(1+n)+Dial.width*n, y);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getY()>renderY+(height-Dial.height)/2 
                && e.getY()<renderY+(height-Dial.height)/2){
            if(e.getX()>renderX+buffer && e.getX()<renderX+buffer+Dial.width)
                enterKeyPressed("TWIST DIAL 1 CLOCKWISE");
            else if(e.getX()>renderX+2*buffer+Dial.width && e.getX()<renderX+2*buffer+2*Dial.width)
                enterKeyPressed("TWIST DIAL 2 CLOCKWISE");
            else if(e.getX()>renderX+3*buffer+2*Dial.width && e.getX()<renderX+3*buffer+3*Dial.width)
                enterKeyPressed("TWIST DIAL 3 CLOCKWISE");
            else if(e.getX()>renderX+4*buffer+3*Dial.width && e.getX()<renderX+4*buffer+4*Dial.width)
                enterKeyPressed("TWIST DIAL 4 CLOCKWISE");
        }
    }

}
