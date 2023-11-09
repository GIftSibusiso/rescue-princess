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
    public Commands doCommand(World world, Player player) {
        if (player.getName() != null) {
            return new InvalidCommand("Player already in world").doCommand(world, player);
        }
        // Make command recognise that robot is launched and assign given name to player 
        player.setPosition(world.launchPlayer());
        player.setDirection(world.getRandomDirection());
        player.setName(arguments.get(1));

        response.put("result", "OK");
        response.put("message", "Player launched to world");
        response.put("data", Additions.addData(player));
        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }
    
}
