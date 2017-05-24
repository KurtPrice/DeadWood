/**
 * Created by Tom on 5/24/2017.
 */
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Resources {

    private ImageIcon scene;
    private ImageIcon[] dice;
    private ImageIcon cardBack;
    private ImageIcon shotCounter;
    private ImageIcon background;
    static Resources instance;

    private Resources() {
        dice = new ImageIcon[48];

        try {
            String path;
            int num;
            int index =0;
            for (int i = 0; i < 6; ++i) {
                num = i+1;
                path = "resources/dice/b" + num + ".png";
                dice[index] = new ImageIcon(

                        ImageIO.read(new File(path)));
                index++;
            }
            for (int i = 0; i < 6; ++i) {
                num = i+1;
                path = "resources/dice/c" + num + ".png";
                dice[index] = new ImageIcon(

                        ImageIO.read(new File(path)));
                index++;
            }
            for (int i = 0; i < 6; ++i) {
                num = i+1;
                path = "resources/dice/g" + num + ".png";
                dice[index] = new ImageIcon(

                        ImageIO.read(new File(path)));
                index++;
            }
            for (int i = 0; i < 6; ++i) {
                num = i+1;
                path = "resources/dice/o" + num + ".png";
                dice[index] = new ImageIcon(

                        ImageIO.read(new File(path)));
                index++;
            }
            for (int i = 0; i < 6; ++i) {
                num = i+1;
                path = "resources/dice/p" + num + ".png";
                dice[index] = new ImageIcon(

                        ImageIO.read(new File(path)));
                index++;
            }
            for (int i = 0; i < 6; ++i) {
                num = i+1;
                path = "resources/dice/r" + num + ".png";
                dice[index] = new ImageIcon(

                        ImageIO.read(new File(path)));
                index++;
            }
            for (int i = 0; i < 6; ++i) {
                num = i+1;
                path = "resources/dice/v" + num + ".png";
                dice[index] = new ImageIcon(

                        ImageIO.read(new File(path)));
                index++;
            }
            for (int i = 0; i < 6; ++i) {
                num = i+1;
                path = "resources/dice/y" + num + ".png";
                dice[index] = new ImageIcon(

                        ImageIO.read(new File(path)));
                index++;
            }

            background = new ImageIcon (
                    ImageIO.read(new File("resources/board.jpg")));
            cardBack = new ImageIcon (
                    ImageIO.read(new File("resources/cardBack.png")));
            shotCounter = new ImageIcon (
                    ImageIO.read(new File("resources/shot.png")));
        }catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public ImageIcon getScene(String name)
    {
        try{
        String path = "resources/scenes/" + name;
        scene = new ImageIcon(

                ImageIO.read(new File(path)));

        }catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return scene;
    }

    public ImageIcon getDice(int i)
    {
        return dice[i];
    }

    public ImageIcon getCB(){
        return cardBack;
    }

    public ImageIcon getShotCounter(){
        return shotCounter;
    }

    public ImageIcon getBG(){
        return background;
    }

    public static Resources getInstance() {
        if (instance == null)
            instance = new Resources();
        return instance;
    }
}