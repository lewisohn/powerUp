package com.powerup;

import com.powerup.logic.Game;

/**
 * Main class.
 *
 * @author Oliver Lewisohn
 * @version 0.8
 * @since 2014-01-22
 */
public class Main {

    private static Game game;

    /**
     * Main method.
     * @param args Arguments.
     */
    public static void main(String[] args) {
        game = new Game();
        game.start();
    }
}
