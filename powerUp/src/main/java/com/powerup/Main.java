package com.powerup;

import com.powerup.gui.*;
import com.powerup.logic.*;
import javax.swing.SwingUtilities;

public class Main {

    private static Game game;
    private static Interface ui;

    public static void main(String[] args) {
        game = new Game();
        ui = new Interface(game);
        SwingUtilities.invokeLater(ui);

    }
}
