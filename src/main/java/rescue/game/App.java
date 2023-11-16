package rescue.game;

import java.io.IOException;

import rescue.game.server.ServerSetUp;
import rescue.game.turtle.Turtle;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        new ServerSetUp(2000);
    } 
}
