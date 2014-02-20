package com.powerup.logic;

import com.powerup.gui.Window;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A game turn.
 *
 * @author Oliver Lewisohn
 * @since 2014-02-13
 */
public final class Turn {

   private final Game game;
   private final Window window;
   private final Player activePlayer;
   private final Random random;
   private boolean tilePlayed;
   private boolean nonNeutralTilePlayed;
   private int actions;
   private int startActions;
   private int pid;
   private ArrayList<Player> maj;
   private ArrayList<Player> min;

   /**
    * Starts a new turn.
    *
    * @param game The game.
    * @param pid The ID of the player whose turn is starting.
    */
   public Turn(Game game, int pid) {
      this.game = game;
      this.pid = pid;
      activePlayer = game.getPlayer(pid);
      window = game.getWindow();
      random = new Random();
      beginTurn();
   }

   private void beginTurn() {
      int sa = random.nextInt(6);
      actions = startActions = (sa == 2 ? 2 : (sa < 2 ? 4 : 3));
      tilePlayed = nonNeutralTilePlayed = false;
      window.writeln("___________");
      window.write(activePlayer + "'s turn");
      window.write(activePlayer + " rolled " + startActions + " actions for this turn");
      window.activateTileListener(game);
      updateWindow();
   }

   /**
    * Ends the turn and instructs the game to begin a new one.
    */
   public void endTurn() {
      window.writepn("End of " + activePlayer + "'s turn");
      pid++;
      pid = (pid % game.getPlayers().length);
      game.newTurn(pid);
   }

   private void determineMajorAndMinor(Company company) {
      maj = new ArrayList<>();
      min = new ArrayList<>();
      for (int i = 0; i < game.getPlayers().length; i++) {
         shareholderCheck(game.getPlayer(i), company);
      }
      if (game.getDoe() != null) {
         shareholderCheck(game.getDoe(), company);
      }
   }

   private void shareholderCheck(Player player, Company company) {
      int shares = player.getNumberOfShares(company);
      if (shares > 0) {
         if ((maj.isEmpty()) || (shares > maj.get(0).getNumberOfShares(company))) {
            min = (ArrayList<Player>) maj.clone();
            maj.clear();
            maj.add(player);
         } else if (shares == maj.get(0).getNumberOfShares(company)) {
            maj.add(player);
         } else {
            if ((min.isEmpty()) || (shares > min.get(0).getNumberOfShares(company))) {
               min.clear();
               min.add(player);
            } else if (shares == min.get(0).getNumberOfShares(company)) {
               min.add(player);
            }
         }
      }
   }

   private void giveBonuses(Company prey) {
      int majPot = 10 * prey.sellPrice();
      int minPot = 5 * prey.sellPrice();
      if (maj.size() > 1) {
         window.write("There was a " + maj.size() + "-way split"
                 + " of the combined bonuses");
         majPot += minPot;
         minPot = 0;
      } else if (maj.size() == 1) {
         if (min.isEmpty()) {
            window.write(maj.get(0) + " was the sole shareholder");
            majPot += minPot;
            minPot = 0;
         } else {
            window.write(maj.get(0) + " was the majority shareholder");
         }
      }
      for (Player majPlayer : maj) {
         int split = (int) Math.ceil(majPot / maj.size());
         majPlayer.giveCash(split);
         window.write(majPlayer + " received a $" + split + " bonus");
      }
      if (minPot != 0) {
         if (min.size() > 1) {
            window.write("There was a " + min.size() + "-way split"
                    + " of the minority bonus");
         } else {
            window.write(min.get(0) + " was the minority shareholder");
         }
         for (Player minPlayer : min) {
            int split = (int) Math.ceil(minPot / min.size());
            minPlayer.giveCash(split);
            window.write(minPlayer + " received a $" + split + " bonus");
         }
      }
   }

   /**
    * Checks to see which action needs to be performed after a tile is played.
    * <p /> The action could be: create a new company; join an existing company;
    * initiate a merger; or nothing. <p /> This method may be better located in
    * the Board class.
    *
    * @param tile
    */
   public void boardCheck(Tile tile) {
      tilePlayed = true;
      ArrayList<Company> companies = game.getBoard().companyNeighbours(tile);
      Collections.sort(companies);
      if (companies.size() >= 1) {
         nonNeutralTilePlayed = true;
         Company survivor = companies.get(0);
         if (companies.size() >= 2) {
            survivor = getSurvivor(companies);
         }
         recursivelyAddTilesToCompany(survivor, tile);
         window.write(survivor + " grew to size " + survivor.size());
      } else if (game.getBoard().neutralNeighbours(tile) > 0) {
         attemptToCreateNewCompany(tile);
      }
   }

