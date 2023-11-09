package rescue.game.server.player;

import rescue.game.server.world.Direction;

public class Player {
    private final int[] position = new int[2];
    private Direction direction;

    public void setPosition(int[] newPosition) {
        position[0] = newPosition[0];
        position[1] = newPosition[1];
    }

    public int[] getPosition() {
        return position;
    }

    public void setDirection(Direction newDirection) {
        direction = newDirection;
    }

    public Direction getDirection() {
        return direction;
    }
}
