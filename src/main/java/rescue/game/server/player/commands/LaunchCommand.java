package rescue.game.server.player.commands;

import java.util.List;

import org.json.JSONObject;

import rescue.game.Additions;
import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.world.World;

public class LaunchCommand extends Commands{
    private final JSONObject response = new JSONObject();
    private List<String> arguments;

    public LaunchCommand( List<String> args ) {
        arguments = args;
    }

    @Override
    public LaunchCommand doCommand(World world, Player player) {
        JSONObject data = Additions.addData(player);
        player.setPosition(world.launchPlayer());

        response.put("result", "OK");
        response.put("message", "Player launched to world");
        response.put("data", data);
        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }
    
}
