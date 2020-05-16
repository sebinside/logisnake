package de.sebinside.logisnake.game;

import java.util.Objects;

/**
 * A mutable position on the board. Defined by X and Y
 */
public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void set(Position other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Position copy() {
        return new Position(x, y);
    }

    public Position offset(int xOffset, int yOffset) {
        return new Position(x + xOffset, y + yOffset);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
