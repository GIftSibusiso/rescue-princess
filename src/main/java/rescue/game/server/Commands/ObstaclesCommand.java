package rescue.game.server.Commands;

import rescue.game.server.ClientConnection;
import rescue.game.server.world.Obstacle;

public class ObstaclesCommand extends Command {

    public ObstaclesCommand(String[] args) {
        super(args);
    }

    @Override
    public void doCommand(ClientConnection clientConnection) {
        if ( arguments.length != 6 ) {
            new InvalidCommand("Expentect 6 arguments").doCommand(clientConnection);
        } else {
            int[] initialPosition = { Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]) };
            int length = Integer.parseInt(arguments[2]), width = Integer.parseInt(arguments[3]);

            for ( int i=0; i<Integer.parseInt(arguments[5]); i++ ) {
                Obstacle obstacle;
                if ( arguments[4].equalsIgnoreCase("up") ) {
                    if ( initialPosition[1] + i*length >= clientConnection.WORLD.HEIGHT ) {
                        break;
                    }
                    obstacle = new Obstacle(
                            initialPosition[0], initialPosition[1] + i*length, 
                            length, width
                        );
                    clientConnection.WORLD.obstacles.add(obstacle);
                } else {
                    if ( initialPosition[0] + i*width >= clientConnection.WORLD.WIDTH ) {
                        break;
                    }
                    obstacle = new Obstacle(
                            initialPosition[0] + i*width, initialPosition[1], 
                            length, width
                        );
                    clientConnection.WORLD.obstacles.add(obstacle);
                }
                clientConnection.WORLD.updateObstacle(obstacle);
            }
        }
    }
    
}
