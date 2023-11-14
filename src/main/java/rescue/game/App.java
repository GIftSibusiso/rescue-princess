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
        // Turtle turtle = new Turtle(0, 0);
        // Turtle turtle2 = turtle.clone();
        // turtle2.up();
        // turtle2.forward(50);
    }
}
