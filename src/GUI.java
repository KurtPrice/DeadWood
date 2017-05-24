import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.awt.Dimension;
import java.util.Random;

/**
 * Created by Tom on 5/24/2017.
 */
public class GUI {

    private JFrame mainFrame;
    private static GUIView gView;

    public GUI () throws IOException {

        //clock = new Clock();
        gView = new GUIView();
        //cCtlr = new Controller(clock);

        //clock.addListener(cView);

        mainFrame = new JFrame();
        mainFrame.setTitle("DeadWood");

        mainFrame.getContentPane().add(gView);
        //mainFrame.getContentPane().add(cCtlr);
        mainFrame.getContentPane().setPreferredSize(new Dimension(1400,900));
        mainFrame.pack();

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }});

        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        gView.requestFocus();
    }

    public static boolean turn(Player currentPlayer, Room[] roomArray) {
        gView.updatePlayerDisp(currentPlayer.getPlayerName() + "'s turn",
                "$" + currentPlayer.getWallet().getDollars(),
                currentPlayer.getWallet().getCredits() + "cd",
                "Current Part: " + currentPlayer.getRoleName(),
                "Part: " + currentPlayer.getRoleDesc(),
                "Rank: " + currentPlayer.getPlayerRank());
        boolean end = false;
        boolean moveAble = true;
        boolean actAble = true;
        boolean finishScene = false;
        while (!end) {
            String entry = "null";
            String currentSceneN;
            String currentSceneD;
            int currentSceneBudget;
            Role[] availRolesScene = new Role[0];
            Role[] availRolesRoom = new Role[0];

            if (currentPlayer.getPlayerLoc().getSceneRoom()) {
                SceneRoom r = (SceneRoom) currentPlayer.getPlayerLoc();
                if (!r.getSceneFinish()) {
                    currentSceneN = r.getRoomScene().getSceneName();
                    currentSceneD = r.getRoomScene().getSceneDescription();
                    currentSceneBudget = r.getRoomScene().getBudget();
                    availRolesScene = r.getRoomScene().getRoleList();
                    availRolesRoom = r.getRoleList();
                } else {
                    currentSceneN = "No current scene";
                    currentSceneD = "None";
                    currentSceneBudget = 0;
                }
            } else {
                currentSceneN = "No current scene";
                currentSceneD = "None";
                currentSceneBudget = 0;
            }

            String[] entrySplit = entry.split(" ", 2);

            switch (entrySplit[0]) {
                case "where":
                    System.out.println("Current player's room: " + currentPlayer.getPlayerLoc().getRoomName()
                            + ", Active scene: " + currentSceneN
                            + ", Description: " + currentSceneD
                            + ", Budget: " + currentSceneBudget);
                    if (availRolesScene.length != 0 || availRolesRoom.length != 0) {
                        System.out.print("Available Roles: \n");
                        System.out.println("On-Card Roles: ");
                    }

                    for (Role rs : availRolesScene) {
                        System.out.print("(" + rs.getRoleName() + ", Rank: " + rs.getRoleRank() + ") ");
                    }
                    if (availRolesRoom.length > 0) {
                        System.out.println("\nOff-Card Roles: ");
                    }
                    for (Role rr : availRolesRoom) {
                        System.out.print("(" + rr.getRoleName() + ", Rank: " + rr.getRoleRank() + ") ");
                    }
                    System.out.println();
                    Room[] adjRooms = currentPlayer.getPlayerLoc().getAdjRooms();
                    System.out.println("Available Rooms:\n");
                    for (Room r : adjRooms) {
                        System.out.println(r.getRoomName());
                    }
                    break;
                case "move":
                    if (!moveAble) {
                        System.out.println("Already moved.");
                        break;
                    }
                    if (currentPlayer.getRoleTaken()) {
                        System.out.println("Can't move while working a role.");
                        break;
                    }
                    if (entrySplit.length == 1) {
                        System.out.println("Please specify what room you want to move to.");
                        break;
                    }
                    for (int i = 0; i < (currentPlayer.getPlayerLoc().getAdjRooms().length); i++) {
                        if (entrySplit[1].equals((currentPlayer.getPlayerLoc().getAdjRooms())[i].getRoomName())) {
                            for (Room r : roomArray) {
                                if ((currentPlayer.getPlayerLoc().getAdjRooms())[i].getRoomName().equals(r.getRoomName())) {
                                    currentPlayer.move(r);
                                    currentPlayer.getPlayerLoc().removePlayer(currentPlayer);
                                    r.addPlayer(currentPlayer);
                                    break;
                                }
                            }
                            System.out.println("Move successful.");
                            moveAble = false;
                            break;
                        }
                    }
                    if (moveAble) {
                        System.out.println("Not a valid room please try again.");
                    }
                    break;
                case "work":
                    if (availRolesScene.length == 0) {
                        System.out.println("No Available Scene.");
                        break;
                    }
                    if (availRolesScene.length == 0 && availRolesRoom.length == 0) {
                        System.out.println("No Available Roles.");
                        break;
                    }
                    boolean taken = false;
                    if (currentPlayer.getRoleTaken()) {
                        System.out.println("Already working a role.");
                        break;
                    }
                    for (Role rs : availRolesScene) {
                        if (currentPlayer.getPlayerRank() >= rs.getRoleRank() && rs.getRoleName().equals(entrySplit[1]) && !currentPlayer.getRoleTaken()) {
                            currentPlayer.takeRole(rs);
                            System.out.println("Role Taken");
                            taken = true;
                            actAble = false;
                        }
                    }
                    for (Role rr : availRolesRoom) {
                        if (currentPlayer.getPlayerRank() >= rr.getRoleRank() && rr.getRoleName().equals(entrySplit[1]) && !currentPlayer.getRoleTaken()) {
                            currentPlayer.takeRole(rr);
                            System.out.println("Role Taken");
                            taken = true;
                            actAble = false;
                        }
                    }
                    if (!taken) {
                        System.out.println("Role not taken. Either not a valid role or not high enough rank.");
                    }
                    break;
                //Upgrade not tested fully
                case "upgrade":
                    if (!(currentPlayer.getPlayerLoc().equals(roomArray[11]))) {
                        System.out.println("Not in casting office, can't upgrade.");
                        break;
                    }
                    String[] entrySplitCur = entrySplit[1].split(" ", 2);
                    CastingOffice co = (CastingOffice) roomArray[11];
                    boolean success = false;
                    switch (entrySplitCur[0]) {
                        case "$":
                            try {
                                success = co.rankPlayerDollar(Integer.parseInt(entrySplitCur[1]), currentPlayer);
                            } catch (Exception e) {
                                System.out.print("Integer.parseInt fail.");
                            }
                            if (success) {
                                System.out.println("Rank up successful.");
                            } else {
                                System.out.println("Rank up unsuccessful.");
                            }
                            break;
                        case "cr":
                            try {
                                success = co.rankPlayerCredit(Integer.parseInt(entrySplitCur[1]), currentPlayer);
                            } catch (Exception e) {
                                System.out.print("Integer.parseInt fail.");
                            }
                            if (success) {
                                System.out.println("Rank up successful.");
                            } else {
                                System.out.println("Rank up unsuccessful.");
                            }
                            break;
                        default:
                            System.out.println("Specify upgrade type with $ or cr, please try again.");
                            break;
                    }
                    break;
                case "rehearse":
                    if (!actAble) {
                        System.out.println("You have already acted or rehearsed.");
                        break;
                    }
                    if (currentPlayer.getRoleTaken()) {
                        SceneRoom sr = (SceneRoom) currentPlayer.getPlayerLoc();
                        int budget = sr.getRoomScene().getBudget();
                        if (budget > (currentPlayer.pracChips + 1)) {
                            currentPlayer.pracChips++;
                            actAble = false;
                            System.out.println("One practice chip added.");
                        } else {
                            System.out.println("Max practice chips, you must act.");
                        }
                    } else {
                        System.out.println("No role to rehearse.");
                    }
                    break;
                case "act":
                    if (!actAble) {
                        System.out.println("You have already acted or rehearsed or just taken a role.");
                        break;
                    }
                    if (currentPlayer.getRoleTaken()) {
                        actAble = false;
                        SceneRoom sr = (SceneRoom) currentPlayer.getPlayerLoc();
                        int budget = sr.getRoomScene().getBudget();
                        Random ran = new Random();
                        int dieRole = (ran.nextInt(6) + 1);
                        System.out.println("You rolled a " + dieRole);
                        if (budget <= (currentPlayer.pracChips + dieRole)) {
                            if (currentPlayer.getRole().getOnCardRole()) {
                                sr.tempShotCounters--;
                                currentPlayer.getWallet().incCredits(2);
                            } else {
                                sr.tempShotCounters--;
                                currentPlayer.getWallet().incDollars(1);
                                currentPlayer.getWallet().incCredits(1);
                            }
                            System.out.println("Act successful!");
                        } else {
                            if (!currentPlayer.getRole().getOnCardRole()) {
                                currentPlayer.getWallet().incDollars(1);
                            }
                            System.out.println("Act unsuccessful.");
                        }
                        if (sr.tempShotCounters == 0) {
                            System.out.println("That's a wrap!");
                            finishScene = true;
                            sr.setFinishScene(true);
                            sr.getRoomScene().finishScene(sr);
                        }
                    } else {
                        System.out.println("No role to act.");
                    }
                    break;
                case "end":
                    end = true;
                    break;
                default:
                    break;
            }
        }
        return finishScene;
    }

    public static void setScene(int[] area){
        gView.setScene(area[0],area[1]);
    }

    public static void revealScene(int[] area, String sceneImg){
        gView.revealScene(area[0],area[1],sceneImg);
    }

    public static void setShot(int[] area){
        gView.setShotCounter(area[0],area[1]);
    }
}
