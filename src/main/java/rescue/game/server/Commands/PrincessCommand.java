package rescue.game.server.Commands;

import rescue.game.server.ClientConnection;

public class PrincessCommand extends Command {

    public PrincessCommand(String[] args) {
        super(args);
    }

    @Override
    public void doCommand(ClientConnection clientConnection) {
        if ( arguments.length != 0 ) {
            new InvalidCommand("Not expecting arguments").doCommand(clientConnection);
        } else {
            int[] position = clientConnection.WORLD.PRINCESS.getPosition();
            System.out.println(
                "The princess is located at:\n" + 
                "  | x = " + position[0] + "\n" +
                "  | y = " + position[1] + "\n"
            );
        }
    }
    
}
