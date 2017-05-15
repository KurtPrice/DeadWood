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

    public static boolean turn(Player currentPlayer, Room[] roomArray) {
        System.out.println(currentPlayer.getPlayerName() + "'s turn.");
        boolean end = false;
        boolean moveAble = true;
        boolean actAble = true;
        boolean finishScene = false;
        while (end == false) {
            System.out.println("what would you like to do?");
            String entry = sc.nextLine();
            String currentSceneN;
            String currentSceneD;
            Role[] availRolesScene = new Role[0];
            Role[] availRolesRoom = new Role[0];

            if (currentPlayer.getPlayerLoc().getSceneRoom()) {
                SceneRoom r = (SceneRoom) currentPlayer.getPlayerLoc();
                if(r.getSceneFinish()==false){
                currentSceneN = r.getRoomScene().getSceneName();
                currentSceneD = r.getRoomScene().getSceneDescription();
                availRolesScene = r.getRoomScene().getRoleList();
                availRolesRoom = r.getRoleList();}
                else{currentSceneN = "No current scene";
                    currentSceneD = "None";}
            } else {
                currentSceneN = "No current scene";
                currentSceneD = "None";
            }

            String[] entrySplit = entry.split(" ", 2);

            switch (entrySplit[0]) {
                case "who":
                    System.out.println("Current player: " + currentPlayer.getPlayerName()+" ($"+currentPlayer.getWallet().getDollars()+", "+currentPlayer.getWallet().getCredits()+"cd), Current Part: " + currentPlayer.getRoleName());
                    break;
                case "where":
                    System.out.println("Current player's room: " + currentPlayer.getPlayerLoc().getRoomName() + ", Active scene: " + currentSceneN+", Description: "+currentSceneD);
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
                    if (moveAble == false) {System.out.println("Already moved.");break;}
                    if (currentPlayer.getRoleTaken()){System.out.println("Can't move while working a role."); break;}
                    for (int i = 0; i < (currentPlayer.getPlayerLoc().getAdjRooms().length); i++) {
                        if (entrySplit[1].equals((currentPlayer.getPlayerLoc().getAdjRooms())[i].getRoomName())) {
                            for(int j=0; j<roomArray.length; j++){
                                if((roomArray[j].getRoomName()).equals((currentPlayer.getPlayerLoc().getAdjRooms())[i].getRoomName())){
                                    currentPlayer.move(roomArray[j]);
                                    roomArray[j].addPlayer(currentPlayer);
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
                case "work":
                    if (availRolesScene.length == 0){System.out.println("No Available Scene."); break;}
                    if (availRolesScene.length == 0 && availRolesRoom.length == 0){System.out.println("No Available Roles."); break;}
                    boolean taken = false;
                    if(currentPlayer.getRoleTaken()){System.out.println("Already working a role."); break;}
                    for(int i=0; i<availRolesScene.length; i++){
                        if(currentPlayer.getPlayerRank() >= availRolesScene[i].getRoleRank() && availRolesScene[i].getRoleName().equals(entrySplit[1]) && currentPlayer.getRoleTaken() == false){
                            currentPlayer.takeRole(availRolesScene[i]);
                            System.out.println("Role Taken");
                            taken = true;
                        }
                    }
                    for(int j=0; j<availRolesRoom.length; j++){
                        if(currentPlayer.getPlayerRank() >= availRolesRoom[j].getRoleRank() && availRolesRoom[j].getRoleName().equals(entrySplit[1]) && currentPlayer.getRoleTaken() == false){
                            currentPlayer.takeRole(availRolesRoom[j]);
                            System.out.println("Role Taken");
                            taken = true;
                        }
                    }
                    if(taken == false){System.out.println("Role not taken. Either not a valid role or not high enough rank.");}
                    break;
               //Upgrade not tested fully
                case "upgrade":
                    if(!(currentPlayer.getPlayerLoc().equals(roomArray[11]))){System.out.println("Not in casting office, can't upgrade.");break;}
                    String[] entrySplitCur = entrySplit[1].split(" ", 2);
                    CastingOffice co = (CastingOffice)roomArray[11];
                    boolean success = false;
                    if (entrySplitCur[0].equals("$")) {
                        try {
                            success = co.rankPlayerDollar(Integer.parseInt(entrySplitCur[1]),currentPlayer);
                        } catch (Exception e) {}
                        if(success){
                            System.out.println("Rank up successful.");
                        }else{System.out.println("Rank up unsuccessful.");}
                    }else if (entrySplitCur[0].equals("cr")) {
                        try {
                            success = co.rankPlayerDollar(Integer.parseInt(entrySplitCur[1]), currentPlayer);
                        }catch (Exception e){}
                        if(success){
                            System.out.println("Rank up successful.");
                        }else{System.out.println("Rank up unsuccessful.");}
                    }else{System.out.println("Specify upgrade type with $ or cr, please try again.");}
                    break;
                case "rehearse":
                    if(actAble == false){System.out.println("You have already acted or rehearsed.");break;}
                    if(currentPlayer.getRoleTaken()){
                        SceneRoom sr = (SceneRoom)currentPlayer.getPlayerLoc();
                        int budget = sr.getRoomScene().getBudget();
                        if(budget > (currentPlayer.pracChips + 1)){
                            currentPlayer.pracChips++;
                            actAble=false;
                            System.out.println("One practice chip added.");
                        }else{System.out.println("Max practice chips, you must act.");}
                    }else{System.out.println("No role to rehearse.");}
                    break;
                case "act":
                    if(actAble == false){System.out.println("You have already acted or rehearsed.");break;}
                    if(currentPlayer.getRoleTaken()){
                        actAble=false;
                        SceneRoom sr = (SceneRoom)currentPlayer.getPlayerLoc();
                        int budget = sr.getRoomScene().getBudget();
                        Random ran = new Random();
                        int dieRole = (ran.nextInt(5)+1);
                        System.out.println("You rolled a "+dieRole);
                        if(budget < (currentPlayer.pracChips + dieRole)){
                            if(currentPlayer.getRole().getOnCardRole()){
                                sr.shotCounters--;
                                currentPlayer.getWallet().incCredits(2);
                            }else{
                                sr.shotCounters--;
                                currentPlayer.getWallet().incDollars(1);
                                currentPlayer.getWallet().incCredits(1);
                            }
                            System.out.println("Act successful!");
                        }else{
                            if(currentPlayer.getRole().getOnCardRole() ==false){
                                currentPlayer.getWallet().incDollars(1);
                            }
                            System.out.println("Act unsuccessful.");
                        }
                        if (sr.shotCounters == 0){System.out.println("That's a wrap!");finishScene = true; sr.setFinishScene(true);sr.getRoomScene().finishScene(sr);}
                    }else{System.out.println("No role to act.");}
                    break;
                case "end":
                    end = true;
                    break;
                default:
                    System.out.println("Not a valid command try again.");
                    break;
            }
        }
    return finishScene;
    }

    public void endDayPrint(){
        System.out.println("End of the day.");
    }

    public void endGamePrint(Player p, int score){
        System.out.println(p.getPlayerName()+", Score: "+score);
    }

    public void displayWinner(String winner) {
        System.out.println("Congratulations "+winner+"!");
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
