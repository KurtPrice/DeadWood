import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.Random;

/**
 * Created by pricek21 on 5/2/17.
 */
public class Board {

    private static UIText UI = new UIText();
    private static int numPlayer = UI.numberPlayers();
    private ArrayList<Player> playerList;
    private static ArrayList<Integer> usedScenes = new ArrayList<>();
    private static Scene[] sceneArray;
    private static Room[] roomArray;
    private static int numDays = 4;
    private int numScenes;
    private String winner;


    public static void main(String args[]) {
        sceneArray = readSceneXML();
        roomArray = readBoardXML();
        Player[] playerList = startGame(numPlayer);
        for(int i = 0; i < numDays; i++){
            startDay(playerList);
        }
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
                    Role[] shrunkRoles = new Role[roleIndex];
                    for(int i=0; i<roleIndex; i++){
                        shrunkRoles[i] = sRoles[i];
                    }
                    Scene s = new Scene(sName, sDescription, shrunkRoles, Integer.parseInt(sBudget), 0);
                    sceneArray[temp] = s;
                }
            }
            return sceneArray;
        } catch (Exception e) {
            e.printStackTrace();
            Scene[] s = new Scene[0];
            return s;
        }
    }

    /**
     * Board XML parser.
     */
    private static Room[] readBoardXML() {
        try {
            Room[] roomArray = new Room[12];
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
                    Role[] shrunkRoles = new Role[roleIndex];
                    for(int i=0; i<roleIndex; i++){
                        shrunkRoles[i] = sRoles[i];
                    }
                    SceneRoom r = new SceneRoom(sName, 0, shrunkRoles);
                    r.setSceneRoom(true);
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
            Room[] r = new Room[0];
            return  r;
        }
    }

    /**
     *Method that chooses initial conditions based off of number of players.
     */
    private static Player[] startGame(int playerCount)
    {
        int startRank = 1;
        int startCredit = 0;
        switch (playerCount){
            case 2: numDays = 3;
                break;
            case 3: numDays = 3;
                break;
            case 5: startCredit = 2;
                break;
            case 6:  startCredit = 4;
                break;
            case 7: startRank = 2;
                break;
            case 8: startRank = 2;
                break;
            default:
                break;
        }
        Player[] pList = CreatePlayers(playerCount, startRank, startCredit);
      return pList;
    }

    /**
     *Method that creates player objects with initial conditions as passed.
     */
    private static Player[] CreatePlayers(int playerCount, int startRank, int startCredit) {
        Player[] pList = new Player[playerCount];

        for (int i = 0; i < playerCount; i++) {
            String pName = ("Player " + String.valueOf(i + 1));
            PlayerCurrency pw = new PlayerCurrency(startCredit, 0);
            Player p = new Player(pName, startRank, roomArray[10], false, 0, pw);
            pList[i]=p;
        }
        return pList;
    }

    private void setNumDayes(int x) {
        numDays = x;
    }

    private static void startDay(Player [] playerList) {

        for(int i=0; i<10; i++){
           setScenes(i);
        }
        for (int i = 0; i < playerList.length; i++){
                UI.turn(playerList[i],roomArray);
            }
    }

    private static int setScenes(int i){
        Random rand = new Random();
        SceneRoom sr  = (SceneRoom)roomArray[i];
        int n = rand.nextInt(39);
        if(usedScenes.contains(n)){
            return setScenes(i);
        }else{
            sr.setRoomScene(sceneArray[n]);
            usedScenes.add(n);}
            return 0;
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
