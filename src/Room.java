import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;

/**
 * Created by pricek21 on 5/2/17.
 */
public class Room {
    private String roomName;
    private Room[] adjRooms = new Room[0];
    private boolean isSceneRoom = false;
    private int numberPlayers;
    public ArrayList<Player> playersInRoom = new ArrayList<>();

    public Room(String name, int availableCount){
        roomName = name;
        numberPlayers = availableCount;
    }

    public String getRoomName(){
        return roomName;
    }

    public Room[] getAdjRooms(){
        return adjRooms;
    }

    public boolean getSceneRoom(){
        return isSceneRoom;
    }

    public void setSceneRoom(boolean b){
        isSceneRoom = b;
    }



    public ArrayList<Player> getPlayersInRoom()
    {
        return playersInRoom;
    }

    public void addPlayer(Player playerToAdd){
        playersInRoom.add(playerToAdd);
    }

    public void addAdjRoom(Room adjRoom){
        Room[] tempt = new Room[adjRooms.length+1];
        for(int i = 0; i < adjRooms.length; i++){
            tempt[i] = adjRooms[i];
        }
        tempt[adjRooms.length] = adjRoom;
        adjRooms = tempt;
    }

}
