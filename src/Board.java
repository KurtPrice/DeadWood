import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;

/**
 * Created by pricek21 on 5/2/17.
 */
public class Board {

    private static UIText UI = new UIText();
    private static int numPlayer = UI.numberPlayers();
    private ArrayList<Player> playerList;
    private int numDays;
    private int numScenes;
    private String winner;


    public static void main(String args[]) {
        startGame(numPlayer);
        Scene[] sceneArray = readSceneXML();
        Room[] roomArray = readBoardXML();
    }

    /**
     * Scene XML parser.
     */
    private static Scene[] readSceneXML() {
        try {
            Scene[] sceneArray = new Scene[40];

            File fXmlFile = new File("src/cards.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nListScene = doc.getElementsByTagName("card");
            NodeList nListRole = doc.getElementsByTagName("part");

            int roleXmlPos = 0;
            for (int temp = 0; temp < nListScene.getLength(); temp++) {

                Node nNodeScene = nListScene.item(temp);

                if (nNodeScene.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElementScene = (Element) nNodeScene;

                    String sName = eElementScene.getAttribute("name");
                    String sBudget = eElementScene.getAttribute("budget");
                    String sDescription = eElementScene.getElementsByTagName("scene").item(0).getTextContent();

                    Role[] sRoles = new Role[4];
                    int roleIndex = 0;

                    Node nNodeRole = nListRole.item(roleXmlPos);
                    while (nNodeScene.compareDocumentPosition(nNodeRole) == 20 && nNodeRole != null) {
                        if (nNodeRole.getNodeType() == Node.ELEMENT_NODE) {

                            Element eElementRole = (Element) nNodeRole;

                            String pName = eElementRole.getAttribute("name");
                            String pRank = eElementRole.getAttribute("level");
                            String pDescription = eElementRole.getElementsByTagName("line").item(0).getTextContent();
                            Role r = new Role(pName, pDescription, Integer.parseInt(pRank), true);
                            sRoles[roleIndex] = r;
                            roleIndex++;
                        }
                        roleXmlPos++;
                        if (roleXmlPos < nListRole.getLength()) {
                            nNodeRole = nListRole.item(roleXmlPos);
                        } else {
                            nNodeRole = nListRole.item(0);
                        }
                    }
                    Scene s = new Scene(sName, sDescription, sRoles, Integer.parseInt(sBudget), 0);
                    sceneArray[temp] = s;
                }
            }

            return sceneArray;
        } catch (Exception e) {
            e.printStackTrace();
            Scene[] empty = new Scene[0];
            return empty;
        }
    }

    /**
     * Board XML parser.
     */
    private static Room[] readBoardXML() {
        try {
            Room[] roomArray = new Room[100];

            File fXmlFile = new File("src/board.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nListRoom = doc.getElementsByTagName("set");
            NodeList nListRole = doc.getElementsByTagName("part");

            int partXmlPos = 0;
            int temp2 = 0;
            for (int temp = 0; temp < nListRoom.getLength(); temp++) {

                Node nNodeRoom = nListRoom.item(temp);


                if (nNodeRoom.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElementRoom = (Element) nNodeRoom;

                    String sName = eElementRoom.getAttribute("name");

                    Role[] sRoles = new Role[4];
                    int roleIndex = 0;

                    Node nNodeRole = nListRole.item(partXmlPos);
                    while (nNodeRoom.compareDocumentPosition(nNodeRole) == 20 && nNodeRole != null) {
                        if (nNodeRole.getNodeType() == Node.ELEMENT_NODE) {

                            Element eElementRole = (Element) nNodeRole;

                            String pName = eElementRole.getAttribute("name");
                            String pRank = eElementRole.getAttribute("level");
                            String pDescription = eElementRole.getElementsByTagName("line").item(0).getTextContent();
                            Role r = new Role(pName, pDescription, Integer.parseInt(pRank), false);
                            sRoles[roleIndex] = r;
                            roleIndex++;
                        }
                        partXmlPos++;
                        if (partXmlPos < nListRole.getLength()) {
                            nNodeRole = nListRole.item(partXmlPos);
                        } else {
                            nNodeRole = nListRole.item(0);
                        }

                    }
                    //if();
                    Room r = new SceneRoom(sName, 0, sRoles);
                    for (int i = 0; i < (eElementRoom.getElementsByTagName("neighbor").getLength()); i++) {
                        Node neighborNode = eElementRoom.getElementsByTagName("neighbor").item(i);
                        if (nNodeRole.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElementNeighbor = (Element) neighborNode;
                            String neighborName = eElementNeighbor.getAttribute("name");
                            Room r2 = new Room(neighborName, 0);
                            r.addAdjRoom(r2);
                        }
                    }
                    roomArray[temp] = r;
                }
                temp2++;
            }
            NodeList trailer = doc.getElementsByTagName("trailer");
            Node nTrailer = trailer.item(0);
            if (nTrailer.getNodeType() == Node.ELEMENT_NODE) {

                Element eElementTrailer = (Element) nTrailer;
                Room r = new Room("trailer", 0);
                for (int i = 0; i < (eElementTrailer.getElementsByTagName("neighbor").getLength()); i++) {
                    Node neighborNode = eElementTrailer.getElementsByTagName("neighbor").item(i);
                    Element eElementNeighbor = (Element) neighborNode;
                    String neighborName = eElementNeighbor.getAttribute("name");
                    Room r2 = new Room(neighborName, 0);
                    r.addAdjRoom(r2);
                }
                roomArray[temp2] = r;
                temp2++;
            }
            NodeList office = doc.getElementsByTagName("office");
            Node nOffice = office.item(0);
            if (nOffice.getNodeType() == Node.ELEMENT_NODE) {

                Element eElementTrailer = (Element) nOffice;
                Room r = new Room("office", 0);
                for (int i = 0; i < (eElementTrailer.getElementsByTagName("neighbor").getLength()); i++) {
                    Node neighborNode = eElementTrailer.getElementsByTagName("neighbor").item(i);
                    Element eElementNeighbor = (Element) neighborNode;
                    String neighborName = eElementNeighbor.getAttribute("name");
                    Room r2 = new Room(neighborName, 0);
                    r.addAdjRoom(r2);
                }
                roomArray[temp2] = r;
            }
            return roomArray;
        } catch (Exception e) {
            e.printStackTrace();
            Room[] empty = new Room[0];
            return empty;
        }
    }

    private static void startGame(int playerCount) {
        CreatePlayers(playerCount);
    }

    private static ArrayList<Player> CreatePlayers(int playerCount) {
        ArrayList<Player> pList = new ArrayList<>();

        for (int i = 0; i < playerCount; i++) {
            String pName = ("Player" + String.valueOf(i + 1));
            PlayerCurrency pw = new PlayerCurrency(0, 0);
            //Player p = new Player(pList, Room, false, 0, pw);
            // pList.add(p);
        }

        return pList;
    }

    private void setNumDayes(int x) {
        numDays = x;
    }

    private void startDay(int playerCount) {

    }

    public int getSceneNum() {
        return numScenes;
    }

    public void decSceneNum() {
        numScenes -= 1;
    }

    private void endDay() {

    }

    private String endGame() {
        return winner;
    }

    private void displayWinner(String congratMessage) {
        System.out.println(congratMessage + " " + winner);
    }

}
