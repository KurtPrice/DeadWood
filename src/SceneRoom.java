/**
 * Created by pricek21 on 5/3/17.
 */
public class SceneRoom extends Room {
    private boolean revealScene;
    private Role[] roleList;
    private Scene roomScene;
    public int shotCounters;
    private boolean sceneFinished = false;

    public SceneRoom(String name, int availableCount, Role[] rList, int shots) {
        super(name, availableCount);
        roleList = rList;
        shotCounters = shots;
    }

    public Role[] getRoleList(){
        return roleList;
    }

    public void setRoomScene(Scene cScene){
        roomScene = cScene; sceneFinished= false;
    }
    public Scene getRoomScene(){
        return roomScene;
    }

    public boolean getSceneFinish(){
        return sceneFinished;
    }

    public void setFinishScene(boolean b){
        sceneFinished = b;
    }
    /**
     * I don't believe this is necessary for text UI.
     */
    // private void revealScene(){
    //    }

}
