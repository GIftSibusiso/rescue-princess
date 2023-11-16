package rescue.game.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;

import rescue.game.Additions;
import rescue.game.turtle.Turtle;

public class ResponseHandler implements Runnable {
    private Socket socket;
    public boolean running = false;
    private Turtle player;
    private Turtle pen;
    private List<Turtle> players = new ArrayList<>();
    private String name;

    public ResponseHandler(Socket socket, Turtle player, String name) {
        this.socket = socket;
        running = true;
        this.player = player;
        pen = player.clone();
        pen.hide();
        this.name = name;
    }
    
    @Override
    public void run() {
        try {
            while (running) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                JsonNode node = Additions.getMapper().readTree(in.readLine());
                System.out.println("\n" + node.toPrettyString() + "\n");
                updateGUI(node);
                System.out.print(name.toUpperCase() + " command: ");
            }
        } catch (IOException ignore) {
            running = false;
        } catch ( IllegalArgumentException e ) {
            System.out.println("\n  Disconnected from server");
            running = false;
        }
        
    }

    private void updateGUI(JsonNode node) {
        if ( !node.get("command").isNull() )

        if (players.size() != 0) {
            for ( Turtle turtle: players ) {
                turtle.hide();
            }
            players = new ArrayList<>();
        }
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
                drawPlayers(node.get("data").get("players"));
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

    private void drawPlayers(JsonNode node) {
        int count = 0;

        while ( node.get(count) != null ) {
            JsonNode playersAtt = node.get(count);
            Turtle player1 = player.clone();
            player1.setPosition(
                playersAtt.get("position").get(0).asInt()-200,
                playersAtt.get("position").get(1).asInt()-200
            );
            player1.setDirection(
                ClientServerConnection.directionMapper(playersAtt.get("direction").asText())
            );
            player1.shape("triangle");
            player1.shapeSize(5, 5);
            player1.outlineColor("red");
            players.add(player1);
            count++;
        }
    }
}
