package rescue.game.server.player.commands;

import java.util.List;

import org.json.JSONObject;

import rescue.game.Additions;
import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.world.Direction;
import rescue.game.server.world.World;

public class MovementCommand extends Commands{
    private final JSONObject response = new JSONObject();
    private boolean backCommand = false;

    public MovementCommand(List<String> args) {
        super(args);
    }

    public MovementCommand(List<String> args, boolean backCommand) {
        super(args);
        this.backCommand = backCommand;
    }

    @Override
    public Commands doCommand(World world, Player player) {
        if (arguments.size()!=1 || !Additions.isInt(arguments.get(0))) {
            return new InvalidCommand("Expected integer as argument").doCommand(world, player);
        }
        JSONObject data = new JSONObject();
        Direction.Status status;

        if ( backCommand ) {
            status = player.getDirection()
                    .updatePosition(world, player, -Integer.parseInt(arguments.get(0)));
        } else {
            status = player.getDirection()
                    .updatePosition(world, player, Integer.parseInt(arguments.get(0)));
        }

        data.put("position", player.getPosition());
        data.put("direction", player.getDirection());

        response.put("result", "OK");
        response.put("command", "movement");
        response.put("message", getMessage(status));
        response.put("data", data);

        if ( world.PRINCESS.displacement(player.getPosition()) <= 10 ) {
            response.put("message", "You've found the Princess :)");
        }

        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }
    
    private String getMessage(Direction.Status status) {
        String message;

        switch(status) {
            case OBSTRUCTED:
                message = "Path blocked by something/someone";
                break;
            case SUCCESSFUL:
                message = "Movement successful";
                break;
            case OUTSIDE_WORLD:
                message = "Can't go outside world";
                break;
            default:
                message = "Error occured";
        }

        return message;
    }
}
