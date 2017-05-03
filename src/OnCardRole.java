/**
 * Created by pricek21 on 5/2/17.
 */
public class OnCardRole extends Role {
    private int cardPosition;

    public OnCardRole(String name, String description, int rank, boolean onCard) {
        super(name, description, rank, onCard);
    }

    public int getCardPosition(){
        return cardPosition;
    }
}
