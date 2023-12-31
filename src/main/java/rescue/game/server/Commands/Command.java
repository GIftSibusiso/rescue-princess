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
            case "WORLD":
                return new WorldCommand(arguments);
            case "OBSTACLE":
                return new CreateObstacle(arguments);
            case "REMOVE":
                return new RemoveCommand(arguments);
            case "OBSTACLES":
                return new ObstaclesCommand(arguments);
            case "HELP":
                return new HelpCommand(arguments);
            case "PRINCESS":
                return new PrincessCommand(arguments);
            default:
                return new InvalidCommand("Command does not exist");
        }
    }
}
