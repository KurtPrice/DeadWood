import java.util.HashMap;

/**
 * Created by pricek21 on 5/2/17.
 * Class that extends Room and gives methods available only to the casting office.
 */
public class CastingOffice extends Room {
    //First integer is the rank, second is the amount
    private HashMap<Integer, Integer> rankRequireDollar = new HashMap<>();
    private HashMap<Integer, Integer> rankRequireCredit = new HashMap<>();

    public CastingOffice(String name, int availableCount) {
        super(name, availableCount);
    }

    //public HashMap<Integer, Integer> getRankRequireDollar(){
    //    return rankRequireDollar;
    //}

    //public HashMap<Integer, Integer> getRankRequireCredit(){
    //    return rankRequireCredit;
    //}

    /**
     * Method: setRankRequireDollar
     *
     * Parameter(s): Method takes an Integer which will be the key value and an Integer which will be
     * the value mapped with that key.
     *
     * Responsibilities: Method is responsible for mapping key with value into HashMap.
     *
     * Return(s): nothing
     */
    public void setRankRequireDollar(Integer key, Integer value){
        rankRequireDollar.put(key,value);
    }

    /**
     * Method: setRankRequireCredit
     *
     * Parameter(s): Method takes an Integer which will be the key value and an Integer which will be
     * the value mapped with that key.
     *
     * Responsibilities: Method is responsible for mapping key with value into HashMap.
     *
     * Return(s): nothing
     */
    public void setRankRequireCredit(Integer key, Integer value){
        rankRequireCredit.put(key,value);
    }

    /**
     * Method: rankPlayerCredit
     *
     * Parameter(s): Method takes an int being the desired rank and a player object being the player trying to rank up.
     *
     * Responsibilities: Method is responsible for ranking up a player by payment of credits if possible.
     *
     * Return(s): boolean representing if the rank up was successful or not.
     */
    public boolean rankPlayerCredit(int goalRank, Player player){
        if(player.getWallet().getCredits() >= rankRequireCredit.get(goalRank)){
            player.setPlayerRank(goalRank);
            player.getWallet().decCredits(rankRequireCredit.get(goalRank));
            return true;
        }
        return false;
    }

    /**
     * Method: rankPlayerDollar
     *
     * Parameter(s): Method takes an int being the desired rank and a player object being the player trying to rank up.
     *
     * Responsibilities: Method is responsible for ranking up a player by payment of dollars if possible.
     *
     * Return(s): boolean representing if the rank up was successful or not.
     */
    public boolean rankPlayerDollar(int goalRank, Player player){
        if(player.getWallet().getDollars() >= rankRequireDollar.get(goalRank)){
            player.setPlayerRank(goalRank);
            player.getWallet().decDollars(rankRequireDollar.get(goalRank));
            return true;
        }
        return false;
    }
}
