package com.powerup.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileTest {

    Game game;
    Company eclipse;

    public TileTest() {
        game = new Game();
        eclipse = game.getCompany(0);
        eclipse.deactivate(true);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void tileReferencesAreCorrect() {
        assertEquals("A0", game.getBoard().getTile(0, 0).toString());
        assertEquals("E4", game.getBoard().getTile(4, 4).toString());
        assertEquals("J9", game.getBoard().getTile(9, 9).toString());
    }

    @Test
    public void tileOwnerIsCorrect() {
        Tile tile = game.getBoard().getTile(5, 5);
        tile.setOwner(eclipse);
        assertEquals(eclipse, tile.getOwner());
    }

}
