
package rpeg.states;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import static rpeg.RPG.TEXT;
import static rpeg.RPG.ai;
import static rpeg.RPG.avatar;
import rpeg.level.Room;

/**
 *
 * @author Adam Whittaker
 */
public class PuzzleState extends GameState{

    private final Room room;
    public final HashMap<String, Runnable> map;

    PuzzleState(Room r){
        room = r;
        map = room.puzzle.map;
        acceptedInput = r.puzzle.options;
    }

    @Override
    public void execute(){
        TEXT.addState(avatar.name + " approaches the " + room.puzzle.name + ".");
        for(String desc : room.puzzle.description) TEXT.addAI(desc);
        while(room.puzzle.puzzleIncomplete){
            TEXT.addAI("What shall we do?");
            String input = waitForInput().toUpperCase().trim();
            if(map.containsKey(input)){
                map.get(input).run();
            }else ai.misunderstand();
        }
    }

    @Override
    public void paint(Graphics2D g){
        room.puzzle.paint(g);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        room.puzzle.mouseClicked(e);
    }

}
