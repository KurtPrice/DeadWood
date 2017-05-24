import javax.imageio.IIOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pricek21 on 5/2/17.
 * Class is the main driver of our game. Governs over the general flow and fetches data.
 */
public class Deadwood {
    private static UIText UI = new UIText();
    private static GUI UI2;
    private static int numPlayer = 0;
    private static Player[] playerList;
    private static ArrayList<Integer> usedScenes = new ArrayList<>();
    private static Scene[] sceneArray;
    private static Room[] roomArray;
    private static int numDays = 4;
    private static int numScenes;
    //private String winner;

    /**
     * Method: main
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for general flow of game and
     * fetching required data from XML files.
     *
     * Return(s): nothing
     */
    public static void main(String args[]) {
        try{
            UI2 = new GUI();
        } catch (java.io.IOException e) {
          System.out.println("GUI Exception");
        }

        if(args.length != 0) {
            try{
                numPlayer = Integer.valueOf(args[0]);
            } catch(NumberFormatException e){
                numPlayer = UI.numberPlayers();
            }
        }
        sceneArray = ReadSceneXML.read();
        roomArray = ReadBoardXML.read();

        playerList = startGame(numPlayer);
        for (int i = 0; i < numDays; i++) {
            startDay(playerList);
            endDay();
        }
        endGame();
    }

    /**
     * Method: startGame
     *
     * Parameter(s): Method that takes an int that is the number of players participating.
     *
     * Responsibilities: Method is responsible for choosing the initial conditions based off of number of players.
     *
     * Return(s): Instantiated player array with stats based off initial conditions.
     */
    private static Player[] startGame(int playerCount) {
        int startRank = 1;
        int startCredit = 0;
        switch (playerCount) {
            case 2:
                numDays = 3;
                break;
            case 3:
                numDays = 3;
                break;
            case 5:
                startCredit = 2;
                break;
            case 6:
                startCredit = 4;
                break;
            case 7:
                startRank = 2;
                break;
            case 8:
                startRank = 2;
                break;
            default:
                break;
        }
        return createPlayers(playerCount, startRank, startCredit);
    }

    /**
     * Method: createPlayers
     *
     * Parameter(s): Method that takes an int that is the number of players participating, an int being
     * the starting rank of each player, and an int for the initial credits for each player.
     *
     * Responsibilities: Method is responsible for creating player objects with initial conditions as passed.
     *
     * Return(s): Instantiated player array with stats based off initial conditions.
     */
    private static Player[] createPlayers(int playerCount, int startRank, int startCredit) {
        Player[] pList = new Player[playerCount];

        for (int i = 0; i < playerCount; i++) {
            String pName = ("Player " + String.valueOf(i + 1));
            PlayerCurrency pw = new PlayerCurrency(startCredit, 0);
            Player p = new Player(pName, startRank, roomArray[10], false, 0, pw);
            pList[i] = p;
        }
        return pList;
    }

    //private void setNumDayes(int x) {
    //    numDays = x;
    //}

    /**
     * Method: startDay
     *
     * Parameter(s): Method that takes a player array that represents all the players in the game.
     *
     * Responsibilities: Method is responsible for placing the players back into te trailer, and calling for all
     * of the scenes to be set with the scene rooms.
     *
     * Return(s): nothing
     */
    private static void startDay(Player[] playerList) {

        for (int i = 0; i < 10; i++) {
            setScenes(i);
        }
        for (Player p : playerList) {
            p.setPlayerLoc(roomArray[10]);
        }
        while (numScenes != 1) {
            for (Player p : playerList) {
                if (UI2.turn(p, roomArray)) {
                    numScenes--;
                }
                if (numScenes == 1) {
                    break;
                }
            }
        }
    }

    /**
     * Method: setScenes
     *
     * Parameter(s): Method that takes an int that represents the room in which a scene is being placed.
     *
     * Responsibilities: Method is responsible for placing a randomly generated scene into a scene room
     * that has not been used yet.
     *
     * Return(s): Returns an int in order to be recursive.
     */
    private static int setScenes(int i) {
        numScenes = 10;
        Random rand = new Random();
        SceneRoom sr = (SceneRoom) roomArray[i];
        sr.tempShotCounters = sr.shotCounters;
        sr.setFinishScene(false);
        int n = rand.nextInt(40);
        if (usedScenes.contains(n)) {
            return setScenes(i);
        } else {
            sr.setRoomScene(sceneArray[n]);
            usedScenes.add(n);
        }
        return 0;
    }

    /**
     * Method: endDay
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for making sure each player does not have a role
     * when the day ends and calls for the UI to display the end of the day.
     *
     * Return(s): nothing
     */
    private static void endDay() {
        UI.endDayPrint();
        for (Player p : playerList) {
            p.leaveRole();
        }
    }

    /**
     * Method: endGame
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for calculating all player scores, choosing
     * a winner and calling UI to display results accordingly.
     *
     * Return(s): nothing
     */
    private static void endGame() {
        String winner = "there is a tie!";
        int highScore = 0;
        for (Player p : playerList) {
            int score = (5 * p.getPlayerRank() + p.getWallet().getDollars() + p.getWallet().getCredits());
            UI.endGamePrint(p, score);
            if (score > highScore) {
                highScore = score;
                winner = p.getPlayerName();
            }
        }
        UI.displayWinner(winner);
    }

    //public int getSceneNum() {
    //    return numScenes;
    //}

    //public void decSceneNum() {
    //    numScenes -= 1;
    //}
}
