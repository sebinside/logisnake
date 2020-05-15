package de.sebinside.logisnake.game;

public enum MovingDirection {
    LEFT, UP, RIGHT, DOWN;

    public MovingDirection getOpposite() {
        return MovingDirection.values()[(this.ordinal() + 2) % 4];
    }
}
