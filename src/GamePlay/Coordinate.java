package GamePlay;

/**
 * Created by danielmacario on 14-11-12.
 */
public class Coordinate {

    private int row;
    private int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object other) {

        if (this == other) {
            return true;
        } else if (!(other instanceof Coordinate)) {
            return false;
        }

        Coordinate modifiedOther = (Coordinate) other;
        if (row == modifiedOther.getRow() && col == modifiedOther.getCol()) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return row * col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int distanceTo(Coordinate coordinate) {
        return (int) Math.sqrt( (row - coordinate.getRow()) *  (row - coordinate.getRow()) + (col - coordinate.getCol()) *  (col - coordinate.getCol()));
    }

}
