/**
 * Created by pricek21 on 5/2/17.
 * Class that provides a blueprint for a role object.
 */
public class Role {
    private String roleName;
    private String roleDescription;
    private int roleRank;
    private boolean roleOccupancy;
    private boolean onCardRole;

    public Role(String name, String description, int rank, boolean onCard){
        roleName = name;
        roleDescription = description;
        roleRank = rank;
        onCardRole = onCard;
        roleOccupancy = false;
    }

    /**
     * Methods: getRoleName, getRoleDescription, getOnCardRole, getRoleRank
     *
     * Responsibilities: Methods are responsible for getting and setting of role attributes.
     */
    public String getRoleName(){
        return roleName;
    }

    public String getRoleDescription()
    {
        return roleDescription;
    }

    public boolean getOnCardRole(){
        return onCardRole;
    }

    //public boolean takeRole(int playerRank){
    //    return false;
    //}

    public int getRoleRank(){
        return roleRank;
    }
}
