
package rpeg.states;

import static gui.Main.HEIGHT;
import static gui.Main.WIDTH;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;
import rpeg.RPG;
import static rpeg.RPG.TEXT;
import static rpeg.RPG.ai;
import static rpeg.RPG.avatar;
import rpeg.entities.Monster;
import rpeg.items.Consumable;
import rpeg.level.Arena;
import rpeg.level.Room;

/**
 *
 * @author Adam Whittaker
 */
public class BattleState extends GameState{

    private final Room room;
    public final Arena arena;
    public HashMap<String, Runnable> map = new HashMap<>();
    
    public final int renderX, renderY;

    public BattleState(Room r){
        room = r;
        arena = new Arena(room, 48);
        renderX = WIDTH*3/4 - arena.width/2;
        renderY = HEIGHT/2 - arena.height/2;
    }
    
    @Override
    public void execute(){
        TEXT.addState("Monsters attack " + avatar.name + ".");
        TEXT.addAI("Looks like it's time to fight again...");
        initializeAcceptedInput();
        
        while(!room.monsters.stream().allMatch(m -> m.asleep)){
            TEXT.addAI("What shall we do?");
            String input = waitForInput().trim();
            if(map.containsKey(input)){
                map.get(input).run();
                room.monsters.stream().filter(m -> !m.alive).forEach(m -> {
                    if(m.weapon.drops) room.items.add(m.weapon);
                    if(m.armor.maxDefense!=0) room.items.add(m.armor);
                    if(m.drop!=null) room.items.add(m.drop);
                });
                room.monsters.removeIf(m -> !m.alive);
                monsterTurn();
            }else ai.misunderstand();
        }
    }

    private void initializeAcceptedInput(){
        LinkedList<String> opt = new LinkedList<>();
        opt.add("W,A,S,D");
        map.put("W", () -> move(0, -1));
        map.put("A", () -> move(-1, 0));
        map.put("S", () -> move(0, 1));
        map.put("D", () -> move(1, 0));
        opt.add("BLOCK");
        map.put("BLOCK", () -> block());
        
        opt.add("ATTACK <Monster's letter>");
        room.monsters.forEach((m) -> {
            map.put("ATTACK " + m.icon, () -> attack(m.icon));
        });
        avatar.consumables.stream().forEach((c) -> {
            opt.add("USE " + c.name.toUpperCase());
            map.put("USE " + c.name.toUpperCase(), () -> use(c));
        });
        
        map.put("DO NOTHING", () -> doNothing());
        opt.add("DO NOTHING");
        
        map.put("KILL", () -> {
            avatar.health = -1;
            avatar.checkDeath(avatar);
        });
        
        acceptedInput = opt.toArray(new String[opt.size()]);
    }
    
    private void attack(char i){
        for(int y = -1;y<=1;y++){
            for(int x = -1;x<=1;x++){
                if(x!=0 || y!=0){
                    Monster m = room.getMonster(avatar.x+x, avatar.y+y);
                    if(m!=null && m.icon==i){
                        m.asleep = false;
                        avatar.attack(m);
                        return;
                    }
                }
            }
        }
        TEXT.addAI("There are no monsters like this near you.");
    }
    
    private void move(int x, int y){
        if(!room.withinBounds(avatar.x, avatar.y))
            TEXT.addState(avatar.name + " tries to move but walks into a wall.");
        else if(room.getMonster(avatar.x+x, avatar.y+y)!=null){
            TEXT.addState(avatar.name + " tries to move but a monster blocks their path.");
        }else{
            avatar.x += x;
            avatar.y += y;
            room.monsters.stream().filter(m -> m.asleep).forEach(m -> {
                m.sleepCheck(1);
            });
        }
    }
    
    private void monsterTurn(){
        room.monsters.stream().filter(m -> !m.asleep).forEach(m -> {
            m.turn(room);
        });
    }
    
    private void doNothing(){
        ai.doNothing();
    }
    
    private void block(){
        TEXT.addState(avatar.name + " increases the defense of their stance.");
        avatar.defBoost += 0.015;
        room.monsters.stream().filter(m -> m.asleep).forEach(m -> {
            m.sleepCheck(2);
        });
    }
    
    public void use(Consumable c){
        c.effect.apply(avatar);
        avatar.consumables.remove(c);
        map.clear();
        initializeAcceptedInput();
    }
    

    @Override
    public void mouseClicked(MouseEvent e){
        int x = (e.getX() - renderX)/arena.sqLen, y = (e.getY() - renderY)/arena.sqLen;
        if(room.withinBounds(x, y)&&Room.nextToAvatar(x, y)){
            Monster m = room.getMonster(x, y);
            if(m==null){
                if(y==avatar.y){
                    if(x<avatar.x) RPG.enterKeyPressed("A");
                    else if(x>avatar.x) RPG.enterKeyPressed("D");
                }else if(x==avatar.x){
                    if(y<avatar.y) RPG.enterKeyPressed("W");
                    else if(y>avatar.y) RPG.enterKeyPressed("S");
                }
            }else{
                RPG.enterKeyPressed("ATTACK " + m.icon);
            }
        }
    }

    @Override
    public void paint(Graphics2D g){
        arena.paint(g, renderX, renderY);
    }

}
