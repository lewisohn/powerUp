package com.powerup.logic;

import java.util.ArrayList;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BoardTest {

    Game game;
    Board board;
    Company eclipse;
    Random random;
    Player player1;
    Player player2;

    public BoardTest() {
        game = new Game();
        board = game.getBoard();
        eclipse = game.getCompany(0);
        eclipse.setActive(true);
        player1 = game.getPlayer(0);
        player2 = game.getPlayer(1);
        random = new Random();
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
    public void startWith100Tiles() {
        assertNotNull(board.getTiles());
        int sum = 0;
        for (int x = 0; x < board.getTiles().size(); x++) {
            sum += board.getTiles().get(x).size();
        }
        assertEquals(100, sum);
    }

//    private void playTileAndNeighbours(int x, int y) {
//        board.playTile(x, y);
//        board.playTile(x + 1, y);
//        board.playTile(x - 1, y);
//        board.playTile(x, y + 1);
//        board.playTile(x, y - 1);
//    }

//    @Test
//    public void plotHasCorrectNeighbours() {
//        int x = 0;
//        int y = 0;
//        playTileAndNeighbours(x, y);
//        ArrayList<Tile> neighbours = board.getNeighbours(board.getTile(x, y));
//        assertNull(board.getTile(x - 1, y));
//        assertNull(board.getTile(x, y - 1));
//        assertTrue(neighbours.contains(board.getTile(x + 1, y)));
//        assertTrue(neighbours.contains(board.getTile(x, y + 1)));
//    }

//    @Test
//    public void plotHasCorrectNeighbours2() {
//        int x = 9;
//        int y = 9;
//        playTileAndNeighbours(x, y);
//        ArrayList<Tile> neighbours = board.getNeighbours(board.getTile(x, y));
//        assertNull(board.getTile(x + 1, y));
//        assertNull(board.getTile(x, y + 1));
//        assertTrue(neighbours.contains(board.getTile(x - 1, y)));
//        assertTrue(neighbours.contains(board.getTile(x, y - 1)));
//    }

//    @Test
//    public void centralPlotHasFourNeighbours() {
//        int x = 1 + random.nextInt(8);
//        int y = 1 + random.nextInt(8);
//        playTileAndNeighbours(x, y);
//        assertEquals(4, board.getNeighbours(board.getTile(x, y)).size());
//    }

//    @Test
//    public void edgePlotHasThreeNeighbours() {
//        int x = 0;
//        int y = 1 + random.nextInt(8);
//        playTileAndNeighbours(x, y);
//        assertEquals(3, board.getNeighbours(board.getTile(x, y)).size());
//    }

//    @Test
//    public void cornerPlotHasTwoNeighbours() {
//        int x = 0;
//        int y = 0;
//        playTileAndNeighbours(x, y);
//        assertEquals(2, board.getNeighbours(board.getTile(x, y)).size());
//    }

//    @Test
//    public void cornerPlotHasTwoNeighbours2() {
//        int x = 9;
//        int y = 9;
//        playTileAndNeighbours(x, y);
//        assertEquals(2, board.getNeighbours(board.getTile(x, y)).size());
//    }

//    public void outOfBoundsPlotHasNoNeighbours() {
//        int x = -1;
//        int y = -1;
//        playTileAndNeighbours(x, y);
//        assertEquals(0, board.getNeighbours(board.getTile(x, y)).size());
//    }

//    @Test
//    public void anyPlotHasNoNeighboursIfTheyHaventBeenPlayed() {
//        int x = random.nextInt(9);
//        int y = random.nextInt(9);
//        assertTrue(board.playTile(x, y));
//        for (Tile t : board.getNeighbours(board.getTile(x, y))) {
//            System.out.println(t);
//        }
//        assertEquals(0, board.getNeighbours(board.getTile(x, y)).size());
//    }

    @Test
    public void cannotGetOutOfBoundsTile() {
        assertNull(board.getTile(-1, -1));
        assertNull(board.getTile(-1, 11));
        assertNull(board.getTile(11, -1));
        assertNull(board.getTile(11, 11));
        assertNull(board.getTile(-1, 5));
        assertNull(board.getTile(5, 11));
    }

//    @Test
//    public void cannotPlayOutOfBoundsTile() {
//        assertFalse(board.playTile(-1, -1));
//        assertFalse(board.playTile(-1, 11));
//        assertFalse(board.playTile(11, -1));
//        assertFalse(board.playTile(11, 11));
//        assertFalse(board.playTile(-1, 5));
//        assertFalse(board.playTile(5, 11));
//    }
}
