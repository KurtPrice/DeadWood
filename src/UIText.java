/**
 * Created by eddyt on 5/10/17.
 */

import java.util.*;

public class UIText {
   private Scanner sc = new Scanner(System.in);

    public int numberPlayers() {
        System.out.println("How many players are playing?");
        String entry = sc.nextLine();
        if (isStringInt(entry) == true) {
            int pCount = Integer.parseInt(entry);
            if (pCount >= 2 & pCount <= 8) {
                return pCount;
            } else {
                System.out.println("Invalid number of players enter a number between 2 and 8");
                return numberPlayers();
            }
        }else{System.out.println("Invalid number of players enter a number between 2 and 8"); return numberPlayers();}
    }

    public void turn(Player currentPlayer) {
        System.out.println(currentPlayer.getPlayerName()+"'s turn.");
        boolean end = false;
        while(end == false){
            System.out.println("what would you like to do?");
            String entry = sc.nextLine();
            switch (entry){
                case "who": System.out.println("Current player: "+currentPlayer.getPlayerName()+", Current Part: "+currentPlayer.getRoleName());
                case "where": System.out.println("");
                case "rehearse":
                case "act":
                case "end":
            }
        }
    }

    private boolean isStringInt(String entry){
        try{Integer.parseInt(entry); return true;}
        catch (Exception e){return false;}
    }
}
