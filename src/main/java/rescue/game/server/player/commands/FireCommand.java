package rescue.game.server.player.commands;

import java.util.List;

import org.json.JSONObject;

import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.world.World;

public class FireCommand extends Commands {
    List<String> arguments;

    public FireCommand( List<String> args ) {
        arguments = args;
    }

    @Override
    public Commands doCommand(World world, Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doCommand'");
    }

    @Override
    public JSONObject getResponse() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getResponse'");
    }
    
}
