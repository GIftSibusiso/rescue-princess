package rescue.game.server.world;


import java.util.List;
import java.util.ArrayList;

import rescue.game.server.player.Player;
import rescue.game.server.player.PlayersConnection;

public enum Direction {
    NORTH {
        @Override
        public Status updatePosition(World world, Player player, int steps) {
            int[] newPosition = { player.getPosition()[0], player.getPosition()[1] };

            newPosition[1] = newPosition[1] + steps;

            Status status = newPostionStatus(player.getPosition(), newPosition, world);

            if (status == Status.SUCCESSFUL) {
                player.setPosition(newPosition);
            } 
            return status;
        }

        @Override
        public void updateDirection(Player player, boolean leftTurn) {
            if (leftTurn) {
                player.setDirection(WEST);
            } else {
                player.setDirection(EAST);
            }
        }

        @Override
        public int seeBarrier(World world, Player player) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'seeBarrier'");
        }
    },

    SOUTH {

        @Override
        public Status updatePosition(World world, Player player, int steps) {
            int[] newPosition = { player.getPosition()[0], player.getPosition()[1] };

            newPosition[1] = newPosition[1] - steps;

            Status status = newPostionStatus(player.getPosition(), newPosition, world);

            if (status == Status.SUCCESSFUL) {
                player.setPosition(newPosition);
            } 
            return status;
        }

        @Override
        public void updateDirection(Player player, boolean leftTurn) {
            if (leftTurn) {
                player.setDirection(EAST);
            } else {
                player.setDirection(WEST);
            }
        }

        @Override
        public int seeBarrier(World world, Player player) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'seeBarrier'");
        }

    },

    WEST {

        @Override
        public Status updatePosition(World world, Player player, int steps) {
            int[] newPosition = { player.getPosition()[0], player.getPosition()[1] };

            newPosition[0] = newPosition[0] - steps;

            Status status = newPostionStatus(player.getPosition(), newPosition, world);

            if (status == Status.SUCCESSFUL) {
                player.setPosition(newPosition);
            } 
            return status;
        }

        @Override
        public void updateDirection(Player player, boolean leftTurn) {
            if (leftTurn) {
                player.setDirection(SOUTH);
            } else {
                player.setDirection(NORTH);
            }
        }

        @Override
        public int seeBarrier(World world, Player player) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'seeBarrier'");
        }

    },

    EAST {

        @Override
        public Status updatePosition(World world, Player player, int steps) {
            int[] newPosition = { player.getPosition()[0], player.getPosition()[1] };

            newPosition[0] = newPosition[0] + steps;

            Status status = newPostionStatus(player.getPosition(), newPosition, world);

            if (status == Status.SUCCESSFUL) {
                player.setPosition(newPosition);
            } 
            return status;
        }

        @Override
        public void updateDirection(Player player, boolean leftTurn) {
            if (leftTurn) {
                player.setDirection(NORTH);
            } else {
                player.setDirection(SOUTH);
            }
        }

        @Override
        public int seeBarrier(World world, Player player) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'seeBarrier'");
        }

    };

    public abstract Status updatePosition(World world, Player player, int steps);

    public abstract void updateDirection(Player player, boolean leftTurn);

    public static List<Obstacle> seeObstacles(World world, Player player, Direction direction) {
        final List<Obstacle> obstacles = new ArrayList<>();
        int[] position = player.getPosition(), position1;
        
        switch (direction) {
            case NORTH:
                position1 = new int[] { position[0], position[1] + player.getRange() };
                break;
            case SOUTH:
                position1 = new int[] { position[0], position[1] - player.getRange() };
                break;
            case WEST:
                position1 = new int[] { position[0] - player.getRange(), position[1] };
                break;
            default:
                position1 = new int[] { position[0] + player.getRange(), position[1] };
                break;
        }

        for (Obstacle  obstacle: world.obstacles) {
            if ( obstacle.pathBlocked(position, position1) || obstacle.positionBlocked(position1) ) {
                obstacles.add(obstacle);
            }
        }

        return obstacles;
    }

    public static List<Player> seePlayers(World world, Player player, Direction direction) {
        List<Player> players = new ArrayList<>();
        int[] newPosition = getOtherPosition(player, direction);

        for ( PlayersConnection playersConnection: world.PLAYERS ) {
            Player player1 = playersConnection.getPlayer();

            if ( player1.equals(player) ) {
                continue;
            } else if ( positionBlockedByPlayer(player.getPosition(), newPosition, player1.getPosition()) ) {
                players.add(player1);
            }
        }

        return players;
    }

    public abstract int seeBarrier(World world, Player player);

    private static int[] getOtherPosition(Player player, Direction direction) {
        int[] position = player.getPosition(), position1;
        
        switch (direction) {
            case NORTH:
                position1 = new int[] { position[0], position[1] + player.getRange() };
                break;
            case SOUTH:
                position1 = new int[] { position[0], position[1] - player.getRange() };
                break;
            case WEST:
                position1 = new int[] { position[0] - player.getRange(), position[1] };
                break;
            default:
                position1 = new int[] { position[0] + player.getRange(), position[1] };
                break;
        }

        return position1;
    }

    public enum Status {
        SUCCESSFUL, OBSTRUCTED, OUTSIDE_WORLD
    }

    private static Status newPostionStatus( int[] postion, int[] newPosition, World world ) {

        for ( Obstacle obstacle: world.obstacles ) {
            if ( 
                obstacle.pathBlocked(postion, newPosition) || obstacle.positionBlocked(newPosition)
             ) {
                return Status.OBSTRUCTED;
             }
        }

        for ( PlayersConnection player: world.PLAYERS ) {
            int[] pos = player.getPlayer().getPosition();
            if (pos.equals(postion)) {
                continue;
            } else if ( positionBlockedByPlayer(postion, newPosition, pos) ) {
                return Status.OBSTRUCTED;
            }
        }
        if ( newPosition[0] > world.WIDTH || newPosition[1] > world.HEIGHT ||
             newPosition[0] < 0 || newPosition[1] < 0 ) {
            return Status.OUTSIDE_WORLD;
        }

        return Status.SUCCESSFUL;
    }

    private static boolean positionBlockedByPlayer( int[] position, int[] newPosition, int[] player2Postion ) {

        if (position[0] == newPosition[0]) {
            int[] yValues; 

            if (position[1] <= newPosition[1]) { 
                yValues = new int[] {position[1], newPosition[1]} ;
            } else { yValues = new int[] {newPosition[1], position[1]}; }

            return player2Postion[1] >= yValues[0] && player2Postion[1] <= yValues[1] 
                && player2Postion[0] == position[0];

        }

        int[] xValues;

        if (position[0] <= newPosition[0]) {
            xValues = new int[] {position[0], newPosition[0]};
        } else { xValues = new int[] { newPosition[0], position[0] }; }

        return player2Postion[0] >= xValues[0] && player2Postion[0] <= xValues[1] 
            && player2Postion[1] == position[1];
    }
}
