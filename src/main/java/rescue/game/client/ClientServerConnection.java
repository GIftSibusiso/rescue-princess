package rescue.game.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import rescue.game.Additions;

public class ClientServerConnection {
    private ResponseHandler responseHandler;
    private Socket socket;
    private Map<String, Object> request = new HashMap<>();
    private PrintWriter out;

    public ClientServerConnection( int port, String ip ) {
        
        try {
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream());
            responseHandler = new ResponseHandler(socket);
            Thread thread = new Thread(responseHandler);
            thread.start();
            makeRequest();
        } catch (UnknownHostException e) {
            System.out.println(ip + " cannot be found... :(");
        } catch (ConnectException e) {
            System.out.println( "   => Connection refused, Server not running" );
        } catch (IOException ignore) { }
    }

    public ClientServerConnection( int port ) {
        
        try {
            socket = new Socket("localhost", port);
            out = new PrintWriter(socket.getOutputStream());
            responseHandler = new ResponseHandler(socket);
            Thread thread = new Thread(responseHandler);
            thread.start();
            makeRequest();
        } catch (ConnectException e) {
            System.out.println( "   => Connection refused, Server not running" );
        } catch (IOException ignore) { }
    }

    private void makeRequest() throws IOException {
        launchPlayer();
        out.println("Hello server");
        out.flush();

    }

    private void launchPlayer() {
        String name = Additions.getInput("Player name");
        int make = getMake();
        request.put("player", name);
        request.put("command", "launch");
        request.put("arguments",  new int[]{make});
        out.println(request);
        out.flush();
    }

    private int getMake() {
        String make = Additions.getInput(
            "There 3 weapon makes, choose one between:\n" + 
            "  1. Shot gun\n  2. Sniper\n  3. Bazooka\nYour choice"
        ); 

        if ( 
            !Additions.isInt(make) || 
            !(Integer.parseInt(make)>0 && Integer.parseInt(make)<4)
        ) {
            return getMake();
        }

        return Integer.parseInt(make);

    }

    public static void main(String[] args) throws UnknownHostException, IOException
    {
        new ClientServerConnection(2000);
    }
}
