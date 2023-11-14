package rescue.game.server.Commands;

import rescue.game.server.ClientConnection;

public abstract class Command {
    public String[] arguments;

    public Command( String[] args ) {
        arguments = args;
    }

    public abstract void doCommand( ClientConnection clientConnection );

    public static Command processCommand(String command, String[] arguments) {

        switch (command.toUpperCase()) {
            case "OFF":
            case "EXIT":
            case "QUIT":
                return new QuitCommand(arguments);
            case "PLAYERS":
                return new PlayersCommand(arguments);
            default:
                return new InvalidCommand("Command does not exist");
        }
    }
}
