package rescue.game.server.player;

import java.util.HashMap;
import java.util.Map;

import rescue.game.server.world.Direction;
import rescue.game.turtle.Turtle;

public class Player {
    private String name = null, model;
    private final int[] position = new int[2];
    private Direction direction;
    private int shots, range, reloadTime, effect, health;
    private Map<Direction, Integer> directionToDegrees = new HashMap<>();
    
    public Turtle playerGUI;

    public Player() {
        directionToDegrees.put(Direction.NORTH, 90);
        directionToDegrees.put(Direction.WEST, 180);
        directionToDegrees.put(Direction.SOUTH, 270);
        directionToDegrees.put(Direction.EAST, 0);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPosition(int[] newPosition) {
        position[0] = newPosition[0];
        position[1] = newPosition[1];
        playerGUI.setPosition(newPosition[0]-200, newPosition[1]-200);
    }

    public int[] getPosition() {
        return position;
    }

    public void setDirection(Direction newDirection) {
        direction = newDirection;
        playerGUI.setDirection(directionToDegrees.get(newDirection));
    }

    public Direction getDirection() {
        return direction;
    }

    public void setRange(int range) {
        this.range = range;
    } 

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getHealth() {
        return health;
    }

    public int getRange() {
        return range;
    }

    public int getReloadTime() {
        return reloadTime;
    }
    
    public int getShots() {
        return shots;
    }

    public int getEffect() {
        return effect;
    }

    public String getModel() {
        return model;
    }
}
