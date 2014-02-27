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
   private final int pid;
   private final int startActions;
   private final Player activePlayer;
   private final Random random;
   private final Window window;
   private boolean nonNeutralTilePlayed;
   private boolean tilePlayed;
   private int actions;

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
      int sa = random.nextInt(6);
      actions = startActions = (sa == 2 ? 2 : (sa < 2 ? 4 : 3));
      beginTurn();
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
      ArrayList<Company> companies = game.getBoard().getCompanyNeighbours(tile);
      Collections.sort(companies);
      if (companies.size() >= 1) {
         nonNeutralTilePlayed = true;
         Company survivor = companies.get(0);
         if (companies.size() >= 2) {
            survivor = getSurvivor(companies);
         }
         game.getBoard().recursivelyAddTilesToCompany(survivor, tile);
         window.write(survivor + " grew to size " + survivor.getSize());
      } else if (game.getBoard().getNeutralNeighbours(tile) > 0) {
         attemptToCreateNewCompany(tile);
      }
   }

   /**
    * Ends the turn and instructs the game to begin a new one.
    */
   public void endTurn() {
      window.writepn("End of " + activePlayer + "'s turn");
      game.newTurn((pid + 1) % game.getPlayers().length);
   }

   public int getActions() {
      return actions;
   }

   public Player getActivePlayer() {
      return activePlayer;
   }

   public boolean getTilePlayed() {
      return tilePlayed;
   }

   /* Private methods: no Javadoc */
   private void attemptToCreateNewCompany(Tile tile) {
      if (game.getMarket().allCompaniesActive()) {
         window.write("No new company could be established");
      } else {
         nonNeutralTilePlayed = true;
         Company company = game.getMarket().activateCompany(
                 window.showCreateCompanyDialog(), tile);
         activePlayer.buyShare(company, true);
         window.write(company + " was founded at " + tile + " with size " + company.getSize());
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

   private void beginTurn() {
      tilePlayed = nonNeutralTilePlayed = false;
      window.writeln("___________");
      window.write(activePlayer + "'s turn");
      window.write(activePlayer + " rolled " + startActions + " actions for this turn");
      window.activateTileListener();
      updateWindow();
   }

   private String cardinal(int i) {
      if ((i >= 1) && (i < 7)) {
         String[] cardinals = new String[]{"", "A", "Two", "Three", "Four",
            "Five", "Six"};
         return cardinals[i];
      } else {
         return null;
      }
   }

   private void determineBonuses(Company prey, ArrayList<ArrayList<Player>> shareholders) {
      int majPot = 10 * prey.sellPrice();
      int minPot = 5 * prey.sellPrice();
      ArrayList<Player> maj = shareholders.get(0);
      ArrayList<Player> min = shareholders.get(1);
      if (maj.size() > 1) {
         window.write("There was a " + maj.size() + "-way split" + " of the combined bonuses");
         majPot += minPot;
         minPot = 0;
      } else if (min.isEmpty()) {
         window.write(maj.get(0) + " was the sole shareholder");
         majPot += minPot;
         minPot = 0;
      } else {
         window.write(maj.get(0) + " was the majority shareholder");
      }
      giveBonuses(maj, majPot);
      if (minPot != 0) {
         if (min.size() > 1) {
            window.write("There was a " + min.size() + "-way split" + " of the minority bonus");
         } else {
            window.write(min.get(0) + " was the minority shareholder");
         }
         giveBonuses(min, minPot);
      }
   }

   private void dissolveAll() {
      window.writepn("Liquidating remaining companies");
      ArrayList<Company> list = game.getMarket().getActiveCompanies();
      for (Company company : list) {
         window.writepn("Liquidating " + company);
         determineBonuses(company, game.getMarket().determineMajorAndMinor(company));
         sellAllShares(company);
      }
      game.end();
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

   /*
    * This method is unnecessarily long at the moment, because it determines the
    * order in which companies are taken over - asking the user to decide, if
    * necessary - which doesn't affect anything. That functionality will be
    * added in a future release.
    */
   private Company getSurvivor(ArrayList<Company> companies) {
      ArrayList<Company> order = new ArrayList<>();
      int i = 0;
      while (i < companies.size()) {
         if ((i == companies.size() - 1) || (companies.get(i).getSize() > companies.get(i + 1).getSize())) {
            order.add(companies.get(i));
            i++;
         } else {
            int peers = 1;
            for (int j = i + 1; j < companies.size(); j++) {
               if (companies.get(i).getSize() == companies.get(j).getSize()) {
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

   private void giveBonuses(ArrayList<Player> shareholders, int potSize) {
      for (Player minPlayer : shareholders) {
         int split = (int) Math.ceil(potSize / shareholders.size());
         minPlayer.giveCash(split);
         window.write(minPlayer + " received a $" + split + " bonus");
      }
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

   private Company takeOver(Company predator, Company prey) {
      if (prey != null) {
         determineBonuses(prey, game.getMarket().determineMajorAndMinor(prey));
         sellAllShares(prey);
         predator.takeOver(prey);
         window.write(predator + " took over " + prey);
      }
      return predator;
   }

   private void updateWindow() {
      window.update(activePlayer, actions, startActions, tilePlayed);
   }
}
