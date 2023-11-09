package rescue.game.server.player.commands;

import org.json.JSONObject;

import rescue.game.Additions;
import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.world.World;

public class StateCommand extends Commands{
    private final JSONObject response = new JSONObject();

    @Override
    public Commands doCommand(World world, Player player) {
        response.put("result", "OK");
        response.put("data", Additions.addData(player));

        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }
    
}
