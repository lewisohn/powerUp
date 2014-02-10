package com.powerup.logic;

import com.powerup.gui.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Turn {

    private Game game;
    public GameFrame gFrame;
    private Player player;
    public int actions;
    private int pid;
    private TileListener tileListener;
    private TurnListener turnListener;
    private DrawListener drawListener;

    public Turn(Game game, int pid) {
        this.game = game;
        this.gFrame = game.getGameFrame();
        this.pid = pid;
        turnListener = new TurnListener(this);
        drawListener = new DrawListener(this);
        tileListener = new TileListener(this);
        gFrame.getCommandsPanel().getDefaultButton().setEnabled(true);
        if (gFrame.getCommandsPanel().getDefaultButton().getActionListeners().length == 0) {
            gFrame.getCommandsPanel().getDefaultButton().addActionListener(turnListener);
        }
        beginTurn();
    }

    private void beginTurn() {
        this.player = game.getPlayer(pid);
        this.actions = 3;
//        gFrame.getPlayerPanel().setPlayer(this.player);
        gFrame.getPlayerPanel().setCash(this.player.getCash());
        gFrame.getActionsPanel().setActions(this.actions);
        gFrame.getInfoPanel().writeln("Starting new turn");
        gFrame.getInfoPanel().write("It's " + player + "'s turn");
        gFrame.getInfoPanel().writeln(player + " has $" + player.getCash() + " and " + actions + " actions remaining");
        gFrame.getTilesPanel().setTiles(player.getTiles());
        gFrame.getTilesPanel().update();
        if (gFrame.getTilesPanel().getMouseListeners().length == 0) {
            gFrame.getTilesPanel().addMouseListener(tileListener);
        }
        if ((player.getHandSize() < 5) && (game.getBoard().unassignedTilesRemain())) {
            gFrame.getCommandsPanel().getDrawTilesButton().setEnabled(true);
            if (gFrame.getCommandsPanel().getDrawTilesButton().getActionListeners().length == 0) {
                gFrame.getCommandsPanel().getDrawTilesButton().addActionListener(drawListener);
            }
        }
    }

    public void endTurn() {
        pid++;
        pid = (pid % 4);
        beginTurn();
    }

    public void playTile(int n) {
        if (player.getTile(n) != null) {
            Tile t = player.getTile(n);
            game.getBoard().playTileToBoard(player.playTile(n));
            gFrame.getBoardPanel().update();
            gFrame.getTilesPanel().setTiles(player.getTiles());
            gFrame.getTilesPanel().update();
            gFrame.getInfoPanel().write(player + " played tile " + t);
            checkCompanies(t);
            actions--;
            if (actions <= 0) {
                gFrame.getInfoPanel().write(player + " is out of actions");
                gFrame.getInfoPanel().writeln("Click \"End turn\" to proceed with the game");
                gFrame.getCommandsPanel().getDrawTilesButton().setEnabled(false);
                gFrame.getTilesPanel().removeMouseListener(tileListener);

            } else {
                gFrame.getInfoPanel().write(player + " has " + actions + (actions == 1 ? " action " : " actions ") + "remaining");
                if (game.getBoard().unassignedTilesRemain()) {
                    gFrame.getCommandsPanel().getDrawTilesButton().setEnabled(true);
                    if (gFrame.getCommandsPanel().getDrawTilesButton().getActionListeners().length == 0) {
                        gFrame.getCommandsPanel().getDrawTilesButton().addActionListener(drawListener);
                    }
                }
            }
        }
    }

    private ArrayList<Company> companyNeighbours(Tile tile) {
        ArrayList<Company> companies = new ArrayList<>();
        for (Tile t : game.getBoard().getNeighbours(tile)) {
            if ((t.getOwner() != null) && (!companies.contains(t.getOwner()))) {
                companies.add(t.getOwner());
            }
        }
        return companies;
    }

    private int neutralNeighbours(Tile tile) {
        int neutral = 0;
        for (Tile t : game.getBoard().getNeighbours(tile)) {
            if (t.getOwner() == null) {
                neutral++;
            }
        }
        return neutral;
    }

    private Company initiateTakeover(Company comp1, Company comp2) {
        Company predator;
        Company prey;
        if (comp1.size() == comp2.size()) {
            if (gFrame.takeOver(comp1, comp2).equals(comp1)) {
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
        gFrame.getInfoPanel().write(predator + " has taken over " + prey + " and is now size " + predator.size());
        return predator;
    }

    private void checkCompanies(Tile tile) {
        int neutral = neutralNeighbours(tile);
        ArrayList<Company> companies = companyNeighbours(tile);
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
            gFrame.getInfoPanel().write(survivor + " has grown to size " + survivor.size());
            if (neutral > 0) {
                for (Tile t : game.getBoard().getNeighbours(tile)) {
                    if (t.getOwner() != survivor) {
                        checkCompanies(t);
                    }
                }
            }
        } else {
            if (neutral > 0) {
                if (game.allCompaniesActive()) {
                    gFrame.getInfoPanel().write("No new companies can be established right now");
                } else {
                    Company company = gFrame.createCompany(tile);
                    player.buyShare(company, true);
                    gFrame.getInfoPanel().write(company + " has been founded at " + tile + " with size " + company.size());
                    gFrame.getInfoPanel().write(player + " has received a free " + company + " share");
                }
            }

        }
        gFrame.getBoardPanel().update();
    }

    public void drawTiles() {
        for (int i = 0; i < 5; i++) {
            if (player.getTile(i) == null) {
                player.giveTile(game.getBoard().getRandomUnassignedTile());
            }
        }
        gFrame.getCommandsPanel().getDrawTilesButton().setEnabled(false);
        gFrame.getCommandsPanel().getDrawTilesButton().removeActionListener(drawListener);
        gFrame.getTilesPanel().update();
        gFrame.getInfoPanel().write(player + " drew new tiles");
        actions--;
        if (actions <= 0) {
            gFrame.getInfoPanel().write(player + " is out of actions");
            gFrame.getInfoPanel().writeln("Click \"End turn\" to proceed with the game");
            gFrame.getTilesPanel().removeMouseListener(tileListener);
        } else {
            gFrame.getInfoPanel().write(player + " has " + actions + (actions == 1 ? " action " : " actions ") + "remaining");
        }
    }
}
