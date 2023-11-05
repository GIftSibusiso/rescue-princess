package rescue.game.server.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlayersConnection implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectMapper mapper = new ObjectMapper();

    public PlayersConnection( Socket socket ) throws IOException {
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

                out.println(command.doCommand().getResponse());
                out.flush();
            }
        } catch ( IOException ignore ) { }
    }
    
}
