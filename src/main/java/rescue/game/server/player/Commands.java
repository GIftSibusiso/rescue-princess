package rescue.game.server.player;

import java.util.List;

import org.json.JSONObject;

import rescue.game.server.player.commands.*;
import rescue.game.server.world.World;

public abstract class Commands{

    public abstract Commands doCommand( World world, Player player );

    public abstract JSONObject getResponse();

    public static Commands processRequest(String command, List<String> arguments) {

        switch (command.toUpperCase()) {
            case "LAUNCH":
                return new LaunchCommand(arguments);
            case "FORWARD":
                return new MovementCommand(arguments);
            case "BACK":
                return new MovementCommand(arguments, true);
            case "TURN":
                return new TurnCommand(arguments);
            case "STATE":
                return new StateCommand();
            case "LOOK":
                return new LookCommand(arguments);
            default:
                return new InvalidCommand("'" +command+"' command does not exist");
        }
    }
} 
