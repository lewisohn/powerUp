package com.powerup.logic;

import com.powerup.logic.Tile.Location;
import java.util.ArrayList;
import java.util.Random;
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
public class BoardTest {

    static Direction[] directions;
    static Random random;
    Board board;
    Game game;
    Tile tile;

    public BoardTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        directions = new Direction[]{
            new Direction(-1, 0), new Direction(1, 0), new Direction(0, -1),
            new Direction(0, 1)
        };
        random = new Random();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        game = new Game();
        board = game.getBoard();
        tile = board.getRandomUnassignedTile();
    }

    @After
    public void tearDown() {
    }

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

    @Test
    public void testGetCompanyNeighbours() {
        board.playTileToBoard(tile);
        assertEquals(0, board.getCompanyNeighbours(tile).size());
        Direction d = directions[0];
        Tile t = board.getTile(tile.getX() + d.getX(), tile.getY() + d.getY());
        if (t != null) {
            board.playTileToBoard(t);
            game.getMarket().getCompany(0).addTile(t);
            assertEquals(1, board.getCompanyNeighbours(tile).size());
        }
    }

    @Test
    public void testGetHighlightedTile() {
        assertNull(board.getHighlightedTile());
        board.setHighlightedTile(tile);
        assertEquals(tile, board.getHighlightedTile());
    }

    @Test
    public void testGetNeighbours() {
        board.playTileToBoard(tile);
        assertEquals(0, board.getNeighbours(tile).size());
        for (Direction d : directions) {
            Tile t = board.getTile(tile.getX() + d.getX(), tile.getY() + d.getY());
            if (t != null) {
                board.playTileToBoard(t);
                assertTrue(board.getNeighbours(tile).contains(t));
            }
        }
    }

    @Test
    public void testGetNeutralNeighbours() {
        board.playTileToBoard(tile);
        assertEquals(0, board.getNeutralNeighbours(tile));
        int n = 0;
        for (Direction d : directions) {
            Tile t = board.getTile(tile.getX() + d.getX(), tile.getY() + d.getY());
            if (t != null) {
                board.playTileToBoard(t);
                assertTrue(board.getNeighbours(tile).contains(t));
                if (random.nextInt(2) == 0) {
                    n++;
                } else {
                    game.getMarket().getCompany(0).addTile(t);
                }
            }
        }
    }

    @Test
    public void testGetTile() {
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        Tile t = board.getTile(x, y);
        assertEquals(x, t.getX());
        assertEquals(y, t.getY());
    }

    @Test
    public void testGetTiles() {
        int n = 0;
        for (ArrayList<Tile> list : board.getTiles()) {
            assertEquals(10, list.size());
            n++;
        }
        assertEquals(10, n);
    }

    @Test
    public void testGetRandomUnassignedTile() {
        Tile t = board.getRandomUnassignedTile();
        assertEquals(Location.NONE, t.getLocation());
    }

    @Test
    public void testGiveRandomTileToPlayer() {
        game.createPlayers(2, new String[]{"Alice", "Bob"});
        assertEquals(0, game.getPlayer(0).getHandSize());
        board.giveTileToPlayer(game.getPlayer(0));
        assertEquals(1, game.getPlayer(0).getHandSize());
        for (ArrayList<Tile> list : board.getTiles()) {
            for (Tile t : list) {
                if (t.getLocation() != Location.HAND) {
                    t.setLocation(Location.BOARD);
                }
            }
        }
        // No unassigned tiles remain, hand size should remain the same
        board.giveTileToPlayer(game.getPlayer(0));
        assertEquals(1, game.getPlayer(0).getHandSize());
    }

    @Test
    public void testGiveSpecificTileToPlayer() {
        game.createPlayers(2, new String[]{"Alice", "Bob"});
        assertEquals(0, game.getPlayer(0).getHandSize());
        board.giveTileToPlayer(game.getPlayer(0), tile);
        assertEquals(tile, game.getPlayer(0).getTiles()[0]);
    }

    @Test
    public void testPlayTileToBoard() {
        assertEquals(Location.NONE, tile.getLocation());
        board.playTileToBoard(tile);
        assertEquals(Location.BOARD, tile.getLocation());
    }

    @Test
    public void testRecursivelyAddTilesToCompany() {
        board.playTileToBoard(tile);
        Tile t = null;
        Tile t2 = null;
        for (Direction d : directions) {
            t = board.getTile(tile.getX() + d.getX(), tile.getY() + d.getY());
            if (t != null) {
                board.playTileToBoard(t);
                for (Direction d2 : directions) {
                    t2 = board.getTile(t.getX() + d2.getX(), t.getY() + d2.getY());
                    if (t2 != null) {
                        board.playTileToBoard(t2);
                    }
                }
            }
        }
        board.recursivelyAddTilesToCompany(game.getMarket().getCompany(0), tile);
        assertTrue(game.getMarket().getCompany(0).getTiles().contains(tile));
        if (t != null) {
            assertTrue(game.getMarket().getCompany(0).getTiles().contains(t));
        }
        if (t2 != null) {
            assertTrue(game.getMarket().getCompany(0).getTiles().contains(t2));
        }
    }

    @Test
    public void testRefillPlayerHand() {
        game.createPlayers(2, new String[]{"Player 1", "Player 2"});
        assertEquals(0, game.getPlayer(0).getHandSize());
        board.refillPlayerHand(game.getPlayer(0));
        assertEquals(5, game.getPlayer(0).getHandSize());
    }

    @Test
    public void testSetHighlightedTile() {
        assertNull(board.getHighlightedTile());
        Tile t = board.getRandomUnassignedTile();
        board.setHighlightedTile(t);
        assertEquals(t, board.getHighlightedTile());
    }

    @Test
    public void testUnassignedTilesRemaining() {
        assertEquals(100, board.unassignedTilesRemaining());
        for (ArrayList<Tile> list : board.getTiles()) {
            for (Tile t : list) {
                t.setLocation(Location.BOARD);
            }
        }
        assertEquals(0, board.unassignedTilesRemaining());
    }
}
