import java.util.ArrayList;

/**
 * Created by pricek21 on 5/3/17.
 * Class that is a blueprint for creating SceneRoom objects.
 */
public class SceneRoom extends Room {
    //private boolean revealScene;
    private Role[] roleList;
    private Scene roomScene;
    private int[] area;
    public int shotCounters;
    private ArrayList<int[]> shotArea = new ArrayList<>();
    public int tempShotCounters;
    private boolean sceneFinished = false;

    public SceneRoom(String name, int availableCount, Role[] rList, int shots) {
        super(name, availableCount);
        roleList = rList;
        shotCounters = shots;
    }

    /**
     * Methods: getRoleList, setRoomScene, getRoomScene, getSceneFinished, setSceneFinished
     *
     * Responsibilities: Methods are responsible for getting and setting of SceneRoom object attributes.
     */
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

    public void setArea(int[] position){area = position;}

    public int[] getArea(){
        return area;
    }

    public void addShotArea(int [] position){
        shotArea.add(position);
    }

    public int[] getShotArea(int i){
        return shotArea.get(i);
    }

    public int getShotAreaSixe(){
        return  shotArea.size();
    }

    //private void revealScene(){
    //}
}
