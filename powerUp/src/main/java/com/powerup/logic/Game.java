package com.powerup.logic;

import com.powerup.gui.GameFrame;
import com.powerup.gui.StartFrame;
import com.powerup.gui.Window;
import java.util.Arrays;
import javax.swing.SwingUtilities;

/**
 * Sets up and provides access to the game's logic components.
 *
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public final class Game {

   private final Board board;
   private final Market market;
   private DoE doe;
   private Player[] players;
   private Turn turn;
   private Window window;

   /**
    * Sets up the board and market.
    */
   public Game() {
      board = new Board(this);
      market = new Market(this);
   }

   /**
    * Creates new players for the game. <p /> Note: the only reason this method
    * is public is so it can be used in JUnit testing. It is not called from
    * anywhere outside of the Game class.
    *
    * @param n The number of players.
    * @param playerNames The names of the players.
    */
   public void createPlayers(int n, String[] playerNames) {
      players = new Player[n];
      for (int i = 0; i < n; i++) {
         players[i] = new Player(playerNames[i]);
      }
   }

   /**
    * Determines the starting order of the players. <p /> Note: the only reason
    * this method is public is so it can be used in JUnit testing. It is not
    * called from anywhere outside of the Game class.
    */
   public void determineStartOrder() {
      int i = 0;
      while (i < players.length) {
         Tile t = board.getRandomUnassignedTile();
         boolean hasNeighbour = false;
         for (int j = 1; j <= i; j++) {
            hasNeighbour = (t.isNextTo(players[j - 1].getTiles()[0]) ? true : hasNeighbour);
         }
         if (!hasNeighbour) {
            board.giveTileToPlayer(players[i], t);
            i++;
         }
      }
      Arrays.sort(players);
   }

   /**
    * Ends the game. <p /> Re-sorts the list of players by their total cash and
    * shows the results dialog, where they are revealed in order from least cash
    * to most.
    */
   public void end() {
      Arrays.sort(players);
      window.showResultsDialog();
   }

   public Board getBoard() {
      return board;
   }

   public DoE getDoe() {
      return doe;
   }

   public Market getMarket() {
      return market;
   }

   /**
    * Returns a player based on the given ID. <p /> The IDs run from 0 to 3,
    * with 0 being the player who had the first turn.
    *
    * @param i The ID of the player to be fetched
    * @return The player in question.
    */
   public Player getPlayer(int i) {
      if ((i >= 0) && (i < players.length)) {
         return players[i];
      } else {
         return null;
      }
   }

   public Player[] getPlayers() {
      return players;
   }

   public Turn getTurn() {
      return turn;
   }

   public Window getWindow() {
      return window;
   }

   /**
    * Invokes the primary game frame.
    *
    * @param numberOfPlayers The number of players in the game.
    * @param playerNames A list of the names for the players.
    * @param energyDept True if the DoE was selected, false otherwise.
    */
   public void launch(int numberOfPlayers, String[] playerNames, boolean energyDept) {
      createPlayers(numberOfPlayers, playerNames);
      if (energyDept) {
         doe = new DoE("The DoE");
      }
      GameFrame ui = new GameFrame(this);
      SwingUtilities.invokeLater(ui);
   }

   /**
    * Starts a new turn of the game.
    *
    * @param n The ID of the player whose turn it is.
    */
   public void newTurn(int n) {
      turn = new Turn(this, n);
   }

   /**
    * Runs some setup methods and begins the first turn. <p /> This cannot be
    * combined with the launch method because the GameFrame is invoked later by
    * SwingUtilities, and we must wait for it to load.
    *
    * @param window The primary game window.
    */
   public void setUp(Window window) {
      this.window = window;
      window.writepn("Determining starting order");
      determineStartOrder();
      distributeInitialTiles();
      newTurn(0);
   }

   /**
    * Launches the graphical user interface with the "start" frame.
    */
   public void start() {
      start(new Player[4]);
   }

   /**
    * Launches the graphical user interface with the "start" frame. <p /> Also
    * fills in the four player names to match the parameter list.
    *
    * @param players The list of players, for example from the previous game.
    */
   public void start(Player[] players) {
      StartFrame sFrame = new StartFrame(this, players);
      SwingUtilities.invokeLater(sFrame);
   }


   /* Private methods: no Javadoc */
   private void distributeInitialTiles() {
      for (int k = 0; k < players.length; k++) {
         Tile t = players[k].returnTile(0);
         board.playTileToBoard(t);
         window.write(players[k] + " drew " + t + " and will go " + ordinal(k + 1));
         for (int m = 0; m < 5; m++) {
            board.giveTileToPlayer(players[k]);
         }
      }
   }

   private String ordinal(int i) {
      if ((i >= 1) && (i < 5)) {
         String[] ordinals = new String[]{"", "first", "second", "third",
            "fourth"};
         return ordinals[i];
      } else {
         return null;
      }
   }
}
