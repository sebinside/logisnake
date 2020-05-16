package de.sebinside.logisnake;

import de.sebinside.logisnake.game.SnakeGame;
import de.sebinside.logisnake.output.IOutputDevice;
import de.sebinside.logisnake.output.LogitechKeyboard;
import de.sebinside.logisnake.output.ScreenOutput;

import java.io.IOException;

public class Main {

    /**
     * This method checks if the Logitech Java-Library is present. If it is it'll use a keyboard as output-type. Else
     * it'll use a screen. the it'll start a game.
     */
    public static void main(String[] args) {

        IOutputDevice output;
        try {
            Class.forName("com.logitech.gaming.LogiLED");
            output = new LogitechKeyboard();
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            // No logitech found, we use a screen output instead.
            output = new ScreenOutput();
        }

        SnakeGame game = new SnakeGame(output);

        game.startGame();
    }

}
