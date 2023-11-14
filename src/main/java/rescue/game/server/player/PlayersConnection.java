package rescue.game.server.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import rescue.game.server.world.World;

public class PlayersConnection implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectMapper mapper = new ObjectMapper();
    private World world;

    private final Player player = new Player();


    public PlayersConnection( Socket socket, World world ) throws IOException {
        this.world = world;
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                Map<String, Object> request = mapper.readValue(in.readLine(), Map.class);

                Commands command = Commands.processRequest(
                    request.get("command").toString(),
                    (List<String>) request.get("arguments")
                );

                out.println(command.doCommand(world, player).getResponse());
                out.flush();
            }
            
        } catch ( IOException ignore ) { }
        
    }

    public Player getPlayer() {
        return player;
    }

    public PrintWriter getOutputStream() {
        return out;
    }

    public Socket getSocket() {
        return socket;
    }
    
}
