package rescue.game.server.Commands;

import rescue.game.server.ClientConnection;

public class HelpCommand extends Command {

    public HelpCommand(String[] args) {
        super(args);
    }

    @Override
    public void doCommand(ClientConnection clientConnection) {
        if ( arguments.length != 0 ) {
            new InvalidCommand("Not expecting arguments").doCommand(clientConnection);;
        } else {
            System.out.println(
                "Server commands:\n" +
                "  -> Players: Shows the number of players connected and attributes of the players launched\n" +
                "  -> Remove <player name>: removes the specified player from the game\n" +
                "  -> World: displays the position of the princess and world attributes also shows every obstacle position\n" +
                "  -> quit: removes every player connected then shuts the server down\n" +
                "  -> Obstacle <x position> <y position> <length> <width>: create the specified obstacle\n" +
                "  -> Obstacles <x position> <y position> <length> <width> <UP/RIGHT> <number of obstacles>: create trailing obstacles\n" +
                "  -> Help: shows all valid server commands\n"
            );
        }
    }
    
}
