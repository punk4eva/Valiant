
package rpeg.level;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static logic.Utils.R;
import rpeg.factories.RoomFactory;

/**
 *
 * @author Adam Whittaker
 */
public class Level{
    
    public final LinkedList<Room> rooms;
    public final int level;
    
    
    public Level(int l){
        level = l;
        if(level==6){
            rooms = new LinkedList<>();
            rooms.add(new Room());
        }else{
            rooms = generateRooms();
            generateConnections();
        }
    }
    
    
    private LinkedList<Room> generateRooms(){
        int roomNum = 4+R.nextInt(4);
        LinkedList<Room> ret = new LinkedList<>();
        for(int n=0;n<roomNum;n++) ret.add(RoomFactory.genRoom(level));
        switch(level){
            case 2: ret.add(RoomFactory.golemRoom());
                ret.get(1).depthExitLocked = true;
                break;
            case 3: ret.add(RoomFactory.hydraPit());
                ret.get(1).depthExitLocked = true;
                break;
            default: ret.get(1).depthExitLocked = false;
                break;
        }
        return ret;
    }
    
    private void generateConnections(){
        int connectionNum = rooms.size() - 1 + R.nextInt(5);
        int i, j;
        for(int n=0;n<connectionNum;n++){
            i = R.nextInt(rooms.size());
            j = R.nextInt(rooms.size()-1);
            if(j>=i) j++;
            rooms.get(i).connections.add(rooms.get(j));
            rooms.get(j).connections.add(rooms.get(i));
        }
        Set<Room> explored = new HashSet<>();
        explored.add(rooms.get(0));
        ensureConnectedness(rooms.get(0), explored);
    }
    
    private void ensureConnectedness(Room current, Set<Room> explored){
        floodFillCorridors(current, explored);
        if(explored.size()<rooms.size()){
            List<Room> remainingRooms = rooms.stream().filter(r -> !explored.contains(r)).collect(Collectors.toList());
            Room nextRoom = remainingRooms.get(R.nextInt(remainingRooms.size()));
            nextRoom.connections.add(current);
            current.connections.add(nextRoom);
            explored.add(nextRoom);
            ensureConnectedness(nextRoom, explored);
        }
    }
    
    private Room floodFillCorridors(Room current, Set<Room> explored){
        Room ret = current;
        for(Room r : current.connections){
            if(!explored.contains(r)){
                explored.add(r);
                floodFillCorridors(r, explored);
                ret = r;
            }
        }
        return ret;
    }
    
}
