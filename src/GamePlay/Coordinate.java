/**
 * Created by danielmacario on 14-11-12.
 */
package GamePlay;

import java.io.Serializable;

/**
 * The Coordinate class manages the coordinates for game objects.
 */
public class Coordinate implements Serializable  {

    private int row;
    private int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * This method checks if two values are on the same memory location, then if they are coordinates, then if they are the same coordinate.
     * @param other
     * @return Returns true if coordinates are on the same memory location, false if one of them.
     */
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
        return row * col + row - 4;
    }

    /**
     * @return Returns the row position of the object.
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row of an object.
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return Returns the column position of the object
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the column of an object
     * @param col
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * @param coordinate
     * @return Returns distance between two coordinates.
     */
    public int distanceTo(Coordinate coordinate) {
        return (int) Math.sqrt( (row - coordinate.getRow()) *  (row - coordinate.getRow()) + (col - coordinate.getCol()) *  (col - coordinate.getCol()));
    }

}
