package com.powerup.logic;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    Game game;
    Board board;
    Company eclipse;
    Company maniac;
    Random random;
    Player player1;
    Player player2;

    public PlayerTest() {
        game = new Game();
        board = game.getBoard();
        eclipse = game.getEclipse();
        eclipse.setActive(true);
        maniac = game.getManiac();
        maniac.setActive(true);
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
    public void playerHasNoStockInitially() {
        assertEquals(0, player1.getStockAmount(eclipse));
    }

    @Test
    public void playerCanBuyStock() {
        player1.setCash(602);
        player1.buyStock(eclipse);
        player1.buyStock(eclipse);
        player1.buyStock(maniac);
        assertEquals(2, player1.getStockAmount(eclipse));
        assertEquals(0, player1.getCash());

    }

    @Test
    public void playerHasTooLittleMoneyToBuyStock() {
        player1.setCash(100);
        player1.buyStock(eclipse);
        assertEquals(0, player1.getStockAmount(eclipse));
    }

    @Test
    public void playerCannotBuyStockBecauseCompanyHasNoneLeft() {
        player1.setCash(5600);
        for (int i = 0; i < 25; i++) {
            player1.buyStock(eclipse);
        }
        player2.buyStock(eclipse);
        assertEquals(0, eclipse.getStocks().size());
        assertEquals(0, player2.getStockAmount(eclipse));
    }
    
    @Test
    public void playerNameTest() {
        assertEquals("Player 1",player1.getName());
    }
}
