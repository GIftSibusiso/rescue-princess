package rescue.game.server.world;

public class Obstacle {
    private int x, y, length, width, x0, y0;

    public Obstacle( int x,int y, int length, int width ) {
        this.x = x; this.y = y; this.length = length; this.width = width;
        x0 = x + width - 1;
        y0 = y + length - 1;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean positionBlocked( int[] position ) {
        int playerX = position[0], playerY = position[1];

        return playerX >= x && playerX <= x0 && playerY >= y && playerY <= y0;
    }

    public boolean pathBlocked( int[] initialPosition, int[] finalPosition ) {
        int playerX = initialPosition[0], playerX1 = finalPosition[0],
            playerY = initialPosition[1], playerY1 = finalPosition[1];

        if ( playerY == playerY1 ) {
            int[] playerXs;

            if ( playerX <= playerX1 ) {
                playerXs = new int[] { playerX, playerX1 };
            } else {
                playerXs = new int[] { playerX1, playerX };
            }

            return playerY >= y && playerY <= y0 && playerXs[0] <= x && playerXs[1] >= x ;
        }

        int[] playerYs;

        if ( playerY <= playerY1 ) {
            playerYs = new int[] { playerY, playerY1 };
        } else {
            playerYs = new int[] { playerY1, playerY };
        }

        return playerX >= x && playerX <= x0 && playerYs[0] <= y && playerYs[1] >= y;
    }
}
