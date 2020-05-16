package de.sebinside.logisnake.output;

import de.sebinside.logisnake.game.MovingDirection;
import de.sebinside.logisnake.game.Token;

import java.util.function.Consumer;

/**
 * Base interface for classes implementing the i/o logic of LogiSnake
 */
public interface IOutputDevice {

    /**
     * Initialises the IOutputDevice
     *
     * @param eventDispatcher A dispatcher for move-direction change events.
     * @param shutdownDispatcher A dispatcher for shutdown events.
     */
    void init(Consumer<MovingDirection> eventDispatcher, Runnable shutdownDispatcher);

    /**
     * Called when LogiSnake shuts down.
     */
    void shutdown();

    /**
     * Sets one cell.
     */
    void setLED(int x, int y, Token token);

    /**
     * Called before a draw segment starts.
     */
    default void drawStart() {

    }

    /**
     * Called after a draw segment finished. This is always called even if the segment threw and exception.
     */
    default void drawEnd() {

    }
}
