import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.awt.Dimension;
/**
 * Created by Tom on 5/24/2017.
 */
public class GUI {

    private JFrame mainFrame;
    private GUIView gView;

    public GUI () throws IOException {

        //clock = new Clock();
        gView = new GUIView();
        //cCtlr = new Controller(clock);

        //clock.addListener(cView);

        mainFrame = new JFrame();
        mainFrame.setTitle("DeadWood");

        mainFrame.getContentPane().add(gView);
        //mainFrame.getContentPane().add(cCtlr);
        mainFrame.getContentPane().setPreferredSize(new Dimension(1200,900));
        mainFrame.pack();

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }});

        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        gView.requestFocus();
    }

    public static boolean turn(Player currentPlayer, Room[] RoomArray){
        return false;
    }

}
