package rescue.game.server.world;

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

    };

    public abstract Status updatePosition(World world, Player player, int steps);

    public enum Status {
        SUCCESSFUL, OBSTRUCTED, OUTSIDE_WORLD
    }

    private static Status newPostionStatus( int[] postion, int[] newPosition, World world ) {

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
