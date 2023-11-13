package rescue.game.server.player.commands;

import java.util.List;

import org.json.JSONObject;

import rescue.game.Additions;
import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.world.Direction;
import rescue.game.server.world.Obstacle;
import rescue.game.server.world.World;

public class FireCommand extends Commands {
    private int playerDistance, obstacleDistance;
    private JSONObject response = new JSONObject();
    private List<String> arguments;

    public FireCommand( List<String> args ) {
        arguments = args;
    }

    @Override
    public Commands doCommand(World world, Player player) {

        if ( arguments.size()!=1 || !Additions.isInt(arguments.get(0)) ) {
            return new InvalidCommand("Expected integer as argument").doCommand(world, player);
        }
        JSONObject data = new JSONObject();

        List<Player> players = Direction.seePlayers(world, player, player.getDirection());
        Player playerShot = closestPlayer(player, players);

        List<Obstacle> obstacles = Direction.seeObstacles(world, player, player.getDirection());
        Obstacle obstacleShot = closestObstacle(player, obstacles);

        if ( obstacleDistance == 0 && playerDistance == 0 ) {
            data.put("message", "No one was shot");
        } else if ( 
            ( obstacleDistance == 0 && playerDistance != 0 ) || (( playerDistance < obstacleDistance ) 
            && ( obstacleDistance != 0 ))
         ) {
            playerShot.setHealth(playerShot.getHealth() - player.getEffect());
            data.put("name", playerShot.getName());
            data.put("position", playerShot.getPosition());
            data.put("health", playerShot.getHealth());
        } else if ( 
            ( obstacleDistance != 0 && playerDistance == 0 ) || (( playerDistance > obstacleDistance ) &&
            ( playerDistance != 0 ))
        ) {
            Obstacle[] obstacle = {obstacleShot};
            data.put("obstacle", obstacle);
        }

        response.put("data", data);
        return this;
    }

    @Override
    public JSONObject getResponse() {
        return response;
    }

    private Player closestPlayer(Player shootingPlayer, List<Player> players) {
        int distance = 0;
        Player player = null;

        for ( Player player1 : players ) {
            int playerDistance = Additions.getDistance(shootingPlayer.getPosition(), player1.getPosition());
            if (distance==0 || distance > playerDistance ) {
                distance = playerDistance;
                player = player1;
            }
        }

        this.playerDistance = distance;

        return player;
    }

    private Obstacle closestObstacle(Player player, List<Obstacle> obstacles) {
        int distance = 0;
        Obstacle obstacle = null;

        for ( Obstacle obstacle1 : obstacles ) {
            int obsatcleDistance = Additions.getDistance(player.getPosition(), new int[] { obstacle1.getX(), obstacle1.getY() });
            if ( distance == 0 || obsatcleDistance < distance ) {
                distance = obsatcleDistance;
                obstacle = obstacle1;
            }
        }

        this.obstacleDistance = distance;

        return obstacle;
    }
    
}
