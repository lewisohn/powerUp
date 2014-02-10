package com.powerup.logic;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        eclipse = game.getCompany(0);
        eclipse.setActive(true);
        maniac = game.getCompany(1);
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
    public void playerHasNoSharesInitially() {
        assertEquals(0, player1.getNumberOfShares(eclipse));
    }

    @Test
    public void playerCanBuyShares() {
        player1.setCash(602);
        player1.buyShare(eclipse);
        player1.buyShare(eclipse);
        player1.buyShare(maniac);
        assertEquals(2, player1.getNumberOfShares(eclipse));
        assertEquals(0, player1.getCash());

    }

    @Test
    public void playerHasTooLittleMoneyToBuyShares() {
        player1.setCash(100);
        player1.buyShare(eclipse);
        assertEquals(0, player1.getNumberOfShares(eclipse));
    }

    @Test
    public void playerCannotBuySharesBecauseCompanyHasNoneLeft() {
        player1.setCash(5600);
        for (int i = 0; i < 25; i++) {
            player1.buyShare(eclipse);
        }
        player2.buyShare(eclipse);
        assertEquals(0, eclipse.getShares().size());
        assertEquals(0, player2.getNumberOfShares(eclipse));
    }
    
    @Test
    public void playerNameTest() {
        assertEquals("Player 1",player1.toString());
    }
}
