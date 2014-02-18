package com.powerup.logic;

import com.powerup.gui.Window;
import java.util.ArrayList;
import java.util.Arrays;
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
    private Player activePlayer;
    private int actions;
    private int startActions;
    private int pid;
    private boolean tilePlayed;
    private boolean companyCreated;
    private final Random random;

    /**
     * Starts a new turn.
     *
     * @param game The game.
     * @param pid The ID of the player whose turn is starting.
     */
    public Turn(Game game, int pid) {
        this.game = game;
        this.window = game.getWindow();
        this.pid = pid;
        this.random = new Random();
        beginTurn();
    }

    private void beginTurn() {
        this.activePlayer = game.getPlayer(pid);
        int sa = random.nextInt(6);
        if (sa == 2) {
            startActions = 2;
        } else {
            startActions = (sa < 2 ? 4 : 3);
        }
        this.actions = startActions;
        this.tilePlayed = false;
        this.companyCreated = false;
        window.write("---");
        window.write(activePlayer + "'s turn");
        window.write(activePlayer + " rolled " + startActions + " actions for this turn");
        window.activateTileListener(game);
        updateWindow();
        window.buttonCheck(actions, startActions, activePlayer.getHandSize(),
                game.getBoard().unassignedTilesRemaining(), tilePlayed);
    }

    /**
     * Ends the turn and instructs the game to begin a new one.
     */
    public void endTurn() {
        window.writepn("End of " + activePlayer + "'s turn");
        pid++;
        pid = (pid % 4);
        game.newTurn(pid);
    }

    private Company initiateTakeover(Company comp1, Company comp2) {
        Company predator;
        Company prey;
        if (comp1.size() == comp2.size()) {
            predator = window.showTakeOverDialog(comp1, comp2);
            prey = (predator == comp1 ? comp2 : comp1);
        } else if (comp1.size() > comp2.size()) {
            predator = comp1;
            prey = comp2;
        } else {
            predator = comp2;
            prey = comp1;
        }
        window.write(predator + " took over " + prey);
        giveBonuses(determineMajorAndMinor(prey), prey, false);
        sellAllShares(prey);
        predator.takeOver(prey);
        return predator;
    }

    private ArrayList<ArrayList<Player>> determineMajorAndMinor(Company prey) {
        ArrayList<Player> maj = new ArrayList<>();
        ArrayList<Player> min = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int shares = game.getPlayer(i).getNumberOfShares(prey);
            if (shares > 0) {
                if ((maj.isEmpty()) || (shares > maj.get(0).getNumberOfShares(prey))) {
                    min = (ArrayList<Player>) maj.clone();
                    maj.clear();
                    maj.add(game.getPlayer(i));
                } else if (shares == maj.get(0).getNumberOfShares(prey)) {
                    maj.add(game.getPlayer(i));
                } else {
                    if ((min.isEmpty()) || (shares > min.get(0).getNumberOfShares(prey))) {
                        min.clear();
                        min.add(game.getPlayer(i));
                    } else if (shares == min.get(0).getNumberOfShares(prey)) {
                        min.add(game.getPlayer(i));
                    }
                }
            }
        }
        ArrayList<ArrayList<Player>> list = new ArrayList<>();
        list.add(maj);
        list.add(min);
        return list;
    }

    private void giveBonuses(ArrayList<ArrayList<Player>> list, Company prey, boolean endOfGame) {
        // previously, bonuses would be smaller at the end of the game, but the
        // endOfGame field is currently not in use
        int majPot = 10 * prey.sellPrice();
        int minPot = 5 * prey.sellPrice();
        if (list.get(0).size() > 1) {
            window.write("There was a " + list.get(0).size() + "-way split"
                    + " of the combined bonuses");
            majPot += minPot;
            minPot = 0;
        } else if (list.get(0).size() == 1) {
            if (list.get(1).isEmpty()) {
                window.write(list.get(0).get(0) + " was the sole shareholder");
                majPot += minPot;
                minPot = 0;
            } else {
                window.write(list.get(0).get(0) + " was the majority shareholder");
            }
        }
        for (Player majPlayer : list.get(0)) {
            int split = (int) Math.ceil(majPot / list.get(0).size());
            majPlayer.giveCash(split);
            window.write(majPlayer + " received a $" + split + " bonus");
        }
        if (minPot != 0) {
            if (list.get(1).size() > 1) {
                window.write("There was a " + list.get(1).size() + "-way split"
                        + " of the minority bonus");
            } else {
                window.write(list.get(1).get(0) + " was the minority shareholder");
            }
            for (Player minPlayer : list.get(1)) {
                int split = (int) Math.ceil(minPot / list.get(1).size());
                minPlayer.giveCash(split);
                window.write(minPlayer + " received a $" + split + " bonus");
            }
        }
    }

    /**
     * Checks to see which action needs to be performed after a tile is played.
     * <p /> The action could be: create a new company; join an existing
     * company; initiate a merger; or nothing. <p /> This method may be better
     * located in the Board class.
     *
     * @param tile
     */
    public void boardCheck(Tile tile) {
        tilePlayed = true;
        int neutral = game.getBoard().neutralNeighbours(tile);
        ArrayList<Company> companies = game.getBoard().companyNeighbours(tile);
        Arrays.sort(companies.toArray());
        if (companies.size() >= 1) {
            Company survivor = companies.get(0);
            if (companies.size() >= 2) {
                survivor = initiateTakeover(survivor, companies.get(1));
                if (companies.size() >= 3) {
                    survivor = initiateTakeover(survivor, companies.get(2));
                    if (companies.size() >= 4) {
                        survivor = initiateTakeover(survivor, companies.get(3));
                    }
                }
            }
            survivor.addTile(tile);
            if (neutral > 0) {
                for (Tile t : game.getBoard().getNeighbours(tile)) {
                    if (t.getOwner() != survivor) {
                        boardCheck(t);
                    }
                }
            } else {
                window.write(survivor + " grew to size " + survivor.size());
            }
        } else {
            if (neutral > 0) {
                if (game.getMarket().allCompaniesActive() || companyCreated) {
                    window.write("No new company could be established");
                } else {
                    companyCreated = true;
                    Company company = game.getMarket().activateCompany(window.showCreateCompanyDialog(), tile);
                    activePlayer.buyShare(company, true);
                    window.write(company + " was founded at " + tile + " with size " + company.size());
                    window.write(activePlayer + " received a free " + company + " share");
                }
                updateWindow();
            }
        }
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
    }

    /**
     * Reduces the number of remaining actions by one and then checks for
     * consequences.
     */
    public void actionTaken() {
        actions--;
        updateWindow();
        window.buttonCheck(actions, startActions, activePlayer.getHandSize(),
                game.getBoard().unassignedTilesRemaining(), tilePlayed);
        if (actions <= 0) {
            window.deactivateTileListener();
            window.write(activePlayer + " ran out of actions");
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
        for (int i = 0; i < 4; i++) {
            if (game.getPlayer(i).getNumberOfShares(prey) > 0) {
                window.write(game.getPlayer(i) + " received $" + game.getPlayer(i).sellShares(prey)
                        + " in share sales");
            }
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
        String[] cardinals = new String[]{"", "A", "Two", "Three", "Four", "Five", "Six"};
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
            giveBonuses(determineMajorAndMinor(company), company, true);
            sellAllShares(company);
        }
        game.end();
    }

    public boolean getTilePlayed() {
        return tilePlayed;
    }
}
