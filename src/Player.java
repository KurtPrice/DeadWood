/**
 * Created by pricek21 on 5/2/17.
 */
public class Player {

    private String playerName;
    private int playerRank;
    private Room playerLoc;
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
        return null;
    }

    public void moveOptions(Room playerLoc){

    }

    public void move(Room goalRoom){

    }

    public void takeRole(){

    }

    public void practiceRole(int pracChips){

    }

    public int[] actRole(int pracChips){
        return new int[]{0};
    }

    public void endTurn(){

    }
}
