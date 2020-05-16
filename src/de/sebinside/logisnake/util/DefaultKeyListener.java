package de.sebinside.logisnake.util;

import de.sebinside.logisnake.game.MovingDirection;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

/**
 * A default key listener to be used in {@link de.sebinside.logisnake.output.IOutputDevice}.
 * The arrow keys control the direction. Using escape you can shut down LogiSnake.
 *
 * The constructor simply takes both dispatchers from the
 * {@link de.sebinside.logisnake.output.IOutputDevice#init(Consumer, Runnable) init} method
 * of {@link de.sebinside.logisnake.output.IOutputDevice}. This can then be added to a frame.
 */
public class DefaultKeyListener extends KeyAdapter {

    private final Consumer<MovingDirection> eventDispatcher;
    private final Runnable shutdownDispatcher;

    public DefaultKeyListener(Consumer<MovingDirection> eventDispatcher, Runnable shutdownDispatcher) {
        this.eventDispatcher = eventDispatcher;
        this.shutdownDispatcher = shutdownDispatcher;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                eventDispatcher.accept(MovingDirection.DOWN);
                break;
            case KeyEvent.VK_UP:
                eventDispatcher.accept(MovingDirection.UP);
                break;
            case KeyEvent.VK_LEFT:
                eventDispatcher.accept(MovingDirection.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                eventDispatcher.accept(MovingDirection.RIGHT);
                break;
            case KeyEvent.VK_ESCAPE:
                shutdownDispatcher.run();
                break;
        }
    }
}
