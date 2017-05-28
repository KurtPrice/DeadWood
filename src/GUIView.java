/**
 * Created by Tom on 5/24/2017.
 */

import javafx.scene.control.TextInputDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JLabel die;
    private JLabel[] disp = new JLabel[6];

    private JButton moveButton;
    private JButton workButton;
    private JButton upgradeButton;
    private JButton rehearseButton;
    private JButton actButton;
    private JButton endButton;

    public GUIView () {
        Resources r = Resources.getInstance();

        setSize(1400,900);
        setDoubleBuffered(true);

        background = new JLabel();
        add(background, new Integer(0));
        background.setBounds(0,0,1200,900);
        background.setIcon(r.getBG());


        float[] hsbVal = new float[3];
        hsbVal = Color.RGBtoHSB(177,114,70,hsbVal);

        moveButton = new JButton("Move");
        moveButton.setFont(moveButton.getFont().deriveFont((float)30));
        add(moveButton);
        moveButton.setBounds(1205,200,200,100);
        moveButton.setBackground(Color.getHSBColor(hsbVal[0],hsbVal[1],hsbVal[2]));
        moveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (GUI.getMoveAble()) {
                    Room[] roomOptions = GUI.cPlayer.getPlayerLoc().getAdjRooms();
                    String[] names = new String[roomOptions.length];
                    for (int i = 0; i < roomOptions.length; i++) {
                        names[i] = roomOptions[i].getRoomName();
                    }
                    String var = (String) JOptionPane.showInputDialog(null, "Select a room", "Select a Room",
                            JOptionPane.PLAIN_MESSAGE, null,
                            names, "Move");
                    for (Room r : GUI.rArray) {
                        if (r.getRoomName().equals(var)) {
                            GUI.cPlayer.move(r);
                            GUI.cPlayer.getPlayerLoc().removePlayer(GUI.cPlayer);
                            r.addPlayer(GUI.cPlayer);
                            movePlayer(r);
                        }
                    }
                    GUI.setMoveAble(false);
                }
            }
        });

        workButton = new JButton("Work");
        workButton.setFont(workButton.getFont().deriveFont((float)30));
        add(workButton);
        workButton.setBounds(1205,300,200,100);
        workButton.setBackground(Color.getHSBColor(hsbVal[0],hsbVal[1],hsbVal[2]));

        upgradeButton = new JButton("Upgrade");
        upgradeButton.setFont(upgradeButton.getFont().deriveFont((float)30));
        add(upgradeButton);
        upgradeButton.setBounds(1205,400,200,100);
        upgradeButton.setBackground(Color.getHSBColor(hsbVal[0],hsbVal[1],hsbVal[2]));

        rehearseButton = new JButton("Rehearse");
        rehearseButton.setFont(rehearseButton.getFont().deriveFont((float)30));
        add(rehearseButton);
        rehearseButton.setBounds(1205,500,200,100);
        rehearseButton.setBackground(Color.getHSBColor(hsbVal[0],hsbVal[1],hsbVal[2]));

        actButton = new JButton("Act");
        actButton.setFont(actButton.getFont().deriveFont((float)30));
        add(actButton);
        actButton.setBounds(1205,600,200,100);
        actButton.setBackground(Color.getHSBColor(hsbVal[0],hsbVal[1],hsbVal[2]));

        endButton = new JButton("End");
        endButton.setFont(endButton.getFont().deriveFont((float)30));
        add(endButton);
        endButton.setBounds(1205,700,200,100);
        endButton.setBackground(Color.getHSBColor(hsbVal[0],hsbVal[1],hsbVal[2]));
        endButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                remove(disp[0]);
                remove(disp[1]);
                remove(disp[2]);
                remove(disp[3]);
                remove(disp[4]);
                GUI.endTurn();
            }
        });
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

    public void setPlayers(Player[] playerList){
        Resources r = Resources.getInstance();
        for(int i=0; i<playerList.length; i++){
            int playerDie = ((6*i)+(playerList[i].getPlayerRank()-1));
            die = new JLabel();
           add(die, new Integer(i+1));
           die.setBounds(1050,270+(50*i),40,40);
           die.setIcon(r.getDice(playerDie));
           playerList[i].setLabel(die);
           setVisible(true );
        }
        repaint();
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

    public void updatePlayerDisp(String name, String dollars, String credits, String part, String partD){
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
        setVisible(true);
    }
    private void movePlayer(Room r){
        JLabel mLabel = GUI.cPlayer.getLabel();
        int pNum = r.getPlayersInRoom().size();
        if(r.getSceneRoom()){
            SceneRoom sr = (SceneRoom) r;
            int [] area = sr.getArea();
            mLabel.setBounds(area[0]+(50*(pNum-1)),area[1]+125,40,40);
            setVisible(true);
        }else if(r.getRoomName().equals("trailer")){
            mLabel.setBounds(1050,270+(50*pNum),40,40);
        }else if(r.getRoomName().equals("office")){
            mLabel.setBounds(75,420+(50*pNum),40,40);
        }

    }
}