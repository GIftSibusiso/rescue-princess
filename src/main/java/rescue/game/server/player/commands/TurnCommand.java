package rescue.game.server.player.commands;

import java.util.List;

import org.json.JSONObject;

import rescue.game.Additions;
import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.world.World;

public class TurnCommand extends Commands{
    private final JSONObject response = new JSONObject();
    private List<String> arguments;

    public TurnCommand(List<String> args) {
        arguments = args;
    }

    @Override
    public Commands doCommand(World world, Player player) {

        if ( arguments.get(0).equals("right") ) {
            player.getDirection().updateDirection(player, false);
        } else {
            player.getDirection().updateDirection(player, true);
        }

        JSONObject data = new JSONObject();
        data.put("position", player.getPosition());
        data.put("direction", player.getDirection());
    
        response.put("result", "OK");
        response.put("message", "Player moved " + arguments.get(0));
        response.put("data", data);
        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }
    
}
