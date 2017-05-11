import java.util.ArrayList;

/**
 * Created by pricek21 on 5/2/17.
 */
public class Scene {

    private String sceneName;
    private String sceneDescription;
    private Role[] roleList;
    private int budget;
    public int shotCounters;
    private boolean finishScene;

    public Scene(String name, String description, Role[] list, int money,  int shots){
        sceneName = name;
        sceneDescription = description;
        roleList = list;
        budget = money;
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

    public int getBudget(){
        return budget;
    }

    private void finishScene(){

    }

    private void bonusPayment(){

    }
}
