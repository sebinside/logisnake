package de.sebinside.logisnake.game;

import de.sebinside.logisnake.output.IOutputDevice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Implements the game logic of snake. This needs an instance of {@link IOutputDevice} to display the game and receive
 * inputs.
 */
public class SnakeGame {

    /**
     * The width of th board. This is hardcoded because LogiSnake is intended to be played on a keyboard with a defined size
     */
    public static final int BOARD_WIDTH = 10;

    /**
     * The height of the board. This is hardcoded because LogiSnake is intended to be played on a keyboard with a defined size.
     */
    public static final int BOARD_HEIGHT = 4;

    /**
     * The initial delay between two moves of the snake.
     */
    public static final int INITIAL_ROUND_DELAY = 300;

    private final IOutputDevice output;
    private final Object lock = new Object(); // Used to synchronise access to the current direction as it's
                                              // changed from another thread via `IOutputDevice`
    private final Token[][] board = new Token[BOARD_HEIGHT][BOARD_WIDTH];
    private final List<Position> snake = new ArrayList<>();
    private final Random random = new Random();
    private final Position food = new Position(0, 0);

    private MovingDirection direction = MovingDirection.LEFT;
    private int roundDelay = INITIAL_ROUND_DELAY;

    /**
     * Creates a new SnakeGame
     *
     * @param output The Device to draw the game and receive keyboard input.
     */
    public SnakeGame(IOutputDevice output) {
        this.output = output;

        output.init(this::directionChangeEvent, () -> {
            output.shutdown();
            System.exit(0);
        });

        resetBoard();
        resetSnake();
        addNewFood();
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        new GameThread().start();
    }

    /**
     * Resets the game.
     */
    private void resetSnake() {
        synchronized (lock) {
            snake.clear();
            snake.add(new Position(5, 2));
            snake.add(new Position(6, 2));
            snake.add(new Position(7, 2));
            direction = MovingDirection.LEFT;
        }
    }

    /**
     * Sets all values on the board to EMPTY.
     */
    private void resetBoard() {
        Arrays.stream(board).forEach(row -> Arrays.fill(row, Token.EMPTY));
    }

    /**
     * Creates a new random food on the board.
     */
    private void addNewFood() {
        Position position;
        do {
            position = new Position(random.nextInt(BOARD_WIDTH), random.nextInt(BOARD_HEIGHT));
        } while (this.snake.contains(position));
        this.food.setX(position.getX());
        this.food.setY(position.getY());
    }

    /**
     * Prints the board using the {@link IOutputDevice}.
     */
    private void printBoard() {
        output.drawStart();
        try {
            for (int y = 0; y < board.length; y++) {
                for (int x = 0; x < board[y].length; x++) {
                    output.setLED(x, y, board[y][x]);
                }
            }
        } finally {
            output.drawEnd();
        }
    }

    /**
     * Performs a step of the snake and prints it.
     */
    private void calculateNewRound() {
        moveSnake();
        checkSnakeValidity();
        checkFood();
        updateBoard();
        printBoard();
    }

    /**
     * Checks whether the snake crashed its body and if so resets the game.
     */
    private void checkSnakeValidity() {
        if (this.snake.size() != this.snake.stream().distinct().count()) {
            roundDelay = INITIAL_ROUND_DELAY; // Reset the round delay to it's default value as it might be lower if
                                              // you won before.
            resetSnake();
        }
    }

    /**
     * Checks if the snake ate food. If the snake covers the whole board a new game is started with a shorter round-delay.
     */
    private void checkFood() {
        if (this.snake.get(0).equals(this.food)) {
            var last = this.snake.get(this.snake.size() - 1);
            this.snake.add(new Position(last.getX(), last.getY()));

            if (snake.size() >= BOARD_HEIGHT * BOARD_WIDTH) {
                roundDelay = (int) (roundDelay / 1.5); // The snake will move faster if  you won once.
                resetSnake();
            }

            addNewFood();
        }
    }

    /**
     * Moves the snake by one in the current direction.
     */
    private void moveSnake() {
        for (int i = this.snake.size() - 1; i > 0; i--) {
            this.snake.set(i, new Position(this.snake.get(i - 1).getX(), this.snake.get(i - 1).getY()));
        }

        var head = this.snake.get(0);
        head.setX((head.getX() + direction.offsetX + BOARD_WIDTH) % BOARD_WIDTH);
        head.setY((head.getY() + direction.offsetY + BOARD_HEIGHT) % BOARD_HEIGHT);
    }

    /**
     * Sets all values of the board to what they should be at this point in time.
     */
    private void updateBoard() {
        resetBoard();
        for (Position position : this.snake) {
            this.board[position.getY()][position.getX()] = Token.SNAKE;
        }
        this.board[food.getY()][food.getX()] = Token.FOOD;
    }

    /**
     * Processes s direction change event by {@link IOutputDevice}.
     *
     * @param direction The new direction.
     */
    private void directionChangeEvent(MovingDirection direction) {
        synchronized (lock) {
            if (direction != this.direction.opposite()) {
                this.direction = direction;
            }
        }
    }

    /**
     * The class implementing the game loop.
     */
    private class GameThread extends Thread {

        @Override
        public void run() {
            //noinspection InfiniteLoopStatement
            while (true) {
                synchronized (lock) {
                    calculateNewRound();
                }
                try {
                    //noinspection BusyWait
                    Thread.sleep(roundDelay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
    }
}
