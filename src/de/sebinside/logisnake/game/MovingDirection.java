package de.sebinside.logisnake.game;

/**
 * A direction of the snake.
 */
public enum MovingDirection {

    LEFT(-1, 0),
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1);

    /**
     * The offset the snake-head moves on the x-axis per step.
     */
    public final int offsetX;

    /**
     * The offset the snake-head moves on the y-axis per step.
     */
    public final int offsetY;

    private MovingDirection opposite; // Caching this makes it faster

    MovingDirection(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    /**
     * Gets the directions opposite.
     */
    public final MovingDirection opposite() {
        if (opposite == null)
            opposite = values()[(ordinal() + 2) % 4];
        return opposite;
    }
}
