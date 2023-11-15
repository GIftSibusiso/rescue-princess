package rescue.game.server.player.commands;

import java.util.List;

import org.json.JSONObject;

import rescue.game.Additions;
import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.world.World;

public class StateCommand extends Commands{
    private final JSONObject response = new JSONObject();

    public StateCommand(List<String> args) {
        super(args);
    }

    @Override
    public Commands doCommand(World world, Player player) {
        response.put("result", "OK");
        response.put("command", "state");
        response.put("displacement", world.PRINCESS.displacement(player.getPosition()));
        response.put("data", Additions.addData(player));

        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }
    
}
