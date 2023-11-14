package rescue.game.server.player.commands;

import java.io.PrintWriter;
import java.util.List;

import org.json.JSONObject;

import rescue.game.Additions;
import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.player.PlayersConnection;
import rescue.game.server.world.World;

public class RepairCommand extends Commands {
    private final JSONObject response = new JSONObject();

    public RepairCommand(List<String> args) {
        super(args);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Commands doCommand(World world, Player player) {
        if ( arguments.size() > 0 ) {
            return new InvalidCommand("Not expecting arguments").doCommand(world, player);
        }

        alertPlayer(world, player);
        Additions.sleep(3);
        JSONObject data = new JSONObject();

        if ( player.getHealth()<100 ) {
            player.setHealth(player.getHealth() + 10);
        }

        response.put("result", "OK");
        response.put("message", "repair successful");
        data.put("health", player.getHealth());
        response.put("data", data);
        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }
    
    private void alertPlayer(World world, Player player) {
        JSONObject alert = new JSONObject();
        alert.put("result", "ALERT");
        alert.put("message", "Player repairing");
        alert.put("repairTime", 3);

        for ( PlayersConnection playerConnection : world.PLAYERS ) {
            if ( playerConnection.getPlayer().equals(player) ) {
                PrintWriter out = playerConnection.getOutputStream();
                out.println(alert);
                out.flush();
            }
        }
    }
}
