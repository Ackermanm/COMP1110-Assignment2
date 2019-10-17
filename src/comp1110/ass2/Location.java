package comp1110.ass2;

/**
 * This class is used to record location of a piece. Integer X is the row of a piece and Y is the column of a piece.
 */
public class Location {
    private int X;
    private int Y;
    static final int OUT = -1;

    public Location(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public Location() {
        this.X = OUT;
        this.Y = OUT;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}

