import java.util.*;

/**
 * Created by pricek21 on 5/2/17.
 */
public class Scene {

    private String sceneName;
    private String sceneDescription;
    private Role[] roleList;
    private int budget;

    public Scene(String name, String description, Role[] list, int money){
        sceneName = name;
        sceneDescription = description;
        roleList = list;
        budget = money;
    }

    public String getSceneName(){
        return sceneName;
    }

    public String getSceneDescription(){
        return sceneDescription;
    }

    public Role[] getRoleList(){
        return roleList;
    }

    public int getBudget(){
        return budget;
    }

    //Needs debugging.
    public void finishScene(SceneRoom room) {
        boolean bonusPay = false;
        ArrayList<Player> pInRoom = room.getPlayersInRoom();
        ArrayList<Player> pOnCard = new ArrayList<>();
        ArrayList<Player> pOffCard = new ArrayList<>();
        for (Player p : pInRoom) {
            if (p.getRole().getOnCardRole()) {
                bonusPay = true;
                pOnCard.add(p);
            }else{pOffCard.add(p);}
        }
        if (bonusPay) {
            ArrayList<Player> pOrdered = new ArrayList<>();
            Player highPlayer = pOnCard.get(0);

            ArrayList<Integer> payDice = new ArrayList<>();
            ArrayList<Integer> dieOrdered = new ArrayList<>();
            int highDie = 0;

            Random ran = new Random();
            while (!(pOnCard.isEmpty())) {
                for (Player p : pOnCard) {
                    if (highPlayer.getRole().getRoleRank() < p.getRole().getRoleRank()) {
                        highPlayer = p;
                    }
                }
                pOrdered.add(highPlayer);
                pOnCard.remove(highPlayer);
            }

            for (int j = 0; j < budget; j++) {
                int n = (ran.nextInt(6) + 1);
                System.out.println("Rolled a " + n + "!");
                payDice.add(n);
            }

            while (!(payDice.isEmpty())) {
                for (Integer d : payDice) {
                    if (highDie < d) {
                        highDie = d;
                    }
                }
                dieOrdered.add(highDie);
                payDice.remove(highDie);
            }
            bonusPaymentOnCard(pOrdered, dieOrdered);
            bonusPaymentOffCard(pOffCard);
        }
        for (Player p : pInRoom) {
            p.leaveRole();
            p.pracChips = 0;
        }
    }

    private void bonusPaymentOnCard(ArrayList<Player> pList, ArrayList<Integer> dList){
        for(int i=0; i<dList.size();i++){
           pList.get((i%pList.size())).getWallet().incDollars(dList.get(i));
        }
    }

    private void bonusPaymentOffCard(ArrayList<Player> pList){
        for(Player p : pList) p.getWallet().incDollars(p.getRole().getRoleRank());
    }
}
