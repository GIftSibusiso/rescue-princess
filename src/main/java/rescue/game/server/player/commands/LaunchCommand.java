package rescue.game.server.player.commands;

import java.util.List;

import org.json.JSONObject;

import rescue.game.Additions;
import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.player.PlayersConnection;
import rescue.game.server.world.World;

public class LaunchCommand extends Commands{
    private final JSONObject response = new JSONObject();

    public LaunchCommand( List<String> args ) {
        super(args);
    }

    @Override
    public Commands doCommand(World world, Player player) {
        if (player.getName() != null) {
            return new InvalidCommand("Player already in world").doCommand(world, player);
        } else if ( nameTaken(world, player, arguments.get(1)) ) {
            return new InvalidCommand("Name is taken").doCommand(world, player);
        }
        JSONObject worldDimensions = new JSONObject();
        worldDimensions.put("height",world.HEIGHT);
        worldDimensions.put("width",world.WIDTH);
        
        showPlayer(world, player);
        configPlayer(arguments.get(0), player);
        player.setModel(arguments.get(0));
        player.setPosition(world.launchPlayer());
        player.setDirection(world.getRandomDirection());
        player.setName(arguments.get(1));player.playerGUI.show();

        response.put("result", "OK");
        response.put("command", "launch");
        response.put("message", "Player launched to world");
        response.put("data", Additions.addData(player));
        response.put("world", worldDimensions);
        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    private static void configPlayer(String type, Player player) {
        // "  1. Shot gun\n  2. Sniper\n  3. Bazooka\nYour choice"
        switch(type) {
            case "1":
                player.setShots(10);
                player.setRange(15);
                player.setReloadTime(4);
                player.setEffect(10);
                break;
            case "2":
                player.setShots(3);
                player.setRange(40);
                player.setReloadTime(10);
                player.setEffect(50);
                break;
            case "3":
                player.setShots(6);
                player.setRange(23);
                player.setReloadTime(7);
                player.setEffect(30);
        }

        player.setHealth(100);
    }

    public static void reconfigurePlayer(World world, Player player) {
        configPlayer(player.getModel(), player);
        player.setPosition(world.launchPlayer());
        player.setDirection(world.getRandomDirection());
    }

    private void showPlayer( World world, Player player ) {
        player.playerGUI = world.draw.clone();
        player.playerGUI.up();
        player.playerGUI.shape("triangle");
        player.playerGUI.shapeSize(5, 5);
    }

    private boolean nameTaken(World world, Player player, String name) {
        for ( PlayersConnection playersConnection : world.PLAYERS ) {
            if ( 
                playersConnection.getPlayer().getName() != null &&
                !playersConnection.getPlayer().equals(player) &&
                playersConnection.getPlayer().getName().equalsIgnoreCase(name)
             ) {
                return true;
            }
        }

        return false;
    }
    
}
