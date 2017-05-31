import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by pricek21 on 5/2/17.
 * Class that represents each instance of a player in our game.
 */
public class Player {

    private String playerName;
    private int playerRank;
    private Room playerLoc;
    private Role playerRole;
    private boolean roleTaken;
    private PlayerCurrency wallet;
    public int pracChips;
    private JLabel label;

    public Player(String name, int rank, Room location, boolean roleTaken, int pracChips, PlayerCurrency money){
        playerName = name;
        playerRank = rank;
        playerLoc = location;
        this.roleTaken = roleTaken;
        this.pracChips = pracChips;
        wallet = money;
    }

    /**
     * Method: getPlayerName
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for returning player name.
     *
     * Return(s): string
     */
    public String getPlayerName(){
        return playerName;
    }

    /**
     * Method: getPlayerRank
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for returning player rank.
     *
     * Return(s): int
     */
    public int getPlayerRank(){
        return playerRank;
    }

    /**
     * Method: setPlayerRank
     *
     * Parameter(s): Method takes an int which will be the players rank.
     *
     * Responsibilities: Method is responsible for setting player rank.
     *
     * Return(s): nothing
     */
    public void setPlayerRank(int rank){
        playerRank = rank;
    }

    /**
     * Method: getPlayerLoc
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for returning player's room location.
     *
     * Return(s): Room
     */
    public Room getPlayerLoc()
    {
        return playerLoc;
    }

    /**
     * Method: setPlayerLoc
     *
     * Parameter(s): Method takes a Room which will be the players new location.
     *
     * Responsibilities: Method is responsible for setting player location.
     *
     * Return(s): nothing
     */
    public void setPlayerLoc(Room room){
        playerLoc = room;
    }

    /**
     * Method: getWallet
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for getting player wallet.
     *
     * Return(s): PlayerCurrency
     */
    public PlayerCurrency getWallet(){
        return wallet;
    }

    /**
     * Method: getRoleTaken
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for getting the boolean responsible for
     * telling if a player currently has a role.
     *
     * Return(s): boolean
     */
    public boolean getRoleTaken(){return roleTaken;}

    /**
     * Method: getRole
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for getting player role.
     *
     * Return(s): Role
     */
    public Role getRole(){
        return  playerRole;
    }

    /**
     * Method: getRoleName
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for getting role name if possible.
     *
     * Return(s): string
     */
    public String getRoleName(){
        if(playerRole != null){return playerRole.getRoleName();}else{return "No Current Role";}
    }

    /**
     * Method: getRoleDesc
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for getting player role description if possible.
     *
     * Return(s): string
     */
    public String getRoleDesc(){
        if(playerRole != null){return playerRole.getRoleDescription();}else{return "No Current Role";}
    }


    public void setLabel(JLabel l){
        label = l;
    }

    public JLabel getLabel(){
        return label;
    }
    //public void moveOptions(Room playerLoc){
    //    Room[] adjRooms = playerLoc.getAdjRooms();
    //    for(Room room: adjRooms){
    //        System.out.println(room.getRoomName());
    //    }
    //}

    /**
     * Method: move
     *
     * Parameter(s): Method takes a Room which is the desired room to move to.
     *
     * Responsibilities: Method is responsible for moving the player to the desired room if
     * and only if the room is accessible from the players current location.
     *
     * Return(s): nothing
     */
    public void move(Room goalRoom){
        Room[] adjRooms = playerLoc.getAdjRooms();
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

    /**
     * Method: takeRole
     *
     * Parameter(s): Method takes a Role which will be the players new role.
     *
     * Responsibilities: Method is responsible for setting players role and role taken boolean.
     *
     * Return(s): nothing
     */
    public void takeRole(Role currentRole){
        roleTaken = true;
        playerRole = currentRole;
    }

    /**
     * Method: leaveRole
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for removing the player from their
     * current role.
     *
     * Return(s): nothing
     */
    public void leaveRole()
    {
        roleTaken = false;
        playerRole = null;
    }

    public void practiceRole(int pracChips){
        this.pracChips += pracChips;
    }

    public void actRole(int credits, int dollars){
        wallet.incCredits(credits);
        wallet.incDollars(dollars);
    }

    public ArrayList<Integer> getUpgrades(){
        CastingOffice office = new CastingOffice("Office", 0);
        int credits = wallet.getCredits();
        int dollars = wallet.getDollars();
        ArrayList<Integer> possibleRanks = new ArrayList<>();
        int checkRank = 2;
        while(checkRank < 7){
            if(office.rankUpgradable(dollars, credits, checkRank)){
                possibleRanks.add(checkRank);
            }
        }
        return possibleRanks;
    }
}
