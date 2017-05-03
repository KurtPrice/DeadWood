import java.util.ArrayList;

/**
 * Created by pricek21 on 5/2/17.
 */
public class Scene {

    private String sceneName;
    private String sceneDescription;
    private Role[] roleList;
    public int shotCounters;
    private boolean finishScene;

    public Scene(String name, String description, Role[] list, int shots){
        sceneName = name;
        sceneDescription = description;
        roleList = list;
        shotCounters = shots;
        finishScene = false;
    }

    public String getSceneName(){
        return sceneName;
    }

    public String getRollDescription(){
        return sceneDescription;
    }

    public Role[] getRoleList(){
        return roleList;
    }

    private void finishScene(){

    }

    private void bonusPayment(){

    }
}
