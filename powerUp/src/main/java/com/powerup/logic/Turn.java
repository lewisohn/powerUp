package com.powerup.logic;

import com.powerup.gui.ShareDialog;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.SwingUtilities;

public class Turn {

    private Game game;
    private Window window;
    private Player player;
    private int actions;
    private int pid;

    public Turn(Game game, int pid) {
        this.game = game;
        this.window = game.getWindow();
        this.pid = pid;
        beginTurn();
    }

    public void launchShareFrame() {
        ShareDialog shareFrame = new ShareDialog(game);
        SwingUtilities.invokeLater(shareFrame);
    }

    private void beginTurn() {
        this.player = game.getPlayer(pid);
        this.actions = 3;
        window.write("---");
        window.writeln(player + "'s turn");
        window.activateTileListener(game);
        buttonCheck();
        updateWindow();
    }

    public void endTurn() {
        window.write("End of " + player + "'s turn");
        pid++;
        pid = (pid % 4);
        game.newTurn(pid);
    }

    public void buyShare(int button) {
        Company clicked = game.getMarket().getCompany((button % 40) / 2);
        if (player.buyShare(game.getMarket().getCompany((button % 40) / 2), false)) {
            window.write(player + " bought one share in " + clicked);
            actions--;
            updateWindow();
        }
    }

    private Company initiateTakeover(Company comp1, Company comp2) {
        Company predator;
        Company prey;
        if (comp1.size() == comp2.size()) {
            if (window.showTakeOverDialog(comp1, comp2).equals(comp1)) {
                predator = comp1;
                prey = comp2;
            } else {
                predator = comp2;
                prey = comp1;
            }
        } else if (comp1.size() > comp2.size()) {
            predator = comp1;
            prey = comp2;
        } else {
            predator = comp2;
            prey = comp1;
        }
        predator.takeOver(prey);
        window.write(predator + " took over " + prey);
        return predator;
    }

    public void boardCheck(Tile tile) {
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
            window.write(survivor + " acquired " + tile + " and grew to size " + survivor.size());
            if (neutral > 0) {
                for (Tile t : game.getBoard().getNeighbours(tile)) {
                    if (t.getOwner() != survivor) {
                        boardCheck(t);
                    }
                }
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
        window.getFrame().validate();
    }

    public void actionTaken() {
        actions--;
        updateWindow();
        buttonCheck();
        if (actions <= 0) {
            window.deactivateTileListener();
            window.writeln(player + " ran out of actions");
        } else {
            window.write("");
        }

    }

    private void buttonCheck() {
        window.enableButton(3);
        if (actions <= 0) {
            window.disableButton(1);
            window.disableButton(2);
        } else {
            window.enableButton(1);
            if ((player.getHandSize() < 5) && (game.getBoard().unassignedTilesRemaining() > 0)) {
                window.enableButton(2);
            } else {
                window.disableButton(2);
            }
        }
    }

    public int getActions() {
        return actions;
    }
}
