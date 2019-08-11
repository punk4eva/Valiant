
package rpeg.states;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;
import rpeg.RPG;
import static rpeg.RPG.*;
import rpeg.items.*;
import rpeg.level.Room;

/**
 *
 * @author Adam Whittaker
 */
public class RoomState extends GameState{
    
    public final Room room;
    public HashMap<String, Runnable> map = new HashMap<>();
    private volatile boolean inRoom = true;
    
    public RoomState(Room r){
        room = r;
    }

    @Override
    public void execute(){
        if(!room.visited){
            TEXT.addState(avatar.name + " has moved into a new room.");
            if(room.puzzle!=null && room.puzzle.lock) TEXT.addState("The doors all lock shut around them.");
            room.visited = true;
            ai.describeRoom(room);
        }else TEXT.addState(avatar.name + " moves to the centre of the room.");
                
        if(room.monsters.stream().anyMatch(m -> !m.asleep)) changeState(new BattleState(room));
        while(inRoom){
            initializeOptions();
            RPG.ensureState(this);
            TEXT.addAI("What shall we do?");
            String input = waitForInput().toUpperCase().trim();
            if(map.containsKey(input)){
                map.get(input).run();
            }else ai.misunderstand();
        }
    }
    
    private void initializeOptions(){
        LinkedList<String> opt = new LinkedList<>();
        if(!room.monsters.isEmpty()){
            map.put("FIGHT", () -> fight());
            opt.add("FIGHT");
        }
        room.items.stream().forEach(i -> {
            map.put("PICK UP " + i.name.toUpperCase(), () -> pickUpItem(i));
            opt.add("PICK UP " + i.name.toUpperCase());
        });
        if(room.puzzle!=null){
            map.put("APPROACH PUZZLE", () -> approachPuzzle());
            opt.add("APPROACH PUZZLE");
        }
        for(int n=0;n<room.connections.size();n++){
            final int i = n; //effective finalization
            map.put("GO THROUGH DOOR " + (n+1), () -> {
                enterDoor(room.connections.get(i));
            });
            opt.add("GO THROUGH DOOR " + (n+1));
        }
        map.put("DESCRIBE ROOM", () -> {
            describe();
        });
        opt.add("DESCRIBE ROOM");
        if(room.depthExitLocked!=null){
            opt.add("EXIT LEVEL");
            map.put("EXIT LEVEL", () -> exitLevel());
        }
        acceptedInput = opt.toArray(new String[opt.size()]);
    }
    
    
    public void exitLevel(){
        if(room.depthExitLocked){
            if(avatar.hasKey("key to depth exit")){
                TEXT.addState(avatar.name + " uses their key to unlock the entrance to the next level.");
                inRoom = false;
                dungeon.progress();
            }else ai.sayLocked();
        }else{
            inRoom = false;
            dungeon.progress();
        }
    }
    
    public void describe(){
        ai.describeRoom(room);
    }
    
    public void enterDoor(Room r){
        if(room.locked) ai.sayLocked();
        else{
            TEXT.addState(avatar.name + " walks through the door and into a new room.");
            changeState(new RoomState(r));
            inRoom = false;
        }
    }
    
    public void approachPuzzle(){
        changeState(new PuzzleState(room));
    }
    
    public void pickUpItem(Item i){
        if(i instanceof Armor){
            if(((Armor) i).maxDefense>avatar.armor.maxDefense){
                TEXT.addState(avatar.name + " equips the " + i.name + " and discards their old " + avatar.armor.name + ".");
                avatar.armor = (Armor) i;
            }else TEXT.addState(avatar.name + " discards the " + ((Armor)i).name + " as it is less protective than their current armor.");
            room.items.remove(i);
        }else if(i instanceof Weapon){
            TEXT.addAI("Do you really want to change your " + avatar.weapon.name + " for a " + i.name + "?");
            TEXT.addAI("You won't be able to use your current weapon again if you discard it.");
            String[] temp = acceptedInput;
            acceptedInput = new String[]{"yes", "no"};
            String inp = waitForInput();
            acceptedInput = temp;
            if(Character.toUpperCase(inp.charAt(0))=='Y'){
                room.items.remove(i);
                TEXT.addState(avatar.name + " equips the " + i.name + " and discards their old " + avatar.weapon.name + ".");
                avatar.weapon = (Weapon) i;
            }
        }else if(i instanceof Amulet){
            room.items.remove(i);
            TEXT.addState(avatar.name + " has acquired an " + i.name + " trinket.");
            ((Amulet) i).effect.apply(avatar);
        }else if(i instanceof Consumable){
            room.items.remove(i);
            TEXT.addState(avatar.name + " puts the " + i.name + " in a pouch for use later.");
            avatar.consumables.add((Consumable)i);
        }else if(i instanceof Key){
            room.items.remove(i);
            TEXT.addState(avatar.name + " puts the " + i.name + " in a pocket.");
            avatar.keys.add((Key)i);
        } else throw new IllegalStateException();
    }
    
    public void fight(){
        room.monsters.stream().forEach(m -> {
            m.asleep = false;
        });
        changeState(new BattleState(room));
    }

    @Override
    public void paint(Graphics2D g){}

    @Override
    public void mouseClicked(MouseEvent e){}

}
