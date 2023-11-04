package rescue.game.server.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayersConnection implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public PlayersConnection( Socket socket ) throws IOException {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            out.println("Hello client");
            out.flush();
            System.out.println(in.readLine());
        } catch ( IOException ignore ) { }
    }
    
}
