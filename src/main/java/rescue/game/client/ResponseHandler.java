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
    private Turtle pen;

    public ResponseHandler(Socket socket, Turtle player) {
        this.socket = socket;
        running = true;
        this.player = player;
        pen = player.clone();
        pen.hide();
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
        if ( !node.get("command").isNull() )

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
                break;
            case "look":
                drawObtacles(node.get("data").get("obstacles"));
        }
    }

    private void drawObtacles( JsonNode obstacles ) {
        pen.up();
        pen.penColor("red");
        int count = 0;
        while ( obstacles.get(count) != null ) {
            JsonNode obstacle = obstacles.get(count);
            int width = obstacle.get("width").asInt(),
                length = obstacle.get("length").asInt();
            pen.setPosition(
                obstacle.get("x").asInt()-200, 
                obstacle.get("y").asInt()-200
            );
            pen.setDirection(0);
            pen.down();

            pen.forward(width);
            pen.left(90);
            pen.forward(length);
            pen.left(90);
            pen.forward(width);
            pen.left(90);
            pen.forward(length);

            pen.up();
            count++;
        }
    }
}
