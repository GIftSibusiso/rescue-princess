package rescue.game.server.player.commands;

import org.json.JSONObject;

import rescue.game.server.player.Commands;

public class InvalidCommand extends Commands{
    private final JSONObject response = new JSONObject();

    @Override
    public InvalidCommand doCommand() {
        response.put("result", "ERROR");
        response.put("message", "Invalid command");
        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }
    
}
