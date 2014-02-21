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
 * @author Oliver Lewisohn
 */
public class BoardTest {

    static Game game;
    static Board board;
    static Direction[] directions;
    Tile tile;

    public BoardTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        game = new Game();
        board = new Board(game);
        directions = new Direction[]{
            new Direction(-1, 0), new Direction(1, 0), new Direction(0, -1), new Direction(0, 1)
        };
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        tile = board.getRandomUnassignedTile();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllConnectedTiles method, of class Board.
     */
    @Test
    public void testGetAllConnectedTiles() {
        board.playTileToBoard(tile);
        assertEquals(1, board.getAllConnectedTiles(tile).size());
        for (Direction d : directions) {
            Tile t = board.getTile(tile.getX() + d.getX(), tile.getY() + d.getY());
            if (t != null) {
                board.playTileToBoard(t);
                assertTrue(board.getAllConnectedTiles(tile).contains(t));
                for (Direction d2 : directions) {
                    Tile t2 = board.getTile(t.getX() + d2.getX(), t.getY() + d2.getY());
                    if (t2 != null) {
                        board.playTileToBoard(t2);
                        assertTrue(board.getAllConnectedTiles(tile).contains(t2));
                    }
                }

            }
        }
    }

    /**
     * Test of getCompanyNeighbours method, of class Board.
     */
    @Test
    public void testGetCompanyNeighbours() {
        System.out.println("getCompanyNeighbours");
        Tile tile = null;
        Board instance = null;
        ArrayList<Company> expResult = null;
        ArrayList<Company> result = instance.getCompanyNeighbours(tile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHighlightedTile method, of class Board.
     */
    @Test
    public void testGetHighlightedTile() {
        System.out.println("getHighlightedTile");
        Board instance = null;
        Tile expResult = null;
        Tile result = instance.getHighlightedTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNeighbours method, of class Board.
     */
    @Test
    public void testGetNeighbours() {
        System.out.println("getNeighbours");
        Tile tile = null;
        Board instance = null;
        ArrayList<Tile> expResult = null;
        ArrayList<Tile> result = instance.getNeighbours(tile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNeutralNeighbours method, of class Board.
     */
    @Test
    public void testGetNeutralNeighbours() {
        System.out.println("getNeutralNeighbours");
        Tile tile = null;
        Board instance = null;
        int expResult = 0;
        int result = instance.getNeutralNeighbours(tile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTile method, of class Board.
     */
    @Test
    public void testGetTile() {
        System.out.println("getTile");
        int x = 0;
        int y = 0;
        Board instance = null;
        Tile expResult = null;
        Tile result = instance.getTile(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTiles method, of class Board.
     */
    @Test
    public void testGetTiles() {
        System.out.println("getTiles");
        Board instance = null;
        ArrayList<ArrayList<Tile>> expResult = null;
        ArrayList<ArrayList<Tile>> result = instance.getTiles();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRandomUnassignedTile method, of class Board.
     */
    @Test
    public void testGetRandomUnassignedTile() {
        System.out.println("getRandomUnassignedTile");
        Board instance = null;
        Tile expResult = null;
        Tile result = instance.getRandomUnassignedTile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of giveTileToPlayer method, of class Board.
     */
    @Test
    public void testGiveTileToPlayer_Player() {
        System.out.println("giveTileToPlayer");
        Player player = null;
        Board instance = null;
        instance.giveTileToPlayer(player);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of giveTileToPlayer method, of class Board.
     */
    @Test
    public void testGiveTileToPlayer_Player_Tile() {
        System.out.println("giveTileToPlayer");
        Player player = null;
        Tile tile = null;
        Board instance = null;
        instance.giveTileToPlayer(player, tile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of playTileToBoard method, of class Board.
     */
    @Test
    public void testPlayTileToBoard() {
        System.out.println("playTileToBoard");
        Tile tile = null;
        Board instance = null;
        boolean expResult = false;
        boolean result = instance.playTileToBoard(tile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recursivelyAddTilesToCompany method, of class Board.
     */
    @Test
    public void testRecursivelyAddTilesToCompany() {
        System.out.println("recursivelyAddTilesToCompany");
        Company company = null;
        Tile tile = null;
        Board instance = null;
        instance.recursivelyAddTilesToCompany(company, tile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refillPlayerHand method, of class Board.
     */
    @Test
    public void testRefillPlayerHand() {
        System.out.println("refillPlayerHand");
        Player player = null;
        Board instance = null;
        instance.refillPlayerHand(player);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHighlightedTile method, of class Board.
     */
    @Test
    public void testSetHighlightedTile() {
        System.out.println("setHighlightedTile");
        Tile tile = null;
        Board instance = null;
        instance.setHighlightedTile(tile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unassignedTilesRemaining method, of class Board.
     */
    @Test
    public void testUnassignedTilesRemaining() {
        System.out.println("unassignedTilesRemaining");
        Board instance = null;
        int expResult = 0;
        int result = instance.unassignedTilesRemaining();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
