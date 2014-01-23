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
        board.playPlot(x, y);
        assertEquals(0, board.getNeighbours(board.getPlot(x, y)).size());
        if (board.playPlot(x + 1, y)) {
            numNeighbours++;
        }
        if (board.playPlot(x - 1, y)) {
            numNeighbours++;
        }
        if (board.playPlot(x, y + 1)) {
            numNeighbours++;
        }
        if (board.playPlot(x, y - 1)) {
            numNeighbours++;
        }
        System.out.println("Tile: " + (char) (x + 65) + (y + 1));
        System.out.println("Number of neighbours: " + numNeighbours);
        assertEquals(numNeighbours, board.getNeighbours(board.getPlot(x, y)).size());
    }

    @Test
    public void companyTest() {
        assertEquals(0, board.getCompanyPlots(eclipse).size());
        board.playPlot(0, 0);
        board.playPlot(0, 1);
        board.playPlot(0, 2);
        board.assignPlot(0, 0, eclipse);
        board.assignPlot(0, 1, eclipse);
        board.assignPlot(0, 2, eclipse);
        board.assignPlot(0, 3, eclipse);
        assertEquals(3, board.getCompanyPlots(eclipse).size());
    }

}
