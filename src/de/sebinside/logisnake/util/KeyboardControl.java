package de.sebinside.logisnake.util;

import com.logitech.gaming.LogiLED;

import de.sebinside.logisnake.game.Token;

import java.awt.*;

public class KeyboardControl {

    private final int[] mappedKeyboard = {
            LogiLED.ONE,
            LogiLED.Q,
            LogiLED.A,
            LogiLED.Z};

    public void setLED(int x, int y, Color color) {
        LogiLED.LogiLedSetLightingForKeyWithKeyName(mappedKeyboard[y] + x, color.getRed(), color.getGreen(), color.getBlue());
    }

    public void init() {
        LogiLED.LogiLedInit();
        LogiLED.LogiLedSetLighting(0, 0, 0);
    }

    public void shutdown() {
        LogiLED.LogiLedShutdown();
    }
}
