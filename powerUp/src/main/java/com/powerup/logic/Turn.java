package com.powerup.logic;

import com.powerup.gui.ShareDialog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.SwingUtilities;

public class Turn {

    private Game game;
    private Window window;
    private Player player;
    private int actions;
    private int startActions;
    private int pid;
    private boolean tilePlayed;
    private final Random random;

    public Turn(Game game, int pid) {
        this.game = game;
        this.window = game.getWindow();
        this.pid = pid;
        this.random = new Random();
        beginTurn();
    }

    public void launchShareFrame() {
        ShareDialog shareFrame = new ShareDialog(game);
        SwingUtilities.invokeLater(shareFrame);
    }

    private void beginTurn() {
        this.player = game.getPlayer(pid);
        int sa = random.nextInt(4);
        this.startActions = 2 + (sa == 3 ? 1 : sa);
        this.actions = startActions;
        this.tilePlayed = false;
        window.write("---");
        window.write(player + "'s turn");
        window.write(player + " rolled " + startActions + " actions for this turn");
        window.activateTileListener(game);
        buttonCheck();
        updateWindow();
    }

    public void endTurn() {
        window.writepn("End of " + player + "'s turn");
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
        int majPot = (endOfGame ? 6 : 12) * prey.sellPrice();
        int minPot = (endOfGame ? 3 : 6) * prey.sellPrice();
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
                if (game.getMarket().allCompaniesActive()) {
                    window.write("No new company could be established");
                } else {
                    Company company = game.getMarket().activateCompany(window.showCreateCompanyDialog(), tile);
                    player.buyShare(company, true);
                    window.write(company + " was founded at " + tile + " with size " + company.size());
                    window.write(player + " received a free " + company + " share");
                }
                updateWindow();
            }
        }
    }

    public Player getActivePlayer() {
        return player;
    }

    private void updateWindow() {
        window.setActions(actions);
        window.setCash(player.getCash());
        window.updateTiles(player.getTiles());
        window.updateBoard();
    }

    public void actionTaken() {
        actions--;
        updateWindow();
        buttonCheck();
        if (actions <= 0) {
            window.deactivateTileListener();
            window.write(player + " ran out of actions");
        }
        if (endOfGameCheck()) {
            window.deactivateTileListener();
            window.disableAllButtons();
            dissolveAll();
        }

    }

    private void buttonCheck() {
        if (actions <= 0) {
            window.disableButton(1);
            window.disableButton(2);
            window.enableButton(3);
        } else {
            if ((player.getHandSize() < 5) && (game.getBoard().unassignedTilesRemaining() > 0)) {
                window.enableButton(2);
            } else {
                window.disableButton(2);
            }
            if (actions == startActions) {
                window.disableButton(1);
                window.disableButton(3);
            } else {
                if (tilePlayed) {
                    window.enableButton(1);
                    window.enableButton(3);
                } else {
                    window.disableButton(1);
                    window.disableButton(3);
                }
            }
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
            for (int i = 0; i < 3; i++) {
                int threshold = 50 - (i == 2 ? 25 : i * 15);
                int n = game.getMarket().numberOfCompaniesLargerThan(threshold);
                if (n >= (i + 1)) {
                    window.writepn("The game is over");
                    window.write(cardinal(n) + (n > 1 ? " companies have" : " company has")
                            + " reached size " + threshold);
                    return true;
                }
            }
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
}
