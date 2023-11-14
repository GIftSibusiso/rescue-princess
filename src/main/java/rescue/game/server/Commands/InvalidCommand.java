package rescue.game.server.Commands;

import rescue.game.server.ClientConnection;

public class InvalidCommand extends Command {

    public InvalidCommand(String message) {
        super(new String[] {});
    }

    @Override
    public void doCommand( ClientConnection clientConnection ) {
        System.out.println("Command does not exist");
    }
    
}
