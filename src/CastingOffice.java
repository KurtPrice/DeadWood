import java.util.HashMap;

/**
 * Created by pricek21 on 5/2/17.
 */
public class CastingOffice extends Room {
    //First integer is the rank, second is the amount
    private HashMap<Integer, Integer> rankRequireDollar = new HashMap<>();
    private HashMap<Integer, Integer> rankRequireCredit = new HashMap<>();

    public CastingOffice(String name, int availableCount) {
        super(name, availableCount);
    }

    public HashMap<Integer, Integer> getRankRequireDollar(){
        return rankRequireDollar;
    }

    public HashMap<Integer, Integer> getRankRequireCredit(){
        return rankRequireCredit;
    }

    public void setRankRequireDollar(Integer key, Integer value){
        rankRequireDollar.put(key,value);
    }

    public void setRankRequireCredit(Integer key, Integer value){
        rankRequireCredit.put(key,value);
    }

    public boolean rankPlayerCredit(int goalRank, Player player){
        if(player.getWallet().getDollars() >= rankRequireCredit.get(goalRank)){
            player.setPlayerRank(goalRank);
            return true;
        }
        return false;
    }

    public boolean rankPlayerDollar(int goalRank, Player player){
        if(player.getWallet().getDollars() >= rankRequireDollar.get(goalRank)){
            player.setPlayerRank(goalRank);
            return true;
        }
        return false;
    }
}
