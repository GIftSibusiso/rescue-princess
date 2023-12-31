package rescue.game.server.player.commands;

import java.util.ArrayList;

import org.json.JSONObject;

import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.world.World;

public class InvalidCommand extends Commands{
    private final JSONObject response = new JSONObject();
    private String message;

    public InvalidCommand( String message ) {
        super(new ArrayList<String>());
        this.message = message;
    }

    @Override
    public InvalidCommand doCommand(World world, Player player) {
        response.put("result", "ERROR");
        response.put("command", "invalid");
        response.put("message", message);
        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }
    
}
