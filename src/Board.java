/**
 * Created by pricek21 on 5/2/17.
 */
public class Board {

    private int numPlayer;
    private ArrayLIst<Player> playerList;
    private int numDays;
    private int numScenes;
    private String winner;

    private void startGame(int playerCount){

    }

    private ArrayList<Player> CreatePlayers(int playerCount){
        return null;
    }

    private void setNumDayes(int x){
        numDays = x;
    }

    private void startDay(int playerCount){

    }

    public int getSceneNum(){
        return numScenes;
    }

    public void decSceneNum(){
        numScenes -= 1;
    }

    private void endDay(){

    }

    private String endGame(){
        return winner;
    }

    private void displayWinner(String congratMessage){
        System.out.println(congratMessage + " " + winner);
    }

    public static void main(String args[]){

    }
}
