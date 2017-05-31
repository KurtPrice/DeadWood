/**
 * Created by Tom on 5/24/2017.
 */

import javafx.scene.control.TextInputDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
//import model.GUI;


public class GUIView
        extends JLayeredPane{
        //implements GUI.Listener {

    private JLabel background;
    private JLabel[] cardBack = new JLabel[10];
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
                if (GUI.getMoveAble() && !GUI.cPlayer.getRoleTaken()) {
                    Room[] roomOptions = GUI.cPlayer.getPlayerLoc().getAdjRooms();
                    String[] names = new String[roomOptions.length];
                    for (int i = 0; i < roomOptions.length; i++) {
                        names[i] = roomOptions[i].getRoomName();
                    }
                    String var = (String) JOptionPane.showInputDialog(null, "Select a room", "Select a Room",
                            JOptionPane.PLAIN_MESSAGE, null,
                            names, "Move");
                    for (int i = 0; i<GUI.rArray.length; i++) {
                        if (GUI.rArray[i].getRoomName().equals(var)) {
                            if(GUI.rArray[i].getSceneRoom() && GUI.rArray[i].getPlayersInRoom().isEmpty()){
                                SceneRoom sr = (SceneRoom)GUI.rArray[i];
                                remove(cardBack[i]);
                                repaint();
                            }
                            GUI.cPlayer.move(GUI.rArray[i]);
                            GUI.cPlayer.getPlayerLoc().removePlayer(GUI.cPlayer);
                            GUI.rArray[i].addPlayer(GUI.cPlayer);
                            movePlayer(GUI.rArray[i]);
                            GUI.setMoveAble(false);
                        }
                    }
                }
            }
        });

        workButton = new JButton("Work");
        workButton.setFont(workButton.getFont().deriveFont((float)30));
        add(workButton);
        workButton.setBounds(1205,300,200,100);
        workButton.setBackground(Color.getHSBColor(hsbVal[0],hsbVal[1],hsbVal[2]));
        workButton.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (GUI.cPlayer.getRoleTaken() == false && GUI.cPlayer.getPlayerLoc().getSceneRoom()) {
                SceneRoom sr = (SceneRoom)GUI.cPlayer.getPlayerLoc();
                Role[] roleOptions = sr.getRoleList();
                ArrayList<String> names = new ArrayList<>();
                for (int i = 0; i < roleOptions.length; i++) {
                    if(roleOptions[i].getRoleRank()<=GUI.cPlayer.getPlayerRank() && !roleOptions[i].getRoleOccupancy()){
                        names.add(roleOptions[i].getRoleName());
                    }
                }
                for (int i = 0; i < sr.getRoomScene().getRoleList().length; i++) {
                    if(sr.getRoomScene().getRoleList()[i].getRoleRank()<=GUI.cPlayer.getPlayerRank() && !sr.getRoomScene().getRoleList()[i].getRoleOccupancy()){
                        names.add(sr.getRoomScene().getRoleList()[i].getRoleName());
                    }
                }
                Object[] namesArray = names.toArray();
                if(!names.isEmpty()) {
                    String var = (String) JOptionPane.showInputDialog(null, "Select a role", "Select a Role",
                            JOptionPane.PLAIN_MESSAGE, null,
                            namesArray, "Work");
                    for (Role r : sr.getRoleList()) {
                        if (r.getRoleName().equals(var)) {
                            GUI.cPlayer.takeRole(r);
                            takeRolePlayer(sr, r);
                            GUI.setActAble(false);
                            r.setRoleOccupancy(true);
                        }
                    }
                    for (Role r : sr.getRoomScene().getRoleList()) {
                        if (r.getRoleName().equals(var)) {
                            GUI.cPlayer.takeRole(r);
                            takeRolePlayer(sr, r);
                            GUI.setActAble(false);
                            r.setRoleOccupancy(true);
                        }
                    }
                }
            }
        }
    });

        upgradeButton = new JButton("Upgrade");
        upgradeButton.setFont(upgradeButton.getFont().deriveFont((float)30));
        add(upgradeButton);
        upgradeButton.setBounds(1205,400,200,100);
        upgradeButton.setBackground(Color.getHSBColor(hsbVal[0],hsbVal[1],hsbVal[2]));
        upgradeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent){
                Player current = GUI.cPlayer;
                if(current.getPlayerLoc().getRoomName() == "office"){
                    CastingOffice office = (CastingOffice) current.getPlayerLoc();
                    ArrayList<Integer> ranks = current.getUpgrades();
                    boolean success;
                    int rank = (int) JOptionPane.showInputDialog(null, "Select a rank",
                            "Select a rank",
                            JOptionPane.PLAIN_MESSAGE, null,
                            ranks.toArray(), "Upgrade");

                    success = office.rankPlayerCredit(rank, current);
                    if(!success){
                        success = office.rankPlayerDollar(rank, current);
                    }
                    if(!success){
                        JOptionPane.showMessageDialog(null, "Error attempting to upgrade!");
                    } else{
                        JOptionPane.showMessageDialog(null, "Player upgraded to " + rank);
                    }
                }
            }
        });

        rehearseButton = new JButton("Rehearse");
        rehearseButton.setFont(rehearseButton.getFont().deriveFont((float)30));
        add(rehearseButton);
        rehearseButton.setBounds(1205,500,200,100);
        rehearseButton.setBackground(Color.getHSBColor(hsbVal[0],hsbVal[1],hsbVal[2]));
        rehearseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Player current = GUI.cPlayer;
                if (!GUI.getActAble()) {
                    JOptionPane.showMessageDialog(null,"You have already acted or rehearsed.");
                    return;
                }
                if (current.getRoleTaken()) {
                    SceneRoom sr = (SceneRoom) current.getPlayerLoc();
                    int budget = sr.getRoomScene().getBudget();
                    if (budget > (current.pracChips + 1)) {
                        current.pracChips++;
                        GUI.setActAble(false);
                        JOptionPane.showMessageDialog(null,"One practice chip added.");
                    } else {
                        JOptionPane.showMessageDialog(null,"Max practice chips, you must act.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"No role to rehearse.");
                }
            }
        });

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

    public void setScene(int x,int y, int tag) {
        Resources r = Resources.getInstance();
        cardBack[tag] = new JLabel();
        add(cardBack[tag], new Integer(tag+1));
        cardBack[tag].setBounds(x,y,205,115);
        cardBack[tag].setIcon(r.getCB());

        setVisible(true);
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
        background.add(scene);

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
            mLabel.setBounds(1050,270+(50*(pNum-1)),40,40);
        }else if(r.getRoomName().equals("office")){
            mLabel.setBounds(75,420+(50*(pNum-1)),40,40);
        }

    }
    private void takeRolePlayer(SceneRoom sr,Role r){
        Player currentPlayer = GUI.cPlayer;
        JLabel mLabel = currentPlayer.getLabel();

        if(r.getOnCardRole()){
            for(int i=0; i<sr.getRoomScene().getRoleList().length; i++){
                if(sr.getRoomScene().getRoleList()[i].getRoleRank() == r.getRoleRank()){
                    int x = sr.getArea()[0];
                    int y = sr.getArea()[1];
                    double xAdj = 205.0*(1.0/(1.0 + sr.getRoomScene().getRoleList().length))*(i+1.0);
                    int xCoordinate = x+(int)Math.round(xAdj)-30;
                    mLabel.setBounds(xCoordinate,y+48,40,40);

                    setVisible(true);
                }
            }
        }else{
            for(Role rr : sr.getRoleList()){
                if(rr.getRoleName().equals(r.getRoleName())){
                    int [] area = rr.getRoleArea();
                    mLabel.setBounds(area[0]+3,area[1]+3,40,40);
                }
            }
        }
        remove(disp[0]);
        remove(disp[1]);
        remove(disp[2]);
        remove(disp[3]);
        remove(disp[4]);
        updatePlayerDisp(currentPlayer.getPlayerName() + "'s turn",
                "$" + currentPlayer.getWallet().getDollars(),
                currentPlayer.getWallet().getCredits() + "cd",
                "Current Part: " + currentPlayer.getRoleName(),
                "Part: " + currentPlayer.getRoleDesc());
            setVisible(true);

    }
}