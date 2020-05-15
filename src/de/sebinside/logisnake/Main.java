package de.sebinside.logisnake;

import de.sebinside.logisnake.game.SnakeGame;
import de.sebinside.logisnake.util.KeyboardControl;
import de.sebinside.logisnake.util.SimpleKeyListener;
import de.sebinside.logisnake.util.Window;

import java.io.IOException;

public class Main{

    public static void main(String[] args) throws IOException {
        var keyboardControl = new KeyboardControl();
        keyboardControl.init();

        var snakeGame = new SnakeGame(keyboardControl);
        var listener = new SimpleKeyListener(snakeGame);
        new Window(listener);
    }

}
