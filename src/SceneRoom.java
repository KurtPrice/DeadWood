/**
 * Created by pricek21 on 5/3/17.
 */
public class SceneRoom extends Room {
    private boolean revealScene;
    private Role[] roleList;
    private Scene roomScene;

    public SceneRoom(String name, int availableCount, Role[] rList) {
        super(name, availableCount);
        roleList = rList;
    }

    public Role[] getRoleList(){
        return roleList;
    }

    public void setRoomScene(Scene cScene){
        roomScene = cScene;
    }
    public Scene getRoomScene(){
        return roomScene;
    }

    /**
     * I don't believe this is necessary for text UI.
     */
    // private void revealScene(){
    //    }

}
