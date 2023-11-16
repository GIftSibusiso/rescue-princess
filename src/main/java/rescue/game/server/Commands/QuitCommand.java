package rescue.game.server.Commands;

import java.io.IOException;

import rescue.game.server.ClientConnection;
import rescue.game.server.player.PlayersConnection;
import rescue.game.turtle.Turtle;

public class QuitCommand extends Command{

    public QuitCommand(String[] args) {
        super(args);
    }

    @Override
    public void doCommand(ClientConnection clientConnection) {
        if ( arguments.length > 0 ) {
            new InvalidCommand("Not expecting arguments");
        } else {
            clientConnection.playerConnection = false;
            for (PlayersConnection playersConnection: clientConnection.players) {
                try {
                    if ( !playersConnection.getSocket().isClosed() )
                    playersConnection.getSocket().close();
                } catch (IOException ignore) {  }
            }

            try {
                Turtle.exit();
                clientConnection.server.close();
            } catch (IOException ignore) { }
        }
    }
    
}
