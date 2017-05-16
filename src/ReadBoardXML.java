import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created by Tom on 5/14/2017.
 * Class that parses the Deadwood XML file.
 */
public class ReadBoardXML {

    /**
     * Method: read
     *
     * Parameter(s): none
     *
     * Responsibilities: Method is responsible for parsing the board XML file into usable/accessible
     * Java objects.
     *
     * Return(s): Room array containing all of the room objects in the game.
     */
    public static Room[] read() {
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
                    System.arraycopy(sRoles,0,shrunkRoles,0,roleIndex);
                    SceneRoom r = new SceneRoom(sName, 0, shrunkRoles,eElementRoom.getElementsByTagName("take").getLength());
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

                Element eElementOffice = (Element) nOffice;
                CastingOffice co = new CastingOffice("office", 0);
                for (int i = 0; i < (eElementOffice.getElementsByTagName("neighbor").getLength()); i++) {
                    Node neighborNode = eElementOffice.getElementsByTagName("neighbor").item(i);
                    Element eElementNeighbor = (Element) neighborNode;
                    String neighborName = eElementNeighbor.getAttribute("name");
                    Room r2 = new Room(neighborName, 0);
                    co.addAdjRoom(r2);
                }
                for (int j = 0; j < (eElementOffice.getElementsByTagName("upgrade").getLength()); j++) {
                    Node upgradeNode = eElementOffice.getElementsByTagName("upgrade").item(j);
                    Element eElementUpgrade = (Element) upgradeNode;
                    String upgradeType = eElementUpgrade.getAttribute("currency");
                    if(upgradeType.equals("dollar")){
                        Integer level = Integer.parseInt(eElementUpgrade.getAttribute("level"));
                        Integer amt = Integer.parseInt(eElementUpgrade.getAttribute("amt"));
                        co.setRankRequireDollar(level,amt);
                    }else{
                        Integer level = Integer.parseInt(eElementUpgrade.getAttribute("level"));
                        Integer amt = Integer.parseInt(eElementUpgrade.getAttribute("amt"));
                        co.setRankRequireCredit(level,amt);
                    }
                }
                roomArray[temp2] = co;
            }
            return roomArray;
        } catch (Exception e) {
            e.printStackTrace();
            return new Room[0];
        }
    }
}