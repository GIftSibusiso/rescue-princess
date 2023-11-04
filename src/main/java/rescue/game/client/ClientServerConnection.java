package rescue.game.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientServerConnection {
    private ResponseHandler responseHandler;
    private Socket socket;

    public ClientServerConnection( int port, String ip ) {
        
        try {
            socket = new Socket(ip, port);
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
            responseHandler = new ResponseHandler(socket);
            Thread thread = new Thread(responseHandler);
            thread.start();
            makeRequest();
        } catch (ConnectException e) {
            System.out.println( "   => Connection refused, Server not running" );
        } catch (IOException ignore) { }
    }

    private void makeRequest() throws IOException {
        PrintStream out = new PrintStream(socket.getOutputStream());
        out.println("Hello server");
        out.flush();

    }

    public static void main(String[] args) throws UnknownHostException, IOException
    {
        new ClientServerConnection(2000);
    }
}
