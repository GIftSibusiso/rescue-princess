package rescue.game.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.JsonNode;

import rescue.game.Additions;
import rescue.game.turtle.Turtle;

public class ResponseHandler implements Runnable {
    private Socket socket;
    public boolean running = false;
    private Turtle player;

    public ResponseHandler(Socket socket, Turtle player) {
        this.socket = socket;
        running = true;
        this.player = player;
    }
    
    @Override
    public void run() {
        try {
            while (running) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                JsonNode node = Additions.getMapper().readTree(in.readLine());
                System.out.println(node.toPrettyString());
                updateGUI(node);
            }
        } catch (IOException ignore) {
            running = false;
        } catch ( IllegalArgumentException e ) {
            System.out.println("\nServer shut down");
            running = false;
        }
        
    }

    private void updateGUI(JsonNode node) {
        switch ( node.get("command").asText() ) {
            case "movement":
                player.setPosition(
                    node.get("data").get("position").get(0).asInt() - 200,
                    node.get("data").get("position").get(1).asInt() - 200
                );
                break;
            case "turn":
                player.setDirection(
                    ClientServerConnection
                    .directionMapper(node.get("data").get("direction").asText())
                );
        }
    }
}
