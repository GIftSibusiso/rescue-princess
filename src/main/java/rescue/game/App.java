package rescue.game;

import java.net.ServerSocket;
import java.net.UnknownHostException;

import rescue.game.server.ServerSetUp;

import java.net.InetAddress;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws UnknownHostException
    {
        new ServerSetUp(2000);
    }
}
