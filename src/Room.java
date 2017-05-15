import java.util.ArrayList;

/**
 * Created by pricek21 on 5/2/17.
 * Class that provides a blueprint for a Room object.
 */
public class Room {
    private String roomName;
    private Room[] adjRooms = new Room[0];
    private boolean isSceneRoom = false;
    //private int numberPlayers;
    private ArrayList<Player> playersInRoom = new ArrayList<>();

    public Room(String name, int availableCount){
        roomName = name;
        //numberPlayers = availableCount;
    }

    /**
     * Methods: getRoomName, getAdjRooms, getSceneRoom, getPlayersInRoom, setSceneRoom, addPlayer, removePlayer, addAdjRoom
     *
     * Responsibilities: Methods are responsible for getting and setting of Room objects attributes.
     */
    public String getRoomName(){
        return roomName;
    }

    public Room[] getAdjRooms(){
        return adjRooms;
    }

    public boolean getSceneRoom(){
        return isSceneRoom;
    }

    public ArrayList<Player> getPlayersInRoom()
    {
        return playersInRoom;
    }

    public void setSceneRoom(boolean b){
        isSceneRoom = b;
    }

    public void addPlayer(Player playerToAdd){
        playersInRoom.add(playerToAdd);
    }

    public void removePlayer(Player playerToRemove){
        playersInRoom.remove(playerToRemove);
    }

    public void addAdjRoom(Room adjRoom){
        Room[] tempt = new Room[adjRooms.length+1];
        System.arraycopy(adjRooms,0, tempt,0,adjRooms.length);
        tempt[adjRooms.length] = adjRoom;
        adjRooms = tempt;
    }
}
