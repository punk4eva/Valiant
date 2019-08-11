
package rpeg.puzzles;

import static rpeg.RPG.changeState;
import static rpeg.factories.MonsterFactory.statue;
import rpeg.level.Room;
import rpeg.states.BattleState;
import static rpeg.RPG.TEXT;
import rpeg.items.assets.YendorAmulet;

/**
 *
 * @author Adam Whittaker
 */
public class GuardianPuzzle extends StatuePuzzle{

    public GuardianPuzzle(int level, Room r){
        super("chained guardian ogre", null, true, statue(true, level), r);
        description = new String[]{
            "There is a furious stone ogre chained to the wall. The chains are ",
            "connected to a hidden pulley mechanism behind the wall, and there is a", 
            "panel with four buttons next to the ogre. The ogre is wearing a ",
            statue.armor.name + " and there is a " + statue.weapon.name + " stuck behind his back."};
    }

    @Override
    public void win(){
        TEXT.addState("All four buttons are clicked. The chains tighten and allow the doors to unlock,");
        TEXT.addState("crushing the ogre and its equipment beneath the weight of the chains.");
        TEXT.addState("A secret amulet is revealed behind the ogre.");
        room.items.add(new YendorAmulet(0.1, 0.1));
        puzzleIncomplete = false;
        room.locked = false;
    }

    @Override
    public void fail(){
        puzzleIncomplete = false;
        TEXT.addState("The chains loosen enough for the stone ogre to escape. The chains constrict in his absense, unlocking the doors.");
        room.monsters.add(statue);
        changeState(new BattleState(room));
        room.locked = false;
    }

}
