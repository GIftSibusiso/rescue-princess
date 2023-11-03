package rescue.game.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
            Socket socket = server.accept();
            PrintWriter write = new PrintWriter(socket.getOutputStream());
            write.println("You're connected to my server... Enjoy :)");
            write.flush();
            System.out.println(socket.getInetAddress());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
