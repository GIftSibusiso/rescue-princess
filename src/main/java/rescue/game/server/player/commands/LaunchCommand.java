package rescue.game.server.player.commands;

import org.json.JSONObject;

import rescue.game.server.player.Commands;
import rescue.game.server.player.PlayersConnection;
import rescue.game.server.world.World;

public class LaunchCommand extends Commands{
    private final JSONObject response = new JSONObject();

    @Override
    public LaunchCommand doCommand(World world, PlayersConnection playersConnection) {
        // TODO: Add data function that return an JSONObject with added data in Additions
        JSONObject data = new JSONObject();
        playersConnection.setPosition(world.launchPlayer());
        data.put("position", playersConnection.getPosition());

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
