package de.sebinside.logisnake.util;

import com.logitech.gaming.LogiLED;
import de.sebinside.logisnake.game.SnakeGame;

import java.awt.*;

public class KeyboardControl {

    private final int[] mappedKeyboard = {
            LogiLED.ONE,
            LogiLED.Q,
            LogiLED.A,
            LogiLED.Z};
    private int hue = 0;

    public void setLED(int x, int y, SnakeGame.Token token) {
        var color = getColor(token);
        LogiLED.LogiLedSetLightingForKeyWithKeyName(mappedKeyboard[y] + x, color.getRed(), color.getGreen(), color.getBlue());
    }

    public void init() {
        LogiLED.LogiLedInit();
        LogiLED.LogiLedSetLighting(0, 0, 0);
    }

    public void shutdown() {
        LogiLED.LogiLedShutdown();
    }

    public Color getColor(SnakeGame.Token token) {
        switch (token) {
            case FOOD:
                return Color.WHITE;
            case SNAKE:
                this.hue = (hue + 1) % 255;
                return Color.getHSBColor(hue / 255f, 1f, 1f);
            default:
                return Color.BLACK;
        }
    }

}
