package rescue.game.server.player.commands;

import java.util.List;

import org.json.JSONObject;

import rescue.game.Additions;
import rescue.game.server.Commands.RemoveCommand;
import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.player.PlayersConnection;
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

        if ( world.PRINCESS.displacement(player.getPosition()) <= 10 ) {
            response.put("message", "You've found the Princess :)");
            removeEveryone(world, player.getName());
            data.put("princess-position", world.PRINCESS.getPosition());
        }

        response.put("data", data);

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

    private void removeEveryone( World world, String winnersName ) {
        JSONObject alert = new JSONObject();
        alert.put("result", "LOST");
        alert.put("message", winnersName.toUpperCase() + " found the princess :(");
        alert.put("princess-location", world.PRINCESS.getPosition());

        for ( PlayersConnection playersConnection: world.PLAYERS ) {
            if ( !winnersName.equals(playersConnection.getPlayer().getName()) ) {
                playersConnection.getOutputStream().println(alert);
                playersConnection.getOutputStream().flush();
                new RemoveCommand(new String[] { playersConnection.getPlayer().getName() });
            }
        }
    }
}
