package rescue.game.server.Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rescue.game.server.ClientConnection;
import rescue.game.server.player.PlayersConnection;

public class RemoveCommand extends Command {

    public RemoveCommand(String[] args) {
        super(args);
    }

    @Override
    public void doCommand(ClientConnection clientConnection) {
        if ( arguments.length != 1 ) {
            new InvalidCommand("Expected players name as argument");
        } else {
            List<PlayersConnection> players = new ArrayList<>();
            for ( PlayersConnection playersConnection : clientConnection.players ) {
                if ( playersConnection.getPlayer().getName().equalsIgnoreCase(arguments[0]) ) {
                    try {
                        playersConnection.getSocket().close();
                    } catch (IOException e) {
                        System.out.println("Socket closed");
                    }
                } else {
                    players.add(playersConnection);
                }
            }
            clientConnection.players = players;
        }
    }
    
}
