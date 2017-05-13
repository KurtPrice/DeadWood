/**
 * Created by pricek21 on 5/2/17.
 */
public class Player {

    private String playerName;
    private int playerRank;
    private Room playerLoc;
    private Role playerRole;
    private boolean roleTaken;
    private PlayerCurrency wallet;
    public int pracChips;

    public Player(String name, int rank, Room location, boolean roleTaken, int pracChips, PlayerCurrency money){
        playerName = name;
        playerRank = rank;
        playerLoc = location;
        this.roleTaken = roleTaken;
        this.pracChips = pracChips;
        wallet = money;
    }

    public String getPlayerName(){
        return playerName;
    }

    public int getPlayerRank(){
        return playerRank;
    }

    public Room getPlayerLoc(){
        return playerLoc;
    }

    public PlayerCurrency getWallet(){
        return wallet;
    }

    public void moveOptions(Room playerLoc){
        Room[] adjRooms = playerLoc.getAdjRooms();
        for(Room room: adjRooms){
            System.out.println(room.getRoomName());
        }
    }

    public void move(Room goalRoom){
        Room[] adjRooms = playerLoc.getAdjRooms();
        String goalName = goalRoom.getRoomName();
        boolean found = false;
        for(Room room: adjRooms){
            if(goalRoom.getRoomName().equals(room.getRoomName())){
                found = true;
                break;
            }
        }

        if(found){
            //System.out.println("Moving to " + goalName);
            playerLoc = goalRoom;
        } else{
            //System.out.println("Could not move to " + goalName);
            //System.out.println("Room must be adjacent to players current room.");
        }
    }

    public String getRoleName(){
        if(playerRole != null){return playerRole.getRoleName();}else{return "No Current Role";}
    }

    public void takeRole(Role currentRole){
        roleTaken = true;
        playerRole = currentRole;
    }

    public void leaveRole(){
        roleTaken = false;
    }

    public void practiceRole(int pracChips){
        this.pracChips += pracChips;
    }

    public void actRole(int credits, int dollars){
        wallet.incCredits(credits);
        wallet.incDollars(dollars);
    }
}
