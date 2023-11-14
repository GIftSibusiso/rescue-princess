package rescue.game.server.player.commands;

import java.io.PrintWriter;
import java.util.List;

import org.json.JSONObject;

import rescue.game.Additions;
import rescue.game.server.player.Commands;
import rescue.game.server.player.Player;
import rescue.game.server.player.PlayersConnection;
import rescue.game.server.world.Direction;
import rescue.game.server.world.Obstacle;
import rescue.game.server.world.World;

public class FireCommand extends Commands {
    private int playerDistance, obstacleDistance;
    private JSONObject response = new JSONObject();

    public FireCommand( List<String> args ) {
        super(args);
    }

    @Override
    public Commands doCommand(World world, Player player) {

        if ( arguments.size()!=0 ) {
            return new InvalidCommand("Not expecting arguments").doCommand(world, player);
        }
        JSONObject data = new JSONObject();

        List<Player> players = Direction.seePlayers(world, player, player.getDirection());
        Player playerShot = closestPlayer(player, players);

        List<Obstacle> obstacles = Direction.seeObstacles(world, player, player.getDirection());
        Obstacle obstacleShot = closestObstacle(player, obstacles);

        if ( players.size() == 0 && obstacles.size() == 0 ) {
            data.put("message", "No one was shot");
        } else if ( 
            ( obstacles.size() == 0 && players.size() != 0 ) || (( playerDistance < obstacleDistance ) 
            && ( obstacles.size() != 0 ))
         ) { 
            data.put("name", playerShot.getName());
            data.put("position", playerShot.getPosition());
            data.put("health", updateHealth(player, playerShot, world));
            alertPlayer(world, player, playerShot);
        } else if ( 
            ( obstacles.size() != 0 && players.size() == 0 ) || (( playerDistance > obstacleDistance ) &&
            ( players.size() != 0 ))
        ) {
            Obstacle[] obstacle = {obstacleShot};
            data.put("obstacle", obstacle);
            response.put("message", "Shot an object");
        }

        response.put("result", "OK");
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
            int playerDistance1 = Additions.getDistance(shootingPlayer.getPosition(), player1.getPosition());
            if (distance==0 || distance > playerDistance1 ) {
                distance = playerDistance1;
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

    private int updateHealth(Player playerShoooting, Player playerShot, World world) {
        int health = playerShot.getHealth() - playerShoooting.getEffect();
        if ( health <= 0 ) {
            health = 0;
            LaunchCommand.reconfigurePlayer(world, playerShot);
            response.put("message", "Player is dead");
        } else {
            playerShot.setHealth(health);
            response.put("message", "Shot a player");
        }
        
        return health;
    }

    private void alertPlayer(World world, Player playerShooting, Player playerShot) {
        JSONObject alert = new JSONObject(), data = new JSONObject();
        alert.put("result", "SHOT");
        alert.put("message", "You were shot");
        data.put("name", playerShooting.getName());
        data.put("position", playerShooting.getPosition());
        data.put("direction", playerShooting.getDirection());
        alert.put("data", data);
        alert.put("health", playerShot.getHealth());

        for (PlayersConnection playersConnection : world.PLAYERS) {
            if (playersConnection.getPlayer().equals(playerShot)) {
                PrintWriter out = playersConnection.getOutputStream();
                out.println(alert);
                out.flush();
            }
        }
        
        
    }
    
}
