package rescue.game.server.world;

import java.util.Random;

public class Princess {
    private int[] position;
    private World world;
    private Random random = new Random();

    public Princess(World world) {
        this.world = world;
        generatePrincessPosition();
    }

    private void generatePrincessPosition() {
        int x = random.nextInt(world.WIDTH),
            y = -random.nextInt((int) (world.HEIGHT*0.2)) + world.HEIGHT;
        System.out.println("x = " + x);
        System.out.println("y = " + y);
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
}