   private Company getSurvivor(ArrayList<Company> companies) {
      /* This method might look long, but believe me, it took me many hours to
       * get it down to this length. There are just so many cases to consider.
       * Up to four companies can be merged at once. Call them A, B, C and D.
       * Let the notation X > Y mean that X is a bigger company than Y, let the
       * notation X = Y mean that X is the same size as Y, and let the symbol
       * # represent either > or =. Now, we have to deal with the following
       * cases (number of cases of each type in brackets): A # B (2), A # B # C
       * (4), and A # B # C # D (8), for a total of 14 cases. If two or more
       * companies are the same size, and one of them is the biggest company,
       * the user must select which one survives. If two or more companies are
       * the same size, and none of them is the biggest company, the user must
       * select which one is taken over first. If you can think of a better way
       * of performing this task, please let me know at
       * github.com/lewisohn/powerUp - thanks!
       */
      ArrayList<Company> order = new ArrayList<>();
      int i = 0;
      while (i < companies.size()) {
         if ((i == companies.size() - 1) || (companies.get(i).size() > companies.get(i + 1).size())) {
            order.add(companies.get(i));
            i++;
         } else {
            int peers = 1;
            for (int j = i + 1; j < companies.size(); j++) {
               if (companies.get(i).size() == companies.get(j).size()) {
                  peers = j;
               }
               for (int k = peers; k > 0; k--) {
                  Company c;
                  if (order.isEmpty()) {
                     c = window.showSurvivorDialog(new ArrayList<>(companies.subList(i, i + peers + 1)));
                  } else {
                     c = window.showOrderDialog(new ArrayList<>(companies.subList(i, i + peers + 1)));
                  }
                  order.add(c);
                  companies.remove(c);
               }
            }
         }
      }
      Company survivor = order.get(0);
      for (int k = 1; k < order.size(); k++) {
         survivor = takeOver(survivor, order.get(k));
      }
      return survivor;
   }

   private Company takeOver(Company predator, Company prey) {
      if (prey != null) {
         determineMajorAndMinor(prey);
         giveBonuses(prey);
         sellAllShares(prey);
         predator.takeOver(prey);
         window.write(predator + " took over " + prey);
      }
      return predator;
   }

   private void recursivelyAddTilesToCompany(Company company, Tile tile) {
      company.addTile(tile);
      if (game.getBoard().neutralNeighbours(tile) > 0) {
         for (Tile t : game.getBoard().getNeighbours(tile)) {
            if (t.getOwner() != company) {
               recursivelyAddTilesToCompany(company, t);
            }
         }
      }
   }

   private void attemptToCreateNewCompany(Tile tile) {
      if (game.getMarket().allCompaniesActive()) {
         window.write("No new company could be established");
      } else {
         nonNeutralTilePlayed = true;
         Company company = game.getMarket().activateCompany(
                 window.showCreateCompanyDialog(), tile);
         activePlayer.buyShare(company, true);
         window.write(company + " was founded at " + tile + " with size " + company.size());
         if (game.getDoe() != null) {
            int doePurchase = 1 + random.nextInt(10 - game.getPlayers().length);
            for (int i = doePurchase; i > 0; i--) {
               game.getDoe().buyShare(company, true);
            }
            window.write("The Department of Energy bought " + doePurchase
                    + (doePurchase == 1 ? " share" : " shares"));
         }
         window.write(activePlayer + " received a free share");
      }
      updateWindow();
   }

   public Player getActivePlayer() {
      return activePlayer;
   }

   private void updateWindow() {
      window.setActions(actions);
      window.setCash(activePlayer.getCash());
      window.updateTiles(activePlayer.getTiles());
      window.updateBoard();
      window.updateInfoPanel(activePlayer.toString() + "'s turn");
      window.buttonCheck(actions, startActions, activePlayer.getHandSize(),
              game.getBoard().unassignedTilesRemaining(), tilePlayed);
   }

   /**
    * Reduces the number of remaining actions by one and then checks for
    * consequences.
    */
   public void actionTaken() {
      actions--;
      updateWindow();
      if (actions <= 0) {
         window.write(activePlayer + " ran out of actions");
         window.deactivateTileListener();
      } else if (nonNeutralTilePlayed) {
         window.deactivateTileListener();
      }
      if (endOfGameCheck()) {
         window.deactivateTileListener();
         window.disableAllButtons();
         window.updateInfoPanel("game ended");
         dissolveAll();
      }

   }

   public int getActions() {
      return actions;
   }

   private void sellAllShares(Company prey) {
      for (int i = 0; i < game.getPlayers().length; i++) {
         if (game.getPlayer(i).getNumberOfShares(prey) > 0) {
            window.write(game.getPlayer(i) + " received $" + game.getPlayer(i).sellShares(prey)
                    + " in share sales");
         }
      }
      if (game.getDoe() != null) {
         window.write(game.getDoe() + " received $" + game.getDoe().sellShares(prey)
                 + " in share sales");
      }
   }

   private boolean endOfGameCheck() {
      if (game.getBoard().unassignedTilesRemaining() < 50) {
         for (int i = 0; i < 2; i++) {
            int threshold = 50 - (i == 1 ? 20 : 0);
            int n = game.getMarket().numberOfCompaniesLargerThan(threshold);
            if (n >= (i + 1)) {
               window.writepn("The game is over");
               window.write(cardinal(n) + (n > 1 ? " companies have" : " company has")
                       + " reached size " + threshold);
               window.deactivateTileListener();
               return true;
            }
         }
      } else if ((activePlayer.getHandSize() == 0) && (game.getBoard().unassignedTilesRemaining() == 0)) {
         window.writepn("The game is over");
         window.write(activePlayer + " has run out of tiles and no more can be drawn");
      }
      return false;
   }

   private String cardinal(int i) {
      String[] cardinals = new String[]{"", "A", "Two", "Three", "Four", "Five",
         "Six"};
      if ((i >= 1) && (i < 7)) {
         return cardinals[i];
      } else {
         return null;
      }
   }

   private void dissolveAll() {
      window.writepn("Liquidating remaining companies");
      ArrayList<Company> list = game.getMarket().activeCompanies();
      for (Company company : list) {
         window.writepn("Liquidating " + company);
         determineMajorAndMinor(company);
         giveBonuses(company);
         sellAllShares(company);
      }
      game.end();
   }

   public boolean getTilePlayed() {
      return tilePlayed;
   }
}
