package de.sebinside.logisnake.game;

import java.awt.*;

public enum Token {
    EMPTY,
    FOOD,
    SNAKE;

    static int hue = 0;

    public Color getColor() {
        switch (this) {
            case FOOD:
                return Color.WHITE;
            case SNAKE:
                hue = (hue + 1) % 255;
                return Color.getHSBColor(hue / 255f, 1f, 1f);
            default:
                return Color.BLACK;
        }
    }
}
