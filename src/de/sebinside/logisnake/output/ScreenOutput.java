package de.sebinside.logisnake.output;

import de.sebinside.logisnake.game.MovingDirection;
import de.sebinside.logisnake.game.Position;
import de.sebinside.logisnake.game.SnakeGame;
import de.sebinside.logisnake.game.Token;
import de.sebinside.logisnake.util.DefaultKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * An {@link IOutputDevice} to print on the screen.
 */
public class ScreenOutput implements IOutputDevice {

    private JFrame frame;
    private GameComponent component;

    private int hue = 0;

    @Override
    public void init(Consumer<MovingDirection> eventDispatcher, Runnable shutdownDispatcher) {
        frame = new JFrame("LogiSnake");
        component = new GameComponent();
        frame.add(component);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addKeyListener(new DefaultKeyListener(eventDispatcher, shutdownDispatcher));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shutdownDispatcher.run();
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void shutdown() {
        frame.dispose();
    }

    @Override
    public void setLED(int x, int y, Token token) {
        Position position = new Position(x, y);
        switch (token) {
            case EMPTY:
                component.snakePositions.remove(position);
                component.foodPositions.remove(position);
                break;
            case FOOD:
                component.foodPositions.add(position);
                break;
            case SNAKE:
                this.hue = (hue + 1) % 255;
                component.snakePositions.put(position, Color.getHSBColor(hue / 255f, 1f, 1f));
                break;
        }
    }

    @Override
    public void drawStart() {
        component.foodPositions.clear();
        component.snakePositions.clear();
    }

    @Override
    public void drawEnd() {
        component.doPaint();
    }

    private static class GameComponent extends Canvas {

        public static final int CELL_SIZE = 40;

        public final Set<Position> foodPositions = new HashSet<>();
        public final Map<Position, Color> snakePositions = new HashMap<>();

        public GameComponent() {
            setPreferredSize(new Dimension(SnakeGame.BOARD_WIDTH * CELL_SIZE, SnakeGame.BOARD_HEIGHT * CELL_SIZE));
        }

        @Override
        public void paint(Graphics g) {
            // Do nothing
        }

        public void doPaint() {
            BufferStrategy buffer = getBufferStrategy();
            if (buffer == null) {
                createBufferStrategy(1);
                buffer = getBufferStrategy();
            }
            Graphics g = buffer.getDrawGraphics();
            if (g == null)
                return;

            g.clearRect(0, 0, SnakeGame.BOARD_WIDTH * CELL_SIZE, SnakeGame.BOARD_HEIGHT * CELL_SIZE);

            g.setColor(Color.BLACK);
            for (int x = 0; x < SnakeGame.BOARD_WIDTH; x++) {
                for (int y = 0; y < SnakeGame.BOARD_HEIGHT; y++) {
                    g.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
            for (Position pos : snakePositions.keySet()) {
                g.setColor(snakePositions.get(pos));
                g.fillArc(pos.getX() * CELL_SIZE, pos.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE, 0, 360);
            }
            for (Position pos : foodPositions) {
                g.setColor(Color.RED);
                g.fillArc((pos.getX() * CELL_SIZE) + 5, (pos.getY() * CELL_SIZE) + 10, CELL_SIZE - 10, CELL_SIZE - 10, 0, 360);
                g.setColor(Color.GREEN);
                g.fillPolygon(new int[]{(pos.getX() * CELL_SIZE) + (CELL_SIZE / 2), (pos.getX() * CELL_SIZE) + (CELL_SIZE / 2) + 5, (pos.getX() * CELL_SIZE) + (CELL_SIZE / 2) - 5},
                        new int[]{(pos.getY() * CELL_SIZE) + 10, (pos.getY() * CELL_SIZE) + 5, (pos.getY() * CELL_SIZE) + 5}, 3);
            }
        }

        @Override
        public boolean getIgnoreRepaint() {
            return true;
        }
    }
}
