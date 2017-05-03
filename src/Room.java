/**
 * Created by pricek21 on 5/2/17.
 */
public class Room {
    private String roomName;
    public int numberPlayers;

    public Room(String name, int availableCount){
        roomName = name;
        numberPlayers = availableCount;
    }

    public String getRoomName(){
        return roomName;
    }
}
