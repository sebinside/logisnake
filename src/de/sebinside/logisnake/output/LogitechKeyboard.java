package de.sebinside.logisnake.output;

import com.logitech.gaming.LogiLED;
import de.sebinside.logisnake.game.MovingDirection;
import de.sebinside.logisnake.game.Token;
import de.sebinside.logisnake.util.DefaultKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

/**
 * An {@link IOutputDevice} to print on a Logitech keyboard.
 */
public class LogitechKeyboard implements IOutputDevice {

    private final int[] mappedKeyboard = {
            LogiLED.ONE,
            LogiLED.Q,
            LogiLED.A,
            LogiLED.Z
    };

    private int hue = 0;

    @Override
    public void init(Consumer<MovingDirection> eventDispatcher, Runnable shutdownDispatcher) {
        LogiLED.LogiLedInit();
        LogiLED.LogiLedSetLighting(0, 0, 0);

        // Initialise dummy window

        JFrame frame = new JFrame("logisnake");
        frame.setUndecorated(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shutdownDispatcher.run();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // We lost focus. Shutdown
                shutdownDispatcher.run();
            }
        });
        frame.addKeyListener(new DefaultKeyListener(eventDispatcher, shutdownDispatcher));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(0, 0);
        frame.setLocation(0, 0);
        frame.setVisible(true);
        frame.requestFocus();
    }

    @Override
    public void shutdown() {
        LogiLED.LogiLedShutdown();
    }

    @Override
    public void setLED(int x, int y, Token token) {
        var color = getColor(token);
        LogiLED.LogiLedSetLightingForKeyWithKeyName(mappedKeyboard[y] + x, color.getRed(), color.getGreen(), color.getBlue());
    }

    private Color getColor(Token token) {
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
