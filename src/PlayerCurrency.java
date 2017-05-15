/**
 * Created by pricek21 on 5/3/17.
 * Class that controls each players currency.
 */
public class PlayerCurrency {
    private int credits;
    private int dollars;

    public PlayerCurrency(int c, int d){
        credits = c;
        dollars = d;
    }

    /**
     * Methods: getCredits, getDollars, decDollars, decCredits, incDollars, incCredits
     *
     * Responsibilities: Methods are responsible for getting and setting of a players currency both
     * dollars and credits.
     */
    public int getCredits(){
        return credits;
    }

    public int getDollars(){
        return dollars;
    }

    public void decDollars(int amount){
        dollars -= amount;
    }

    public void decCredits(int amount){
        credits -= amount;
    }

    public void incDollars(int amount){
        dollars += amount;
    }

    public void incCredits(int amount){
        credits += amount;
    }
}
