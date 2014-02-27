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
public class GameTest {

   static Random random;
   Game game;

   public GameTest() {
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
      this.game = new Game();
   }

   @After
   public void tearDown() {
   }

   /**
    * Test of createPlayers method, of class Game.
    */
   @Test
   public void testCreatePlayers() {
      assertNull(game.getPlayers());
      game.createPlayers(2, new String[]{"Alice", "Bob"});
      assertEquals(2, game.getPlayers().length);
   }

   @Test
   public void testDetermineStartOrder() {
      testCreatePlayers();
      game.determineStartOrder();
      assertTrue(game.getPlayer(0).getTiles()[0].compareTo(game.getPlayer(1).getTiles()[0]) < 0);
   }

   @Test
   public void testGetPlayer() {
      testCreatePlayers();
      // Out of range
      assertNull(game.getPlayer(-1));
      assertNull(game.getPlayer(2));
      // In range
      assertEquals("Alice", game.getPlayer(0).toString());
   }

   /*
    * Note: there would be more tests here, but all the remaining methods in the
    * Game class are either simple getters or involve the GUI in some way. As I
    * don't know how to write tests for the GUI yet, it will have to stay bare.
    */
}
