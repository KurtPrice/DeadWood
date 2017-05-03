import java.util.HashMap;

/**
 * Created by pricek21 on 5/2/17.
 */
public class CastingOffice extends Room {
    //First integer is the rank, second is the amount
    private HashMap<Integer, Integer> rankRequireDollar;
    private HashMap<Integer, Integer> rankRequireCredit;

    public CastingOffice(String name, int availableCount) {
        super(name, availableCount);
    }

    public HashMap<Integer, Integer> getRankRequireDollar(){
        return rankRequireDollar;
    }

    public HashMap<Integer, Integer> getRankRequireCredit(){
        return rankRequireCredit;
    }

    public void rankPlayerCredit(int goalRank, Player player){

    }

    public void rankPlayerDollar(int goalRank, Player player){

    }
}
