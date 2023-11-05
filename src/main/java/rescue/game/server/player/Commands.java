package rescue.game.server.player;

import org.json.JSONObject;

import rescue.game.server.player.commands.InvalidCommand;
import rescue.game.server.player.commands.LaunchCommand;

public abstract class Commands{

    public abstract Commands doCommand();

    public abstract JSONObject getResponse();

    public static Commands processRequest(String command) {

        switch (command.toUpperCase()) {
            case "LAUNCH":
                return new LaunchCommand();
            default:
                return new InvalidCommand();
        }
    }
} 
