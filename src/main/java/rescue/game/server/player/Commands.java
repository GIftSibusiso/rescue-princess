package rescue.game.server.player;

import org.json.JSONObject;

import rescue.game.server.player.commands.InvalidCommand;
import rescue.game.server.player.commands.LaunchCommand;
import rescue.game.server.world.World;

public abstract class Commands{

    public abstract Commands doCommand( World world, PlayersConnection playersConnection );

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
