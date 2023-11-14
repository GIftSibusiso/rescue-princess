package rescue.game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import rescue.game.server.player.PlayersConnection;
import rescue.game.server.world.World;

public class ClientConnection implements Runnable {
    public List<PlayersConnection> players = new ArrayList<>();
    ServerSocket server;
    public boolean playerConnection = true;
    public World WORLD;

    public ClientConnection( ServerSocket server ) {
        this.server = server;
        WORLD = new World(players);
    }

    @Override
    public void run() {
        connectClient();
        System.out.println("Connection closed");
    }
    
    private void connectClient() {
        try {
            while ( playerConnection ) {
                Socket socket = server.accept();
                PlayersConnection player = new PlayersConnection(socket, WORLD);
                players.add(player);
                Thread thread = new Thread(player);
                thread.start();
            }
        } catch ( SocketException e ) {
            System.out.println("Server no longer active");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
