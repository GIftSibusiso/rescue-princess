package rescue.game.server.Commands;

import rescue.game.Additions;
import rescue.game.server.ClientConnection;
import rescue.game.server.world.Obstacle;
import rescue.game.server.world.World;

public class CreateObstacle extends Command {

    public CreateObstacle(String[] args) {
        super(args);
    }

    @Override
    public void doCommand(ClientConnection clientConnection) {
        if ( arguments.length != 4 || !allIntegers(arguments) ) {
            System.out.println("Invalid arguments");
        } else {
            World world = clientConnection.WORLD;
            world.obstacles.add(new Obstacle(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]), Integer.parseInt(arguments[2])));
            System.out.println("Obstacle added");
        }
    }

    boolean allIntegers(String[] args) {
        for( String arg : args) {
            if (!Additions.isInt(arg)) {
                return false;
            }
        }

        return true;
    }
    
}
