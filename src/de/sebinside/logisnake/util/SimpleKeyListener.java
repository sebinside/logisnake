package de.sebinside.logisnake.util;

import de.sebinside.logisnake.game.SnakeGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SimpleKeyListener implements KeyListener {

    private final SnakeGame snakeGame;

    public SimpleKeyListener(SnakeGame snakeGame) {
        this.snakeGame = snakeGame;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not implemented
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                snakeGame.move(SnakeGame.MovingDirection.DOWN);
                break;
            case KeyEvent.VK_UP:
                snakeGame.move(SnakeGame.MovingDirection.UP);
                break;
            case KeyEvent.VK_LEFT:
                snakeGame.move(SnakeGame.MovingDirection.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                snakeGame.move(SnakeGame.MovingDirection.RIGHT);
                break;
            case KeyEvent.VK_ESCAPE:
                this.snakeGame.keyboardControl.shutdown();
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not implemented
    }
}
