/**
 * Created by Tom on 5/24/2017.
 */

import java.util.LinkedList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.awt.Color;
//import model.GUI;


public class GUIView
        extends JLayeredPane{
        //implements GUI.Listener {

    private JLabel background;
    private JLabel cardBack;
    private JLabel scene;
    private JLabel shot;
    //private JLabel hOne;

    //private JLabel mTen;
    //private JLabel mOne;

    //private JLabel sTen;
    //private JLabel sOne;


    public GUIView () {
        Resources r = Resources.getInstance();

        setSize(1200,900);
        setDoubleBuffered(true);

        background = new JLabel();
        add(background, new Integer(0));
        background.setBounds(0,0,1200,900);
        background.setIcon(r.getBG());

       // for(int i = 0; i<10;)
        //cardBack = new JLabel();
        //add(cardBack, new Integer(1));
       // cardBack.setBounds(0,0,100,167);

        setVisible(true);
        setFocusable(true);
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
}