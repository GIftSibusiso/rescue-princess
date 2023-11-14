package rescue.game.server;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import rescue.game.Additions;
import rescue.game.server.Commands.Command;

public class ServerSetUp {
    private int Port; 
    private ServerSocket server;
    private boolean serverActive = false;
    private ClientConnection clientConnections;
    Thread thread;

    public ServerSetUp( int Port ) {
        this.Port = Port;
        startServer();

        if (serverActive) {
            acceptClientConnection();
            serverCommand();
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
        clientConnections = new ClientConnection(server);
        thread = new Thread(clientConnections);
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
            clientConnections.playerConnection = false;
            thread.interrupt();
            server.close();
            serverActive = false;
        } catch (IOException e) {
        }
    }

    private void serverCommand() {
        while (serverActive) {
            String cmd = Additions.getInput("", "-> ");
            String[] command = cmd.split(" ");

            Command.processCommand(command[0], getArgs(command)).doCommand(clientConnections);;
        }
    }

    private String[] getArgs(String[] input) {
        String[] args = new String[input.length-1];

        for ( int i=1; i<input.length; i++ ) {
            args[i-1] = input[i];
        }

        return args;
    }
}
