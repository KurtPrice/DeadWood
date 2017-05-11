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
        Scene[] sceneArray = ReadXML();
    }

    public static Scene[] ReadXML() {
        try {
            Scene[] sceneArray = new Scene[40];

            File fXmlFile = new File("C:/Users/Tom/IdeaProjects/DeadWood/src/cards.xml");
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
