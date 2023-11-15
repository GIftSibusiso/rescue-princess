package rescue.game.server.Commands;

import rescue.game.server.ClientConnection;
import rescue.game.server.world.Obstacle;
import rescue.game.server.world.World;

public class WorldCommand extends Command {

    public WorldCommand(String[] args) {
        super(args);
    }

    @Override
    public void doCommand(ClientConnection clientConnection) {
        World world = clientConnection.WORLD;

        System.out.println(
            "World dimensions:\n"+
            "  * Width = " + world.WIDTH + "\n" +
            "  * Height = " + world.HEIGHT + "\n" +
            "  * Princess position: (" + world.PRINCESS.getPosition()[0] + 
            ", " + world.PRINCESS.getPosition()[1] + ")\n"
        );

        System.out.println("\nWorls obstacles:");
        int count = 0;
        for ( Obstacle obstacle: world.obstacles ) {
            count++;
            System.out.println(
                "\nObstacle #" + count + "\n" +
                "bottom left coordinate: (" + obstacle.getX() + ", " + obstacle.getY() + ")\n" +
                "Width = " + obstacle.getWidth() + "\n" +
                "Length = " + obstacle.getLength()
            );
        }
    }
    
}
