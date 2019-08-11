
package rpeg.puzzles;

import static rpeg.RPG.changeState;
import static rpeg.factories.MonsterFactory.statue;
import rpeg.level.Room;
import rpeg.states.BattleState;
import static rpeg.RPG.TEXT;
import rpeg.items.assets.ScrollOfBattle;

/**
 *
 * @author Adam Whittaker
 */
public class AnimatedPuzzle extends StatuePuzzle{

    public AnimatedPuzzle(Room r, int level){
        super("enigmatic statue", null, false, statue(false, level), r);
        description = new String[]{"There is a statue on a pedestal in the centre of the room. ",
                "It is wrapped tightly with chains and is equipped with a ",
                statue.weapon.name + " and a " + statue.armor.name + ". ",
                "There is a control panel with 4 buttons on the pedestal."};
    }

    @Override
    public void win(){
        TEXT.addState("All four buttons are clicked. The chains tighten and the statue and its equipment crumble to pieces.");
        TEXT.addState("A rare scroll is dispensed from the pedestal.");
        room.items.add(new ScrollOfBattle(0.3, 0.5, 5));
        puzzleIncomplete = false;
    }

    @Override
    public void fail(){
        TEXT.addState("The statue is now unchained and suddenly animated.");
        room.monsters.add(statue);
        puzzleIncomplete = false;
        changeState(new BattleState(room));
    }

}
