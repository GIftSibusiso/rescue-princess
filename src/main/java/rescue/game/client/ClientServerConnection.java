package rescue.game.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientServerConnection {
    public static void main(String[] args) throws UnknownHostException, IOException
    {
        Socket socket = new Socket("localhost", 2000);
        Thread thread = new Thread(new ResponseHandler(socket));
        thread.start();
        thread.interrupt();
    }
}
