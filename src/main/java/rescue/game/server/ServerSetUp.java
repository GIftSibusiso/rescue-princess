package rescue.game.server;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerSetUp {
    private int Port; 
    private ServerSocket server;
    private boolean serverActive = false;

    public ServerSetUp( int Port ) {
        this.Port = Port;
        startServer();

        if (serverActive) {
            acceptClientConnection();
        } else {
            System.out.println("Try closing port: " + getPort());
        }
    }

    public void startServer() {
        try {
            this.server = new ServerSocket(getPort());
            System.out.println(
                "Listening to client connection on:\n" +
                "  - Port: " + getPort() + "\n" +
                "  - IP Address: " + getIP()
            );
            serverActive = true;
        } catch ( IOException e ) {
            System.out.println("Port already in use");
        }
        
    }

    private void acceptClientConnection() {
        ClientConnection clientConnections = new ClientConnection(server);
        Thread thread = new Thread(clientConnections);
        thread.start();
    }

    public String getIP() {
        try {
            String IP =  InetAddress.getLocalHost().getHostAddress();
            return IP;
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    public int getPort() {
        return this.Port;
    }

    public void stopServer()  {
        try {
            server.close();
        } catch (IOException e) {
        }
    }
}
