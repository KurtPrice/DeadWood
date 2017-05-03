/**
 * Created by pricek21 on 5/2/17.
 */
public class OffCardRole extends Role {
    private int[] roomCoordin;

    public OffCardRole(String name, String description, int rank, boolean onCard) {
        super(name, description, rank, onCard);
    }

    public int[] getRoomCoordin(){
        return new int[]{0};
    }
}
