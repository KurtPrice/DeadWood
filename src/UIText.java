/**
 * Created by eddyt on 5/10/17.
 */

import java.util.*;

public class UIText {
    private static Scanner sc = new Scanner(System.in);

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

    public static void turn(Player currentPlayer, Room[] roomArray) {
        System.out.println(currentPlayer.getPlayerName() + "'s turn.");
        boolean end = false;
        boolean moveAble = true;
        while (end == false) {
            System.out.println("what would you like to do?");
            String entry = sc.nextLine();
            String currentScene;
            Role[] availRolesScene = new Role[0];
            Role[] availRolesRoom = new Role[0];

            if (currentPlayer.getPlayerLoc().getSceneRoom()) {
                SceneRoom r = (SceneRoom) currentPlayer.getPlayerLoc();
                currentScene = r.getRoomScene().getSceneName();
                availRolesScene = r.getRoomScene().getRoleList();
                availRolesRoom = r.getRoleList();
            } else {
                currentScene = "No current scene";
            }

            String[] entrySplit = entry.split(" ", 2);

            switch (entrySplit[0]) {
                case "who":
                    System.out.println("Current player: " + currentPlayer.getPlayerName()+" ($"+currentPlayer.getWallet().getDollars()+", "+currentPlayer.getWallet().getCredits()+"cd), Current Part: " + currentPlayer.getRoleName());
                    break;
                case "where":
                    System.out.println("Current player's room: " + currentPlayer.getPlayerLoc().getRoomName() + ", Active scene: " + currentScene);
                    if (availRolesScene.length != 0 || availRolesRoom.length != 0){System.out.print("Available Roles: ");}
                    for(int i=0; i<availRolesScene.length; i++){
                        System.out.print("("+availRolesScene[i].getRoleName()+", Rank: "+availRolesScene[i].getRoleRank()+") ");
                    }
                    for(int j=0; j<availRolesRoom.length; j++){
                        System.out.print("("+availRolesRoom[j].getRoleName()+", Rank: "+availRolesRoom[j].getRoleRank()+") ");
                    }
                    System.out.println();
                    break;
                case "move":
                    if (moveAble == false) {
                        System.out.println("Already moved.");
                        break;
                    }
                    for (int i = 0; i < (currentPlayer.getPlayerLoc().getAdjRooms().length); i++) {
                        if (entrySplit[1].equals((currentPlayer.getPlayerLoc().getAdjRooms())[i].getRoomName())) {
                            for(int j=0; j<roomArray.length; j++){
                                if((roomArray[j].getRoomName()).equals((currentPlayer.getPlayerLoc().getAdjRooms())[i].getRoomName())){
                                    currentPlayer.move(roomArray[j]);
                                    break;
                                }
                            }
                            System.out.println("Move successful.");
                            moveAble = false;
                            break;
                        }
                    }
                    if (moveAble == true) {
                        System.out.println("Not a valid room please try again.");
                    }
                    break;
                case "work":if (availRolesScene.length == 0 && availRolesRoom.length == 0){System.out.print("No Available Roles."); break;}
                    for(int i=0; i<availRolesScene.length; i++){
                        if(currentPlayer.getPlayerRank() >= availRolesScene[i].getRoleRank() && availRolesScene[i].getRoleName().equals(entrySplit[1]) && currentPlayer.getRoleTaken() == false){
                            currentPlayer.takeRole(availRolesScene[i]);
                            System.out.println("Role Taken");
                        }
                    }
                    for(int j=0; j<availRolesRoom.length; j++){
                        if(currentPlayer.getPlayerRank() >= availRolesScene[j].getRoleRank() && availRolesScene[j].getRoleName().equals(entrySplit[1]) && currentPlayer.getRoleTaken() == false){
                            currentPlayer.takeRole(availRolesScene[j]);
                            System.out.println("Role Taken");
                        }
                    }
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
