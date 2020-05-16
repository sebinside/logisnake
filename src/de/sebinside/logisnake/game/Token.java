package de.sebinside.logisnake.game;

/**
 * A state of a cell on the board.
 */
public enum Token {

    /**
     * There's nothing in that cell
     */
    EMPTY,

    /**
     * There's food in that cell.
     */
    FOOD,

    /**
     * There's a body part of the snake in that cell.
     */
    SNAKE
}
