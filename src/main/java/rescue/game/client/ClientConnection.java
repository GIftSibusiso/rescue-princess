package rescue.game.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection {
    public static void main(String[] args) throws UnknownHostException, IOException
    {
        Socket socket = new Socket("localhost", 2000);
    }
}
