/**
 * Created by pricek21 on 5/2/17.
 */
public class Room {
    private String roomName;
    private Room[] adjRooms;
    public int numberPlayers;

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

    public int getNumberPlayers(){
        return numberPlayers;
    }

    public void addAdjRoom(Room adjRoom){
        Room[] tempt = new Room[adjRooms.length+1];
        for(int i = 0; i < adjRooms.length; i++){
            tempt[i] = adjRooms[i];
        }
        tempt[adjRooms.length] = adjRoom;
    }

}
