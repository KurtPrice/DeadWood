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

    public static Player cPlayer;
    public static Room[] rArray;
    private static boolean  moveAble;
    private static boolean  actAble;
    private static boolean end;
    public GUI () throws IOException {

        //clock = new Clock();
        gView = new GUIView();
        //cCtlr = new Controller(clock);

        //clock.addListener(cView);

        mainFrame = new JFrame();
        mainFrame.setTitle("DeadWood");

        mainFrame.getContentPane().add(gView);
        //mainFrame.getContentPane().add(cCtlr);
        mainFrame.getContentPane().setPreferredSize(new Dimension(1410,900));
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
        cPlayer = currentPlayer;
        rArray = roomArray;
        gView.updatePlayerDisp(currentPlayer.getPlayerName() + "'s turn",
                "$" + currentPlayer.getWallet().getDollars(),
                currentPlayer.getWallet().getCredits() + "cd",
                "Current Part: " + currentPlayer.getRoleName(),
                "Part: " + currentPlayer.getRoleDesc());
        end = false;
        moveAble = true;
        actAble = true;
        boolean actAble = true;
        boolean finishScene = false;
        while (!end) {
            String entry = "null";
            Role[] availRolesScene = new Role[0];
            Role[] availRolesRoom = new Role[0];

            String[] entrySplit = entry.split(" ", 2);
        }
        return finishScene;
    }

    public static boolean getMoveAble(){
        return moveAble;
    }
    public static void setMoveAble(boolean b){
        moveAble = b;
    }
    public static boolean getActAble(){
        return actAble;
    }
    public static void setActAble(boolean b){
        actAble = b;
    }
    public static void endTurn(){
        end =true;
    }
    public static void setScene(int[] area, int tag){
        gView.setScene(area[0],area[1],tag);
    }

    public static void revealScene(int[] area, String sceneImg){
        gView.revealScene(area[0],area[1],sceneImg);
    }

    public static void setShot(int[] area){
        gView.setShotCounter(area[0],area[1]);
    }

    public static void setPlayers(Player[] pArray){
        gView.setPlayers(pArray);
    }

}
