package rescue.game.server.player.commands;

import org.json.JSONObject;

import rescue.game.server.player.Commands;

public class LaunchCommand extends Commands{
    private final JSONObject response = new JSONObject();

    @Override
    public LaunchCommand doCommand() {
        response.put("result", "OK");
        response.put("message", "Player launched to world");
        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }
    
}
