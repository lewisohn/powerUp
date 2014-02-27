package com.powerup.logic;

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
public class PlayerTest {

   static Random random;
   Company company;
   Game game;
   Player player;
   Tile tile;

   public PlayerTest() {
   }

   @BeforeClass
   public static void setUpClass() {
      random = new Random();
   }

   @AfterClass
   public static void tearDownClass() {
   }

   @Before
   public void setUp() {
      game = new Game();
      company = game.getMarket().getCompany(0);
      game.createPlayers(2, new String[]{"Alice", "Bob"});
      player = game.getPlayer(0);
      tile = game.getBoard().getRandomUnassignedTile();
   }

   @After
   public void tearDown() {
   }

   /**
    * Test of addTileToHand method, of class Player.
    */
   @Test
   public void testAddTileToHand() {
      assertEquals(0, player.getHandSize());
      for (int i = 0; i < 6; i++) {
         Tile t = game.getBoard().getRandomUnassignedTile();
         if (i < 5) {
            assertTrue(player.addTileToHand(t));
            assertEquals(t, player.getTiles()[i]);
         } else {
            assertFalse(player.addTileToHand(t));
            assertEquals(5, player.getHandSize());
         }
      }
   }

   /**
    * Test of buyShare method, of class Player.
    */
   @Test
   public void testBuyShare() {
      company.activate(tile);
      int cash = player.getCash();
      assertEquals(0, player.getNumberOfShares(company));
      // freebie
      assertTrue(player.buyShare(company, true));
      assertEquals(1, player.getNumberOfShares(company));
      assertEquals(cash, player.getCash());
      // non-freebie
      assertTrue(player.buyShare(company, false));
      assertEquals(2, player.getNumberOfShares(company));
      assertEquals((cash - company.sellPrice()), player.getCash());
      // exactly the right amount of money
      player.setCash(company.sellPrice());
      assertTrue(player.buyShare(company, false));
      // no money
      assertFalse(player.buyShare(company, false));
   }

   /**
    * Test of getNumberOfShares method, of class Player.
    */
   @Test
   public void testGetNumberOfShares() {
      assertEquals(0, player.getNumberOfShares(company));
      company.activate(tile);
      int n = random.nextInt(30);
      for (int i = 0; i < n; i++) {
         player.buyShare(company, true);
      }
      assertEquals(Math.min(n, 25), player.getNumberOfShares(company));
      assertEquals(0, player.getNumberOfShares(game.getMarket().getCompany(5)));
   }

   /**
    * Test of giveCash method, of class Player.
    */
   @Test
   public void testGiveCash() {
      int cash = player.getCash();
      player.giveCash(999);
      assertEquals(cash + 999, player.getCash());
   }

   /**
    * Test of returnTile method, of class Player.
    */
   @Test
   public void testReturnTile() {
      // try to return a tile out of range
      assertNull(player.returnTile(-1));
      assertNull(player.returnTile(5));
      // put some tiles in the hand
      for (int i = 0; i < 3; i++) {
         player.addTileToHand(game.getBoard().getTile(i, i));
      }
      // try to return a tile not in hand
      assertNull(player.returnTile(4));
      // try to return a tile in the hand
      assertEquals(game.getBoard().getTile(0, 0), player.returnTile(0));
   }

   /**
    * Test of sellShares method, of class Player.
    */
   @Test
   public void testSellShares() {
      int cash = player.getCash();
      company.activate(tile);
      player.buyShare(company, true);
      assertEquals(1, player.getNumberOfShares(company));
      assertEquals(company.sellPrice(), player.sellShares(company));
      assertEquals(cash + company.sellPrice(), player.getCash());
      assertEquals(0, player.sellShares(game.getMarket().getCompany(5)));
      assertEquals(0, player.getNumberOfShares(company));
   }

   /**
    * Test of DoE, subclass of Player.
    */
   @Test
   public void testDoE() {
      DoE doe = new DoE("The DoE");
      assertTrue(doe instanceof Player);
      assertEquals("The DoE", doe.toString());
   }
}
//
//   /**
//    * Test of setCash method, of class Player.
//    */
//   @Test
//   public void testSetCash() {
//      System.out.println("setCash");
//      int cash = 0;
//      Player instance = null;
//      instance.setCash(cash);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }
//   /**
//    * Test of compareTo method, of class Player.
//    */
//   @Test
//   public void testCompareTo() {
//   }
