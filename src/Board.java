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


    public static void main(String args[]){
        startGame(numPlayer);
        ReadXML();
    }

    public static void ReadXML(){
        ArrayList<Scene> sceneList;
        try {

            File fXmlFile = new File("/home/eddyt/IdeaProjects/DeadWood/src/cards.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("card");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    String sName = eElement.getAttribute("name");
                    String sBudget = eElement.getAttribute("budget");
                    String sDescription = eElement.getElementsByTagName("scene").item(0).getTextContent();

                    //Scene s = new Scene(sName, sDescription, Role[], 0);
                    //sceneList.add(s);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startGame(int playerCount){
        CreatePlayers(playerCount);
    }

    private static ArrayList<Player> CreatePlayers(int playerCount){
        ArrayList<Player> pList = new ArrayList<>();

        for(int i=0; i<playerCount; i++){
            String pName = ("Player"+ String.valueOf(i+1));
            PlayerCurrency pw = new PlayerCurrency(0,0);
            //Player p = new Player(pList, Room, false, 0, pw);
           // pList.add(p);
        }

        return pList;
    }

    private void setNumDayes(int x){
        numDays = x;
    }

    private void startDay(int playerCount){

    }

    public int getSceneNum(){
        return numScenes;
    }

    public void decSceneNum(){
        numScenes -= 1;
    }

    private void endDay(){

    }

    private String endGame(){
        return winner;
    }

    private void displayWinner(String congratMessage){
        System.out.println(congratMessage + " " + winner);
    }

}
