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

    //private JLabel hTen;
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

       // hTen = new JLabel();
        //add(hTen, new Integer(1));
        //hTen.setBounds(0,0,100,167);

        setVisible(true);
        setFocusable(true);
    }

    public void changed() {
        Resources r = Resources.getInstance();

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

}