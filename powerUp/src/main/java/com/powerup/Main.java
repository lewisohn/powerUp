package com.powerup;

import com.powerup.logic.Game;

/**
 * Main class, launches the game.
 *
 * @author Oliver Lewisohn
 * @version 0.1
 * @since 2014-01-22
 */
public class Main {

    private static Game game;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        game = new Game();
        game.start();
    }
}
