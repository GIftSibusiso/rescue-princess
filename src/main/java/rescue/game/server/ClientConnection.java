package rescue.game.server;

import java.io.IOException;
import java.net.ServerSocket;

public class ClientConnection implements Runnable {
    ServerSocket server;

    public ClientConnection( ServerSocket server ) {
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            connectClient();
            System.out.println("Client connected");
        }
    }
    
    private void connectClient() {
        try {
            server.accept();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
