/**
 * Created by pricek21 on 5/3/17.
 */
public class SceneRoom extends Room {
    private boolean revealScene;
    private Role[] roleList;

    public SceneRoom(String name, int availableCount, Role[] rList) {
        super(name, availableCount);
        roleList = rList;
    }

    public Role[] getRoleList(){
        return roleList;
    }
    private void revealScene(){

    }
}
