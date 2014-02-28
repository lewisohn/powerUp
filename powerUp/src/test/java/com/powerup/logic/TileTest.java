package com.powerup.logic;

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
public class TileTest {

    static Direction[] directions;
    Board board;
    Game game;
    Player player;
    Tile tile;
    Tile tile2;

    public TileTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        directions = new Direction[]{
            new Direction(-1, 0), new Direction(1, 0), new Direction(0, -1),
            new Direction(0, 1)
        };
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        game = new Game();
        game.createPlayers(2, new String[]{"Alice", "Bob"});
        player = game.getPlayer(0);
        board = game.getBoard();
        tile = board.getRandomUnassignedTile();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetLocation() {
        assertEquals(Tile.Location.NONE, tile.getLocation());
        player.addTileToHand(tile);
        assertEquals(Tile.Location.HAND, tile.getLocation());
        board.playTileToBoard(player.returnTile(0));
        assertEquals(Tile.Location.BOARD, tile.getLocation());
    }

    @Test
    public void testIsNextTo() {
        Tile t;
        // Possible neighbours
        for (Direction d : directions) {
            t = board.getTile(tile.getX() + d.getX(), tile.getY() + d.getY());
            if (t != null) {
                assertTrue(tile.isNextTo(t));
            } else {
                assertFalse(tile.isNextTo(t));
            }
        }
        // Definitely not a neighbour
        t = board.getTile(9 - tile.getX(), 9 - tile.getY());
        assertFalse(tile.isNextTo(t));
        // Null tile
        t = board.getTile(-1, 10);
        assertFalse(tile.isNextTo(t));
    }
}
