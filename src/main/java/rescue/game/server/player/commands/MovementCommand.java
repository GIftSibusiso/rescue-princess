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
    private List<String> arguments;
    private boolean backCommand = false;

    public MovementCommand(List<String> args) {
        arguments = args;
    }

    public MovementCommand(List<String> args, boolean backCommand) {
        this.backCommand = backCommand;
        arguments = args;
    }

    @Override
    public Commands doCommand(World world, Player player) {
        if (arguments.size()!=1 || !Additions.isInt(arguments.get(0))) {
            return new InvalidCommand("Expected integer as argument").doCommand(world, player);
        }
        Direction.Status status;
        String msg;

        if ( backCommand ) {
            status = player.getDirection()
                    .updatePosition(world, player, -Integer.parseInt(arguments.get(0)));
        } else {
            status = player.getDirection()
                    .updatePosition(world, player, Integer.parseInt(arguments.get(0)));
        }

        switch(status) {
            case OBSTRUCTED:
                msg = "There's a player on the way";
                break;
            case SUCCESSFUL:
                msg = "Movement successful";
                break;
            case OUTSIDE_WORLD:
                msg = "Can't go outside world";
                break;
            default:
                msg = "Error occured";
        }

        response.put("result", "OK");
        response.put("message", msg);
        response.put("data", Additions.addData(player));

        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }
    
}
