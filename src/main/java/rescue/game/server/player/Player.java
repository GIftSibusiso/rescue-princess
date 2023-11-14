package rescue.game.server.player;

import rescue.game.server.world.Direction;

public class Player {
    private String name = null, model;
    private final int[] position = new int[2];
    private Direction direction;
    private int shots, range, reloadTime, effect, health;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

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
