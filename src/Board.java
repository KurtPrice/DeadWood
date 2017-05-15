import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pricek21 on 5/2/17.
 */
public class Board {
    private static UIText UI = new UIText();
    private static int numPlayer = UI.numberPlayers();
    private static Player[] playerList;
    private static ArrayList<Integer> usedScenes = new ArrayList<>();
    private static Scene[] sceneArray;
    private static Room[] roomArray;
    private static int numDays = 4;
    private static int numScenes;
    //private String winner;

    public static void main(String args[]) {
        ReadSceneXML sParser = new ReadSceneXML();
        sceneArray = sParser.read();

        ReadBoardXML bParser = new ReadBoardXML();
        roomArray = bParser.read();

        playerList = startGame(numPlayer);
        for (int i = 0; i < numDays; i++) {
            startDay(playerList);
            endDay();
        }
        endGame();
    }

    /**
     * Method that chooses initial conditions based off of number of players.
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
        return CreatePlayers(playerCount, startRank, startCredit);
    }

    /**
     * Method that creates player objects with initial conditions as passed.
     */
    private static Player[] CreatePlayers(int playerCount, int startRank, int startCredit) {
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

    private static void startDay(Player[] playerList) {

        for (int i = 0; i < 10; i++) {
            setScenes(i);
        }
        for (Player p : playerList) {
            p.setPlayerLoc(roomArray[10]);
        }
        //Changed for debug.
        while (numScenes != 1) {
            for (Player p : playerList) {
                if (UIText.turn(p, roomArray)) {
                    numScenes--;
                }
                //Changed for debug.
                if (numScenes == 1) {
                    break;
                }
            }
        }
    }

    private static int setScenes(int i) {
        numScenes = 10;
        Random rand = new Random();
        SceneRoom sr = (SceneRoom) roomArray[i];
        sr.tempShotCounters = sr.shotCounters;
        sr.setFinishScene(false);
        int n = rand.nextInt(39);
        if (usedScenes.contains(n)) {
            return setScenes(i);
        } else {
            sr.setRoomScene(sceneArray[n]);
            usedScenes.add(n);
        }
        return 0;
    }

    private static void endDay() {
        UI.endDayPrint();
        for (Player p : playerList) {
            p.leaveRole();
        }
    }

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
