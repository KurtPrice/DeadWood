/**
 * Created by eddyt on 5/10/17.
 */

import java.util.*;

public class UIText {
    private Scanner sc = new Scanner(System.in);

    public int numberPlayers() {
        System.out.println("How many players are playing?");
        String entry = sc.nextLine();
        if (isStringInt(entry) == true) {
            int pCount = Integer.parseInt(entry);
            if (pCount >= 2 & pCount <= 8) {
                return pCount;
            } else {
                System.out.println("Invalid number of players enter a number between 2 and 8");
                return numberPlayers();
            }
        } else {
            System.out.println("Invalid number of players enter a number between 2 and 8");
            return numberPlayers();
        }
    }

    public void turn(Player currentPlayer) {
        System.out.println(currentPlayer.getPlayerName() + "'s turn.");
        boolean end = false;
        boolean moveAble = true;
        while (end == false) {
            System.out.println("what would you like to do?");
            String entry = sc.nextLine();
            String currentScene;
            SceneRoom nul = new SceneRoom();
            if (currentPlayer.getPlayerLoc().equals(nul)) {
                SceneRoom r = (SceneRoom) currentPlayer.getPlayerLoc();
                currentScene = r.getRoomScene().getSceneName();
            } else {
                currentScene = "No current scene";
            }

            String[] entrySplit = entry.split(" ");
            if (entrySplit.length == 1) {
                entrySplit = new String[]{entrySplit[0], "null", "null"};
            }
            if (entrySplit.length == 2) {
                entrySplit = new String[]{entrySplit[0], entrySplit[1], "null"};
            }
            switch (entrySplit[0]) {
                case "who":
                    System.out.println("Current player: " + currentPlayer.getPlayerName() + ", Current Part: " + currentPlayer.getRoleName());
                    break;
                case "where":
                    System.out.println("Current player's room: " + currentPlayer.getPlayerLoc().getRoomName() + ", Active scene: " + currentScene);
                    break;
                case "move":
                    if (moveAble == false) {
                        System.out.println("Already moved.");
                        break;
                    }
                    for (int i = 0; i < (currentPlayer.getPlayerLoc().getAdjRooms().length); i++) {
                        if (entrySplit[1].equals((currentPlayer.getPlayerLoc().getAdjRooms())[i].getRoomName()) || (entrySplit[1] + " " + entrySplit[2]).equals((currentPlayer.getPlayerLoc().getAdjRooms())[i].getRoomName())) {
                            currentPlayer.move((currentPlayer.getPlayerLoc().getAdjRooms())[i]);
                            System.out.println("Move successful.");
                            moveAble = false;
                            break;
                        }
                    }
                    if (moveAble == true) {
                        System.out.println("Not a valid room please try again.");
                    }
                    break;
                case "work":
                    break;
                case "upgrade":
                    if (entrySplit[1] == "$") {
                    }
                    if (entrySplit[1] == "cr") {
                    }
                    break;
                case "rehearse":
                    break;
                case "act":
                    break;
                case "end":
                    end = true;
                    break;
                default:
                    System.out.println("Not a valid command try again.");
                    break;
            }
        }
    }

    private boolean isStringInt(String entry) {
        try {
            Integer.parseInt(entry);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
