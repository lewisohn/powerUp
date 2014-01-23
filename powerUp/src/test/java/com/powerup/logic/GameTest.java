package com.powerup.logic;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lewisohn
 */
public class GameTest {

    Game game;

    public GameTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        game = new Game();

    }

    @After
    public void tearDown() {
    }

    @Test
    public void noNeighbours() {
        game.getPlot(5, 5).play();
        assertEquals(0, game.getNeighbours(game.getPlot(5, 5)).size());
    }

    @Test
    public void twoNeighbours() {
        game.getPlot(5, 5).play();
        game.getPlot(6, 5).play();
        game.getPlot(5, 6).play();
        assertEquals(2, game.getNeighbours(game.getPlot(5, 5)).size());
    }

    @Test
    public void fourNeighbours() {
        game.getPlot(5, 5).play();
        game.getPlot(6, 5).play();
        game.getPlot(4, 5).play();
        game.getPlot(5, 6).play();
        game.getPlot(5, 4).play();
        assertEquals(4, game.getNeighbours(game.getPlot(5, 5)).size());
    }

}
