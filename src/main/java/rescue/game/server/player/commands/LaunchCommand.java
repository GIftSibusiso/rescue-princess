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
        configPlayer(arguments.get(0), player);
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

    private void configPlayer(String type, Player player) {
        // "  1. Shot gun\n  2. Sniper\n  3. Bazooka\nYour choice"
        switch(type) {
            case "1":
                player.setShots(10);
                player.setRange(15);
                player.setReloadTime(4);
                player.setEffect(10);
            case "2":
                player.setShots(3);
                player.setRange(40);
                player.setReloadTime(10);
                player.setEffect(50);
            case "3":
                player.setShots(6);
                player.setRange(23);
                player.setReloadTime(7);
                player.setEffect(30);

        }

        player.setHealth(100);
    }
    
}
