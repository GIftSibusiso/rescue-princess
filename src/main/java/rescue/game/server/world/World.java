package rescue.game.server.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;

import rescue.game.Additions;
import rescue.game.server.player.PlayersConnection;
import rescue.game.turtle.Turtle;

public class World {
    Random random = new Random();

    public int HEIGHT, WIDTH;
    public List<PlayersConnection> PLAYERS;
    public Princess PRINCESS;
    public List<Obstacle> obstacles = new ArrayList<>();
    public Turtle draw = new Turtle(-200, -200);
    
    public World (List<PlayersConnection> players) {
        draw.hide();
        draw.speed(0);
        configWorld();
        PLAYERS = players;
        PRINCESS = new Princess(this);
    }

    private void configWorld()  {
        String filename = "dimensions.json";
        obstacles.add(new Obstacle(0, 0, 5, 5));
        obstacles.add(new Obstacle(10, 0, 5, 5));

        try {
            StringBuilder jsonString = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            while(reader.ready()) { jsonString.append(reader.readLine()); }

            reader.close();
            JsonNode node = Additions.getMapper().readTree(jsonString.toString());

            if ( 
                !Additions.isInt(node.get("height").asText()) ||
                !Additions.isInt(node.get("width").asText()) 
            ) 
            {
                throw new FileNotFoundException("Properties not properly defined");
            }

            HEIGHT = node.get("height").asInt();
            WIDTH = node.get("width").asInt();
        } catch (FileNotFoundException e) {
            HEIGHT = 500;
            WIDTH = 500;
        } catch (IOException ignore) {}
        drawWorld();
    }

    public int[] launchPlayer() {
        int x = random.nextInt((int) (WIDTH*0.5)),
            y = random.nextInt((int) (HEIGHT*0.5));

        for ( Obstacle obstacle: obstacles ) {
            if ( obstacle.positionBlocked(new int[] {x, y}) ) {
                return launchPlayer();
            }
        }

        for ( PlayersConnection playersConnection: PLAYERS ) {
            int[] position = playersConnection.getPlayer().getPosition();

            if (position.equals(new int[] {x, y})) {
                return launchPlayer();
            }
        }

        return new int[] {x, y};
    }  

    public Direction getRandomDirection() {
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        
        return directions[random.nextInt(4)];
    }

    private void drawWorld() {
        draw.forward(WIDTH);
        draw.left(90);
        draw.forward(HEIGHT);
        draw.left(90);
        draw.forward(WIDTH);
        draw.left(90);
        draw.forward(HEIGHT);

        for ( Obstacle obstacle : obstacles ) {
            updateObstacle(obstacle);
        }
    }

    public void updateObstacle(Obstacle obstacle) {
        draw.up();
        draw.setPosition(obstacle.getX()-200, obstacle.getY()-200);
        draw.setDirection(0);
        draw.down();

        draw.penColor("green");
        // draw.fillColor("black");
        draw.forward(obstacle.getWidth());
        draw.left(90);
        draw.forward(obstacle.getLength());
        draw.left(90);
        draw.forward(obstacle.getWidth());
        draw.left(90);
        draw.forward(obstacle.getLength());

        draw.penColor("black");

    }

}
