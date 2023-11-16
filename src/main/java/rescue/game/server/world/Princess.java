package rescue.game.server.world;

import java.util.Random;

import rescue.game.turtle.Turtle;

public class Princess {
    private int[] position;
    private World world;
    private Random random = new Random();

    public Princess(World world) {
        this.world = world;
        generatePrincessPosition();
        showPrincess();
    }

    private void generatePrincessPosition() {
        int x = random.nextInt(world.WIDTH),
            y = -random.nextInt((int) (world.HEIGHT*0.2)) + world.HEIGHT;
        position = new int[] {x, y};
    }

    public int displacement(int[] playerPosition) {
        int x = position[0], x1 = playerPosition[0], 
            y = position[1], y1 = playerPosition[1];

        return (int) Math.sqrt(Math.pow(y - y1, 2) + Math.pow(x - x1, 2));
    }

    public int[] getPosition() {
        return position;
    }

    private void showPrincess() {
        Turtle princess = world.draw.clone();
        princess.up();
        princess.setPosition(position[0]-200, position[1]-200);
        princess.shape("circle");
        princess.shapeSize(7, 7);
        princess.outlineColor("orange");
        princess.show();
    }
}
