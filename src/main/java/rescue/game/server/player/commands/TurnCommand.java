package rescue.game.server.player.commands;

import java.util.List;

import org.json.JSONObject;

import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.world.World;

public class TurnCommand extends Commands{
    private final JSONObject response = new JSONObject();

    public TurnCommand(List<String> args) {
        super(args);
    }

    @Override
    public Commands doCommand(World world, Player player) {

        if ( arguments.get(0).equalsIgnoreCase("right") ) {
            player.getDirection().updateDirection(player, false);
        } else if (arguments.get(0).equalsIgnoreCase("left"))  {
            player.getDirection().updateDirection(player, true);
        } else {
            return new InvalidCommand("Invalid argument (right, left)").doCommand(world, player);
        }

        JSONObject data = new JSONObject();
        data.put("position", player.getPosition());
        data.put("direction", player.getDirection());
    
        response.put("result", "OK");
        response.put("command", "turn");
        response.put("message", "Player moved " + arguments.get(0));
        response.put("data", data);
        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }
    
}
