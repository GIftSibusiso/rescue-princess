package rescue.game.server.Commands;

import rescue.game.server.ClientConnection;
import rescue.game.server.player.Player;
import rescue.game.server.player.PlayersConnection;

public class PlayersCommand extends Command {

    public PlayersCommand(String[] args) {
        super(args);
    }

    @Override
    public void doCommand(ClientConnection clientConnection) {
        System.out.println("\nPlayers connected: " + clientConnection.players.size());
        int count = 0;

        for ( PlayersConnection playersConnection : clientConnection.players ) {
            Player player = playersConnection.getPlayer();
            if ( player.getName() != null ) {
                count++;
                System.out.println(
                    "\nLaunched player #"+ count + "\n" +
                    "Name: " + player.getName() + "\n" +
                    "Position: (" + player.getPosition()[0] + ", " + player.getPosition()[1] + ")\n" + 
                    "Direction: " + player.getDirection() + "\n" +
                    "Health: " + player.getHealth() + "\n" +
                    "Shots: " + player.getShots()
                ); 
            }
        }
    }
    
}
