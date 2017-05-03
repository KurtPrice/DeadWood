/**
 * Created by pricek21 on 5/3/17.
 */
public class PlayerCurrency {
    private int credits;
    private int dollars;

    public PlayerCurrency(int c, int d){
        credits = c;
        dollars = d;
    }

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
