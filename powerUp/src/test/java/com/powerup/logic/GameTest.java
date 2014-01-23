package com.powerup.logic;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    Game game;
    Board board;
    Company eclipse;
    Random random;

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
        board = game.getBoard();
        eclipse = game.getEclipse();
        random = new Random();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void upToFourNeighbours() {
        int x = random.nextInt(9);
        int y = random.nextInt(9);
        int numNeighbours = 0;
        board.playTile(x, y);
        assertEquals(0, board.getNeighbours(board.getTile(x, y)).size());
        if (board.playTile(x + 1, y)) {
            numNeighbours++;
        }
        if (board.playTile(x - 1, y)) {
            numNeighbours++;
        }
        if (board.playTile(x, y + 1)) {
            numNeighbours++;
        }
        if (board.playTile(x, y - 1)) {
            numNeighbours++;
        }
        assertEquals(numNeighbours, board.getNeighbours(board.getTile(x, y)).size());
    }

    @Test
    public void companyTest() {
        assertEquals(0, board.getCompanyTiles(eclipse).size());
        board.playTile(0, 0);
        board.playTile(0, 1);
        board.playTile(0, 2);
        board.assignTile(0, 0, eclipse);
        board.assignTile(0, 1, eclipse);
        board.assignTile(0, 2, eclipse);
        board.assignTile(0, 3, eclipse);
        assertEquals(3, board.getCompanyTiles(eclipse).size());
    }

}
