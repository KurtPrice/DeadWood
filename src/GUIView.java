/**
 * Created by Tom on 5/24/2017.
 */

import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
import java.awt.Color;
//import model.GUI;


public class GUIView
        extends JLayeredPane{
        //implements GUI.Listener {

    private JLabel background;
    private JLabel cardBack;
    private JLabel scene;
    private JLabel shot;
    private JLabel[] disp;

    private JButton moveButton;

    public GUIView () {
        Resources r = Resources.getInstance();

        setSize(1400,900);
        setDoubleBuffered(true);

        background = new JLabel();
        add(background, new Integer(0));
        background.setBounds(0,0,1200,900);
        background.setIcon(r.getBG());

        moveButton = new JButton("Move");
        add(moveButton);
        moveButton.setBounds(1205,200,200,100);
        setVisible(true);
    }

    public void setScene(int x,int y) {
        Resources r = Resources.getInstance();

        cardBack = new JLabel();
        add(cardBack, new Integer(1));
        cardBack.setBounds(x,y,205,115);
        cardBack.setIcon(r.getCB());

        setVisible(true);
        setFocusable(true);
       // int h = c.getHours();
       // hTen.setIcon(r.getIcon(h/10));
        //hOne.setIcon(r.getIcon(h%10));

       // int m = c.getMinutes();
       // mTen.setIcon(r.getIcon(m/10));
        //mOne.setIcon(r.getIcon(m%10));

       // int s = c.getSeconds();
        //sTen.setIcon(r.getIcon(s/10));
        //sOne.setIcon(r.getIcon(s%10));

    }

    public void setShotCounter(int x, int y){
        Resources r = Resources.getInstance();

        shot = new JLabel();
        add(shot, new Integer(1));
        shot.setBounds(x,y,47,47);
        shot.setIcon(r.getShotCounter());

        setVisible(true);
        setFocusable(true);
    }
    public void revealScene(int x, int y, String sceneImg){
        Resources r = Resources.getInstance();

        scene = new JLabel();
        add(scene, new Integer(2));
        scene.setBounds(x,y,205,115);
        scene.setIcon(r.getScene(sceneImg));

        setVisible(true);
        setFocusable(true);
    }

    public void updatePlayerDisp(String name, String dollars, String credits, String part, String partD,String partR){
        disp = new JLabel[6];

        disp[0] = new JLabel(name,0);
        disp[0].setFont(disp[0].getFont().deriveFont((float)20));
        add(disp[0],new Integer(3));
        disp[0].setBounds(1200,0,200,50);

        disp[1] = new JLabel(dollars,0);
        disp[1].setFont(disp[1].getFont().deriveFont((float)20));
        add(disp[1],new Integer(4));
        disp[1].setBounds(1200,25,200,50);

        disp[2] = new JLabel(credits,0);
        disp[2].setFont(disp[2].getFont().deriveFont((float)20));
        add(disp[2],new Integer(5));
        disp[2].setBounds(1200,50,200,50);

        String formattedPart=String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 180, part);
        disp[3] = new JLabel(formattedPart);
        disp[3].setFont(disp[3].getFont().deriveFont((float)15));
        add(disp[3],new Integer(6));
        disp[3].setBounds(1200,85,200,50);

        String formattedPartD=String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 180, partD);
        disp[4] = new JLabel(formattedPartD);
        disp[4].setFont(disp[3].getFont().deriveFont((float)15));
        add(disp[4],new Integer(7));
        disp[4].setBounds(1200,120,200,50);

        String formattedPartR=String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 180, partR);
        disp[5] = new JLabel(formattedPartR);
        disp[5].setFont(disp[3].getFont().deriveFont((float)15));
        add(disp[5],new Integer(8));
        disp[5].setBounds(1200,155,200,50);
        setVisible(true);
    }
}