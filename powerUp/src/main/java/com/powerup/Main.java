package com.powerup;

import com.powerup.gui.*;
import com.powerup.logic.*;
import javax.swing.SwingUtilities;

/**
 * Main class, launches the game.
 * @author Oliver Lewisohn
 * @version 0.1
 * @since 2014-01-22
 */
public class Main {

    private static Game game;
    private static UserInterface ui;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        game = new Game();
        ui = new UserInterface(game);
        SwingUtilities.invokeLater(ui);

    }
}
