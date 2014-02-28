package com.powerup.logic;

import java.awt.Color;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Oliver Lewisohn
 */
public class CompanyTest {

    Board board;
    Game game;
    Company companyA;
    Company companyB;
    Tile tile;

    public CompanyTest() {
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
        board = game.getBoard();
        companyA = game.getMarket().getCompany(0);
        companyB = game.getMarket().getCompany(5);
        tile = board.getRandomUnassignedTile();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testActivate() {
        assertFalse(companyA.getActive());
        assertNull(companyA.getHeadquarters());
        assertEquals(0, companyA.getShares().size());
        companyA.activate(tile);
        assertTrue(companyA.getActive());
        assertEquals(tile, companyA.getHeadquarters());
        assertEquals(25, companyA.getShares().size());

    }

    @Test
    public void testAddTile() {
        assertTrue(companyA.getTiles().isEmpty());
        board.playTileToBoard(tile);
        companyA.addTile(tile);
        assertTrue(companyA.getTiles().contains(tile));
    }

    @Test
    public void testDeactivate() {
        testActivate();
        companyA.deactivate();
        assertFalse(companyA.getActive());
    }

    @Test
    public void testGetColor() {
        assertEquals(Color.class, companyA.getColor().getClass());
    }

    @Test
    public void testGetSize() {
        assertEquals(0, companyA.getSize());
        testAddTile();
        assertEquals(1, companyA.getSize());
    }

    @Test
    public void testSellPrice() {
        assertEquals(200, companyA.sellPrice());
        for (int i = 0; i < 3; i++) {
            Tile t = board.getRandomUnassignedTile();
            board.playTileToBoard(t);
            companyA.addTile(t);
            assertEquals(200 + (50 * Math.max(i - 1, 0)), companyA.sellPrice());
        }
    }

    @Test
    public void testSellShare() {
        testActivate();
        for (int i = 0; i < 25; i++) {
            assertNotNull(companyA.sellShare());
            assertEquals(24 - i, companyA.getShares().size());
        }
        // Sold out
        assertNull(companyA.sellShare());
    }

    @Test
    public void testTakeOver() {
        board.playTileToBoard(tile);
        companyA.activate(tile);
        companyA.addTile(tile);
        Tile t = board.getRandomUnassignedTile();
        board.playTileToBoard(t);
        companyB.activate(t);
        companyB.addTile(t);
        for (int i = 0; i < 5; i++) {
            t = board.getRandomUnassignedTile();
            board.playTileToBoard(t);
            companyB.addTile(t);
        }
        companyA.takeOver(companyB);
        assertEquals(7, companyA.getSize());
        assertEquals(0, companyB.getSize());
        assertTrue(companyB.getShares().isEmpty());
        assertTrue(companyA.getActive());
        assertFalse(companyB.getActive());
    }
}
