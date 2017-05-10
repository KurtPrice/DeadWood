/**
 * Created by eddyt on 5/10/17.
 */

import java.util.*;

public class UIText {
    Scanner sc = new Scanner(System.in);

    public int numberPlayers() {
        System.out.println("How many players are playing?");
        int pCount = sc.nextInt();
        if (pCount >= 2 & pCount <= 8) {
            return pCount;
        } else {
            System.out.println("Invalid number of players enter a number between 2 and 8");
            return numberPlayers();
        }
    }
}
