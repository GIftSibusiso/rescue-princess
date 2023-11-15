package rescue.game.server.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.world.Direction;
import rescue.game.server.world.Obstacle;
import rescue.game.server.world.World;

public class LookCommand extends Commands {
    private final JSONObject response = new JSONObject();
    final JSONObject data = new JSONObject();

    public LookCommand( List<String> args ) {
        super(args);
    }

    @Override
    public Commands doCommand(World world, Player player) {
        
        if (arguments.size()!=0) {
            return new InvalidCommand("Look command not expecting arguments").doCommand(world, player);
        }

        displayObstacle(world, player);
        displayPlayers(world, player);
        displayWorld(world, player);

        response.put("result", "OK");
        response.put("command", "look");
        response.put("position", player.getPosition());
        response.put("data", data);

        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    private void displayObstacle(World world, Player player) {
        List<Obstacle> obstacles = Direction.seeObstacles(world, player, Direction.WEST);
        obstacles.addAll(Direction.seeObstacles(world, player, Direction.EAST));
        obstacles.addAll(Direction.seeObstacles(world, player, Direction.SOUTH));
        obstacles.addAll(Direction.seeObstacles(world, player, Direction.NORTH));
        data.put("obstacles", obstacles);
    }
    

    private void displayPlayers( World world, Player player ) {
        List<JSONObject> newPlayersFormat = new ArrayList<>();
        List<Player> players = Direction.seePlayers(world, player, Direction.WEST);
        players.addAll(Direction.seePlayers(world, player, Direction.EAST));
        players.addAll(Direction.seePlayers(world, player, Direction.SOUTH));
        players.addAll(Direction.seePlayers(world, player, Direction.NORTH));
        
        
        for ( Player player1: players ) {
            JSONObject playerObject = new JSONObject();
            playerObject.put("name", player1.getName());
            playerObject.put("position", player1.getPosition());
            playerObject.put("direction", player1.getDirection());
            playerObject.put("health", player1.getHealth());

            newPlayersFormat.add(playerObject);
        }

        data.put("players", newPlayersFormat);
    }

    private void displayWorld(World world, Player player) {
        JSONObject distance = new JSONObject();

        distance.put( "North", world.HEIGHT - player.getPosition()[1] );
        distance.put( "South", player.getPosition()[1] );
        distance.put( "West", player.getPosition()[0] );
        distance.put( "East", world.WIDTH - player.getPosition()[0] );

        data.put("wall distance", distance);

    }
}
