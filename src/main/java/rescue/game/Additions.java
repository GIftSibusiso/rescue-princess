package rescue.game;

import java.util.Scanner;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import rescue.game.server.player.Player;

public class Additions {
    private static final Scanner CLI = new Scanner(System.in);

    public static String getInput(String query) {
        System.out.print(query + ": ");
        String input = CLI.nextLine();

        if ( input.isEmpty() ) {
            return getInput(query);
        }

        return input;
    }

    public static String getInput(String query, String ann) {
        System.out.print(query + "\n    " + ann);
        String input = CLI.nextLine();

        if ( input.isEmpty() ) {
            return getInput(query);
        }

        return input;
    }

    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch ( NumberFormatException e ) {
            return false;
        }
    }

    public static ObjectMapper getMapper() {
        return new ObjectMapper();
    }

    public static JSONObject addData(Player player) {
        JSONObject data = new JSONObject();

        data.put("position", player.getPosition());
        data.put("direction", player.getDirection());
        data.put("health", player.getHealth());
        data.put("shots", player.getShots());
        data.put("range", player.getRange());
        data.put("reload", player.getReloadTime());

        return data;
    }

    public static int getDistance(int[] position, int[] position1) {
        return (int) Math.sqrt(
            Math.pow(position[0] - position1[0], 2) - Math.pow(position[1] - position1[1], 2)
        );
    }
}
