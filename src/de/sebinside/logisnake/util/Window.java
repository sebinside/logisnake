package de.sebinside.logisnake.util;

import javax.swing.*;
import java.awt.event.KeyListener;

public class Window extends JFrame {

    public Window(KeyListener keyListener) {
        this.addKeyListener(keyListener);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
