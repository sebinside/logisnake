package de.sebinside.logisnake.game;

import de.sebinside.logisnake.util.KeyboardControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SnakeGame {

    public final KeyboardControl keyboardControl;

    private final int boardWidth = 10;
    private final int boardHeight = 4;

    private final Token[][] board = new Token[boardHeight][boardWidth];
    private final List<Position> snake = new ArrayList<>();
    private final int roundDelay = 300;
    private Position food;
    private MovingDirection direction = MovingDirection.LEFT;

    public SnakeGame(KeyboardControl keyboardControl) {
        this.keyboardControl = keyboardControl;
        resetBoard();
        resetSnake();
        addNewFood();

        new Thread(() -> {
            while (true) {
                calculateNewRound();
                try {
                    Thread.sleep(roundDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void resetSnake() {
        snake.clear();
        snake.add(new Position(5, 2));
        snake.add(new Position(6, 2));
        snake.add(new Position(7, 2));
        direction = MovingDirection.LEFT;
    }

    private void resetBoard() {
        for (var row : board) {
            Arrays.fill(row, Token.EMPTY);
        }
    }

    private void addNewFood() {
        var random = new Random();
        Position position;
        do {
            var x = random.nextInt(boardWidth);
            var y = random.nextInt(boardHeight);
            position = new Position(x, y);
        } while (this.snake.contains(position));
        this.food = position;
    }

    private void printBoard() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                var token = board[y][x];
                keyboardControl.setLED(x, y, token.getColor());
            }
        }
    }

    private void calculateNewRound() {
        moveSnake();
        checkSnakeValidity();
        checkFood();
        updateBoard();
        printBoard();
    }

    private void checkSnakeValidity() {
        if (this.snake.size() != this.snake.stream().distinct().count()) {
            resetSnake();
        }
    }

    private void checkFood() {
        if (this.snake.get(0).equals(this.food)) {
            var last = this.snake.get(this.snake.size() - 1);
            this.snake.add(new Position(last));

            if (snake.size() == boardHeight * boardWidth) {
                resetSnake();
            }

            addNewFood();
        }
    }

    private void moveSnake() {
        for (int i = this.snake.size() - 1; i > 0; i--) {
            this.snake.set(i, new Position(this.snake.get(i - 1)));
        }

        var head = this.snake.get(0);
        switch (this.direction) {
            case LEFT:
                head.setX((head.getX() - 1 + boardWidth) % boardWidth);
                break;
            case RIGHT:
                head.setX((head.getX() + 1 + boardWidth) % boardWidth);
                break;
            case UP:
                head.setY((head.getY() - 1 + boardHeight) % boardHeight);
                break;
            case DOWN:
                head.setY((head.getY() + 1 + boardHeight) % boardHeight);
                break;
        }
    }

    private void updateBoard() {
        resetBoard();
        for (Position position : this.snake) {
            this.board[position.y][position.x] = Token.SNAKE;
        }
        this.board[food.y][food.x] = Token.FOOD;
    }

    public void move(MovingDirection direction) {
        if (direction != this.direction.getOpposite()) {
            System.out.println("New direction: " + direction.toString());
            this.direction = direction;
        }
    }
}
