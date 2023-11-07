package rescue.game.server.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import rescue.game.server.world.World;

public class PlayersConnection implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectMapper mapper = new ObjectMapper();
    private World world;

    private final int[] position = new int[2];

    public PlayersConnection( Socket socket, World world ) throws IOException {
        this.world = world;
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                JsonNode node = mapper.readTree(in.readLine());

                Commands command = Commands.processRequest(node.get("command").asText());

                out.println(command.doCommand(world, this).getResponse());
                out.flush();
            }
        } catch ( IOException ignore ) { }
    }

    public void setPosition(int[] newPosition) {
        position[0] = newPosition[0];
        position[1] = newPosition[1];
    }

    public int[] getPosition() {
        return position;
    }

    
}
