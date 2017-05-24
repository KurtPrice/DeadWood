import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created by Tom on 5/14/2017.
 * Class that parses the Scene XML file.
 */

public class ReadSceneXML {

    /**
     * Method: read
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for parsing the scene XML file into usable/accessible
     * Java objects.
     *
     * Return(s): Scene array that contains all of the scenes to be used in the game.
     */
    public static Scene[] read() {
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
                    String sImage = eElementScene.getAttribute("img");
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
                    System.arraycopy(sRoles,0,shrunkRoles,0,roleIndex);
                    Scene s = new Scene(sName, sDescription, shrunkRoles, Integer.parseInt(sBudget));
                    s.setSceneImage(sImage);
                    sceneArray[temp] = s;
                }
            }
            return sceneArray;
        } catch (Exception e) {
            e.printStackTrace();
            return new Scene[0];
        }
    }
}