
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


/**
 * Created by Tom on 5/24/2017.
 */
public class GUIView
        extends JLayeredPane {
    private JLabel background;
    private JLabel[] cardBack = new JLabel[10];
    private JLabel die;
    private JLabel[] disp = new JLabel[6];

    public GUIView() {
        Resources r = Resources.getInstance();

        setSize(1400, 900);
        setDoubleBuffered(true);

        background = new JLabel();
        add(background, new Integer(0));
        background.setBounds(0, 0, 1200, 900);
        background.setIcon(r.getBG());


        float[] hsbVal = new float[3];
        hsbVal = Color.RGBtoHSB(177, 114, 70, hsbVal);

        JButton moveButton = new JButton("Move");
        moveButton.setFont(moveButton.getFont().deriveFont((float) 30));
        add(moveButton);
        moveButton.setBounds(1205, 200, 200, 100);
        moveButton.setBackground(Color.getHSBColor(hsbVal[0], hsbVal[1], hsbVal[2]));
        moveButton.addActionListener(actionEvent -> {
            if (GUI.getMoveAble() && !GUI.cPlayer.getRoleTaken()) {
                Room[] roomOptions = GUI.cPlayer.getPlayerLoc().getAdjRooms();
                String[] names = new String[roomOptions.length];
                for (int i = 0; i < roomOptions.length; i++) {
                    names[i] = roomOptions[i].getRoomName();
                }
                String var = (String) JOptionPane.showInputDialog(null, "Select a room", "Select a Room",
                        JOptionPane.PLAIN_MESSAGE, null,
                        names, "Move");
                for (int i = 0; i < GUI.rArray.length; i++) {
                    if (GUI.rArray[i].getRoomName().equals(var)) {
                        if (GUI.rArray[i].getSceneRoom() && GUI.rArray[i].getPlayersInRoom().isEmpty()) {
                            SceneRoom sr = (SceneRoom) GUI.rArray[i];
                            remove(cardBack[i]);
                        }
                        GUI.cPlayer.move(GUI.rArray[i]);
                        GUI.cPlayer.getPlayerLoc().removePlayer(GUI.cPlayer);
                        GUI.rArray[i].addPlayer(GUI.cPlayer);
                        movePlayer(GUI.rArray[i]);
                        GUI.setMoveAble(false);
                    }
                }
            }
            repaint();
        });

        JButton workButton = new JButton("Work");
        workButton.setFont(workButton.getFont().deriveFont((float) 30));
        add(workButton);
        workButton.setBounds(1205, 300, 200, 100);
        workButton.setBackground(Color.getHSBColor(hsbVal[0], hsbVal[1], hsbVal[2]));
        workButton.addActionListener(actionEvent -> {
            if (!GUI.cPlayer.getRoleTaken() && GUI.cPlayer.getPlayerLoc().getSceneRoom()) {
                SceneRoom sr = (SceneRoom) GUI.cPlayer.getPlayerLoc();
                Role[] roleOptions = sr.getRoleList();
                ArrayList<String> names = new ArrayList<>();
                for (Role roleOption : roleOptions) {
                    if (roleOption.getRoleRank() <= GUI.cPlayer.getPlayerRank() && !roleOption.getRoleOccupancy()) {
                        names.add(roleOption.getRoleName());
                    }
                }
                for (int i = 0; i < sr.getRoomScene().getRoleList().length; i++) {
                    if (sr.getRoomScene().getRoleList()[i].getRoleRank() <= GUI.cPlayer.getPlayerRank() && !sr.getRoomScene().getRoleList()[i].getRoleOccupancy()) {
                        names.add(sr.getRoomScene().getRoleList()[i].getRoleName());
                    }
                }
                Object[] namesArray = names.toArray();
                if (!names.isEmpty()) {
                    String var = (String) JOptionPane.showInputDialog(null, "Select a role", "Select a Role",
                            JOptionPane.PLAIN_MESSAGE, null,
                            namesArray, "Work");
                    for (Role r1 : sr.getRoleList()) {
                        if (r1.getRoleName().equals(var)) {
                            GUI.cPlayer.takeRole(r1);
                            takeRolePlayer(sr, r1);
                            GUI.setActAble(false);
                            r1.setRoleOccupancy(true);
                        }
                    }
                    for (Role r1 : sr.getRoomScene().getRoleList()) {
                        if (r1.getRoleName().equals(var)) {
                            GUI.cPlayer.takeRole(r1);
                            takeRolePlayer(sr, r1);
                            GUI.setActAble(false);
                            r1.setRoleOccupancy(true);
                        }
                    }
                }
            }
        });

        JButton upgradeButton = new JButton("Upgrade");
        upgradeButton.setFont(upgradeButton.getFont().deriveFont((float) 30));
        add(upgradeButton);
        upgradeButton.setBounds(1205, 400, 200, 100);
        upgradeButton.setBackground(Color.getHSBColor(hsbVal[0], hsbVal[1], hsbVal[2]));
        upgradeButton.addActionListener(actionEvent -> {
            Player current = GUI.cPlayer;
            if (Objects.equals(current.getPlayerLoc().getRoomName(), "office")) {
                CastingOffice office = (CastingOffice) current.getPlayerLoc();
                initOfficeCosts(office);
                ArrayList<Integer> ranks = current.getUpgrades(office);
                boolean success;
                int rank = (int) JOptionPane.showInputDialog(null, "Select a rank",
                        "Select a rank",
                        JOptionPane.PLAIN_MESSAGE, null,
                        ranks.toArray(), "Upgrade");

                success = office.rankPlayerCredit(rank, current);
                if (!success) {
                    success = office.rankPlayerDollar(rank, current);
                }
                if (!success) {
                    JOptionPane.showMessageDialog(null, "Error attempting to upgrade!");
                } else {
                    JOptionPane.showMessageDialog(null, "Player upgraded to " + rank);
                    updatePlayers(Deadwood.playerList);
                }
            }
        });

        JButton rehearseButton = new JButton("Rehearse");
        rehearseButton.setFont(rehearseButton.getFont().deriveFont((float) 30));
        add(rehearseButton);
        rehearseButton.setBounds(1205, 500, 200, 100);
        rehearseButton.setBackground(Color.getHSBColor(hsbVal[0], hsbVal[1], hsbVal[2]));
        rehearseButton.addActionListener(actionEvent -> {
            Player current = GUI.cPlayer;
            if (!GUI.getActAble()) {
                JOptionPane.showMessageDialog(null, "You have already acted or rehearsed.");
                return;
            }
            if (current.getRoleTaken()) {
                SceneRoom sr = (SceneRoom) current.getPlayerLoc();
                int budget = sr.getRoomScene().getBudget();
                if (budget > (current.pracChips + 1)) {
                    current.pracChips++;
                    GUI.setActAble(false);
                    JOptionPane.showMessageDialog(null, "One practice chip added.");
                } else {
                    JOptionPane.showMessageDialog(null, "Max practice chips, you must act.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No role to rehearse.");
            }
        });

        JButton actButton = new JButton("Act");
        actButton.setFont(actButton.getFont().deriveFont((float) 30));
        add(actButton);
        actButton.setBounds(1205, 600, 200, 100);
        actButton.setBackground(Color.getHSBColor(hsbVal[0], hsbVal[1], hsbVal[2]));
        actButton.addActionListener((ActionEvent actionEvent) -> {
            Player currentPlayer = GUI.cPlayer;
            if (GUI.getActAble() && currentPlayer.getRoleTaken()) {
                GUI.setActAble(false);
                SceneRoom sr = (SceneRoom) currentPlayer.getPlayerLoc();
                int budget = sr.getRoomScene().getBudget();
                Random ran = new Random();
                int dieRole = (ran.nextInt(6) + 1);
                if (budget <= (currentPlayer.pracChips + dieRole)) {
                    int[] shotCoordRemove = sr.getShotArea(sr.tempShotCounters - 1);
                    if (currentPlayer.getRole().getOnCardRole()) {
                        JLabel finishedShot = (JLabel) background.findComponentAt(shotCoordRemove[0], shotCoordRemove[1]);
                        background.remove(finishedShot);
                        sr.tempShotCounters--;
                        currentPlayer.getWallet().incCredits(2);
                    } else {
                        JLabel finishedShot = (JLabel) background.findComponentAt(shotCoordRemove[0], shotCoordRemove[1]);
                        background.remove(finishedShot);
                        sr.tempShotCounters--;
                        currentPlayer.getWallet().incDollars(1);
                        currentPlayer.getWallet().incCredits(1);
                    }
                    JOptionPane.showMessageDialog(null, "Act successful!");
                } else {
                    if (!currentPlayer.getRole().getOnCardRole()) {
                        currentPlayer.getWallet().incDollars(1);
                    }
                    JOptionPane.showMessageDialog(null, "Act unsuccessful.");
                }
                if (sr.tempShotCounters == 0) {
                    JOptionPane.showMessageDialog(null, "That's a wrap!");
                    sr.setFinishScene(true);
                    JLabel finishedScene = (JLabel) background.findComponentAt(sr.getArea()[0], sr.getArea()[1]);
                    background.remove(finishedScene);
                    GUI.setMoveAble(false);
                    movePlayer(sr);
                    sr.getRoomScene().finishScene(sr);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No role to act.");
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
            repaint();
        });

        JButton endButton = new JButton("End");
        endButton.setFont(endButton.getFont().deriveFont((float) 30));
        add(endButton);
        endButton.setBounds(1205, 700, 200, 100);
        endButton.setBackground(Color.getHSBColor(hsbVal[0], hsbVal[1], hsbVal[2]));
        endButton.addActionListener(actionEvent -> {
            remove(disp[0]);
            remove(disp[1]);
            remove(disp[2]);
            remove(disp[3]);
            remove(disp[4]);
            GUI.endTurn();
        });
        setVisible(true);


    }

    public void setCardBack(int x, int y, int tag) {
        Resources r = Resources.getInstance();
        cardBack[tag] = new JLabel();
        add(cardBack[tag], new Integer(tag + 1));
        cardBack[tag].setBounds(x, y, 205, 115);
        cardBack[tag].setIcon(r.getCB());

        setVisible(true);
    }

    public void setShotCounter(int x, int y) {
        Resources r = Resources.getInstance();

        JLabel shot = new JLabel();
        background.add(shot, new Integer(1));
        shot.setBounds(x, y, 47, 47);
        shot.setIcon(r.getShotCounter());

        setVisible(true);
        setFocusable(true);
    }

    public void setPlayers(Player[] playerList) {
        Resources r = Resources.getInstance();
        for (int i = 0; i < playerList.length; i++) {
            int playerDie = ((6 * i) + (playerList[i].getPlayerRank() - 1));
            die = new JLabel();
            add(die, new Integer(i + 1));
            if (i < 4) {
                die.setBounds(1050, 250 + (50 * i), 40, 40);
            } else {
                die.setBounds(1100, 250 + (50 * (i - 4)), 40, 40);
            }
            die.setIcon(r.getDice(playerDie));
            playerList[i].setLabel(die);
            setVisible(true);
        }
        repaint();
    }

    private void updatePlayers(Player[] playerList) {
        Resources r = Resources.getInstance();
        for (int i = 0; i < playerList.length; i++) {
            if (Objects.equals(playerList[i].getPlayerLoc().getRoomName(), "office")) {
                int playerDie = ((6 * i) + (playerList[i].getPlayerRank() - 1));
                die = new JLabel();
                remove(playerList[i].getLabel());
                add(die, new Integer(i + 1));
                die.setBounds(75, 420 + (50 * (i - 1)), 40, 40);
                die.setIcon(r.getDice(playerDie));
                playerList[i].setLabel(die);
                setVisible(true);
            }
        }
        repaint();
    }

    public void setScene(int x, int y, String sceneImg) {
        Resources r = Resources.getInstance();

        JLabel scene = new JLabel();
        add(scene, new Integer(2));
        scene.setBounds(x, y, 205, 115);
        scene.setIcon(r.getScene(sceneImg));
        background.add(scene);

        setVisible(true);
        setFocusable(true);
    }

    public void updatePlayerDisp(String name, String dollars, String credits, String part, String partD) {
        disp[0] = new JLabel(name, 0);
        disp[0].setFont(disp[0].getFont().deriveFont((float) 20));
        add(disp[0], new Integer(3));
        disp[0].setBounds(1200, 0, 200, 50);

        disp[1] = new JLabel(dollars, 0);
        disp[1].setFont(disp[1].getFont().deriveFont((float) 20));
        add(disp[1], new Integer(4));
        disp[1].setBounds(1200, 25, 200, 50);

        disp[2] = new JLabel(credits, 0);
        disp[2].setFont(disp[2].getFont().deriveFont((float) 20));
        add(disp[2], new Integer(5));
        disp[2].setBounds(1200, 50, 200, 50);

        String formattedPart = String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 180, part);
        disp[3] = new JLabel(formattedPart);
        disp[3].setFont(disp[3].getFont().deriveFont((float) 15));
        add(disp[3], new Integer(6));
        disp[3].setBounds(1200, 85, 200, 50);

        String formattedPartD = String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 180, partD);
        disp[4] = new JLabel(formattedPartD);
        disp[4].setFont(disp[3].getFont().deriveFont((float) 15));
        add(disp[4], new Integer(7));
        disp[4].setBounds(1200, 120, 200, 50);
        setVisible(true);
    }

    private void movePlayer(Room r) {
        JLabel mLabel = GUI.cPlayer.getLabel();
        int pNum = r.getPlayersInRoom().size();
        if (r.getSceneRoom()) {
            SceneRoom sr = (SceneRoom) r;
            int[] area = sr.getArea();
            if (r.getPlayersInRoom().size() < 5) {
                mLabel.setBounds(area[0] + (50 * (pNum - 1)), area[1] + 125, 40, 40);
            } else {
                mLabel.setBounds(area[0] + (50 * (pNum - 5)), area[1], 40, 40);
            }
            setVisible(true);
        } else if (r.getRoomName().equals("trailer")) {
            if (r.getPlayersInRoom().size() < 5) {
                mLabel.setBounds(1050, 250 + (50 * (pNum - 1)), 40, 40);
            } else {
                mLabel.setBounds(1100, 250 + (50 * (pNum - 5)), 40, 40);
            }

        } else if (r.getRoomName().equals("office")) {
            if (r.getPlayersInRoom().size() < 5) {
                mLabel.setBounds(75, 470 + (50 * (pNum - 1)), 40, 40);
            } else {
                mLabel.setBounds(125, 420 + (50 * (pNum - 5)), 40, 40);
            }
        }

    }

    private void takeRolePlayer(SceneRoom sr, Role r) {
        Player currentPlayer = GUI.cPlayer;
        JLabel mLabel = currentPlayer.getLabel();

        if (r.getOnCardRole()) {
            for (int i = 0; i < sr.getRoomScene().getRoleList().length; i++) {
                if (sr.getRoomScene().getRoleList()[i].getRoleRank() == r.getRoleRank()) {
                    int x = sr.getArea()[0];
                    int y = sr.getArea()[1];
                    int xCoordinate;

                    if (sr.getRoomScene().getRoleList().length == 3) {
                        xCoordinate = Math.round(x + 20 + (62 * (i)));
                    } else if (sr.getRoomScene().getRoleList().length == 2) {
                        xCoordinate = Math.round(x + 52 + (63 * (i)));
                    } else {
                        xCoordinate = Math.round(x + (205 / 2) - 20);
                    }
                    mLabel.setBounds(xCoordinate, y + 48, 40, 40);

                    setVisible(true);
                }
            }
        } else {
            for (Role rr : sr.getRoleList()) {
                if (rr.getRoleName().equals(r.getRoleName())) {
                    int[] area = rr.getRoleArea();
                    mLabel.setBounds(area[0] + 3, area[1] + 3, 40, 40);
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

    private void initOfficeCosts(CastingOffice office) {
        /* Set Office Credit Requirements */
        office.setRankRequireCredit(2, 5);
        office.setRankRequireCredit(3, 10);
        office.setRankRequireCredit(4, 15);
        office.setRankRequireCredit(5, 20);
        office.setRankRequireCredit(6, 25);
        /* Set Office Dollar Requirements */
        office.setRankRequireDollar(2, 4);
        office.setRankRequireDollar(3, 10);
        office.setRankRequireDollar(4, 18);
        office.setRankRequireDollar(5, 28);
        office.setRankRequireDollar(6, 40);
    }
}