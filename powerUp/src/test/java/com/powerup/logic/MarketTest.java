package com.powerup.logic;

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
public class MarketTest {

   static Random random;
   Company companyA;
   Company companyB;
   Game game;
   Market market;
   Tile tile;

   public MarketTest() {
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
      market = game.getMarket();
      companyA = market.getCompany(0);
      companyB = market.getCompany(5);
      tile = game.getBoard().getRandomUnassignedTile();
   }

   @After
   public void tearDown() {
   }

   /**
    * Test of activateCompany method, of class Market.
    */
   @Test
   public void testActivateCompany() {
      assertFalse(companyA.getActive());
      assertNull(companyA.getHeadquarters());
      assertEquals(companyA, market.activateCompany(companyA, tile));
      assertTrue(companyA.getActive());
      assertEquals(tile, companyA.getHeadquarters());
   }

   /**
    * Test of allCompaniesActive method, of class Market.
    */
   @Test
   public void testAllCompaniesActive() {
      assertFalse(market.allCompaniesActive());
      for (int i = 0; i < 6; i++) {
         market.activateCompany(market.getCompany(i), game.getBoard().getRandomUnassignedTile());
      }
      assertTrue(market.allCompaniesActive());
   }

   /**
    * Test of determineMajorAndMinor method, of class Market.
    */
   @Test
   public void testDetermineMajorAndMinor() {
      game.createPlayers(4, new String[]{"Alice", "Bob", "Caroline", "Dan"});
      testActivateCompany();
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < (3 - i); j++) {
            game.getPlayer(i).buyShare(companyA, true);
         }
      }
      // Alice has 3 shares, Bob has 2, Caroline has 1 and Dan has 0
      assertEquals(game.getPlayer(0), market.determineMajorAndMinor(companyA).get(0).get(0));
      assertEquals(game.getPlayer(1), market.determineMajorAndMinor(companyA).get(1).get(0));
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < i; j++) {
            game.getPlayer(i).buyShare(companyA, true);
         }
      }
      // All four players have 3 shares
      assertEquals(4, market.determineMajorAndMinor(companyA).get(0).size());
      assertEquals(0, market.determineMajorAndMinor(companyA).get(1).size());
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < i; j++) {
            game.getPlayer(i).buyShare(companyA, true);
         }
      }
      // Alice has 3 shares, Bob has 4, Caroline has 5 and Dan has 6
      assertEquals(game.getPlayer(3), market.determineMajorAndMinor(companyA).get(0).get(0));
      assertEquals(game.getPlayer(2), market.determineMajorAndMinor(companyA).get(1).get(0));
   }

   /**
    * Test of getActiveCompanies method, of class Market.
    */
   @Test
   public void testGetActiveCompanies() {
      assertTrue(market.getActiveCompanies().isEmpty());
      testActivateCompany();
      assertTrue(market.getActiveCompanies().contains(companyA));
      assertFalse(market.getActiveCompanies().contains(companyB));
   }

   /**
    * Test of getCompany method with int parameter, of class Market.
    */
   @Test
   public void testGetCompanyInt() {
      // Out of range
      assertNull(market.getCompany(-1));
      assertNull(market.getCompany(6));
      // In range
      assertNotNull(market.getCompany(0));
   }

   /**
    * Test of getCompany method with String parameter, of class Market.
    */
   @Test
   public void testGetCompanyString() {
      // Wrong name
      assertNull(market.getCompany("Blue Flame Gas"));
      // Right name
      assertNotNull(market.getCompany("Whoops Uranium"));
   }

   /**
    * Test of getInactiveCompanies method, of class Market.
    */
   @Test
   public void testGetInactiveCompanies() {
      assertEquals(6, market.getInactiveCompanies().size());
      testActivateCompany();
      assertFalse(market.getInactiveCompanies().contains(companyA));
      assertTrue(market.getInactiveCompanies().contains(companyB));
   }

   /**
    * Test of numberOfCompaniesLargerThan method, of class Market.
    */
   @Test
   public void testNumberOfCompaniesAtLeastSize() {
      growToSize(companyA, 7);
      growToSize(companyB, 6);
      assertEquals(0, market.numberOfCompaniesAtLeastSize(8));
      assertEquals(1, market.numberOfCompaniesAtLeastSize(7));
      assertEquals(2, market.numberOfCompaniesAtLeastSize(6));
      assertEquals(2, market.numberOfCompaniesAtLeastSize(5));
   }

   private void growToSize(Company c, int n) {
      Tile t = game.getBoard().getRandomUnassignedTile();
      game.getBoard().playTileToBoard(t);
      if (!c.getActive()) {
         c.activate(t);
         c.addTile(t);
      }
      for (int i = 0; i < n - 1; i++) {
         t = game.getBoard().getRandomUnassignedTile();
         game.getBoard().playTileToBoard(t);
         c.addTile(t);
      }
   }
}
