package com.powerup.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CompanyTest {

    Game game;
    Board board;
    Company eclipse;
    Company maniac;
    Player player1;
    Player player2;

    public CompanyTest() {
        game = new Game();
        board = game.getBoard();
        eclipse = game.getEclipse();
        eclipse.setActive(true);
        maniac = game.getManiac();
        maniac.setActive(true);
        player1 = game.getPlayer(0);
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
    public void companyHasCorrectNumberOfTiles() {
        assertEquals(0, eclipse.getTiles().size());
        board.playTile(0, 0);
        board.playTile(0, 1);
        board.playTile(0, 2);
        eclipse.addTile(board.getTile(0, 0));
        eclipse.addTile(board.getTile(0, 1));
        eclipse.addTile(board.getTile(0, 2));
        eclipse.addTile(board.getTile(0, 3));
        assertEquals(3, eclipse.getTiles().size());
        assertEquals(eclipse, board.getTile(0,0).getOwner());
    }

    @Test
    public void companySellingPriceIsCalculatedCorrectly() {
        player1.buyStock(eclipse);
        companyHasCorrectNumberOfTiles();
        assertEquals(262, eclipse.sellPrice());
    }

    @Test
    public void companyCanBeDeactivated() {
        assertTrue(eclipse.getActive());
        eclipse.setActive(false);
        assertFalse(eclipse.getActive());

    }

    @Test
    public void companyTakeOverIsSuccessful() {
        companyHasCorrectNumberOfTiles();
        maniac.takeOver(eclipse);
        assertEquals(3, maniac.getTiles().size());
        assertEquals(0, eclipse.getTiles().size());
        assertEquals(25, eclipse.getStocks().size());
        assertFalse(eclipse.getActive());
    }

    @Test
    public void companyNameTest() {
        assertEquals("Eclipse Solar", eclipse.getName());
    }

}
