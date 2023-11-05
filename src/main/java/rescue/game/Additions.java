package rescue.game;

import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

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
}
