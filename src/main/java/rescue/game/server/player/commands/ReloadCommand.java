package rescue.game.server.player.commands;

import java.io.PrintWriter;
import java.util.List;

import org.json.JSONObject;

import rescue.game.Additions;
import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.player.PlayersConnection;
import rescue.game.server.world.World;

public class ReloadCommand extends Commands {
    JSONObject response = new JSONObject();

    public ReloadCommand(List<String> args) {
        super(args);
    }

    @Override
    public Commands doCommand(World world, Player player) {
        if ( arguments.size() > 0 ) {
            return new InvalidCommand("Not expecting arguments").doCommand(world, player);
        }

        alertPlayer(world, player);

        JSONObject data = new JSONObject();
        Additions.sleep(player.getReloadTime());

        switch (player.getModel()) {
            case "1":
                player.setShots(10);
                break;
            case "2":
                player.setShots(3);
                break;
            case "3":
                player.setShots(6);
                break;
        }

        response.put("result", "OK");
        response.put("command", "reload");
        response.put("message", "Reload successful");
        data.put("shots", player.getShots());
        data.put("reloadTime", player.getReloadTime());
        response.put("data",  data);

        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    private void alertPlayer(World world, Player player) {
        JSONObject alert = new JSONObject();
        alert.put("result", "ALERT");
        alert.put("command", "reload");
        alert.put("message", "Player reloading");
        alert.put("reloadTime", player.getReloadTime());

        for ( PlayersConnection playersConnection : world.PLAYERS ) {
            if ( playersConnection.getPlayer().equals(player) ) {
                PrintWriter out = playersConnection.getOutputStream();
                out.println(alert);
                out.flush();
            }
        }
    }
    
}
