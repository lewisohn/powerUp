package com.powerup.logic;

import com.powerup.gui.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Turn {

    private Game game;
    private GameFrame gFrame;
    private Player player;
    private int actions;
    private int pid;
    private DrawListener drawListener;
    private TileListener tileListener;
    private TurnListener turnListener;

    public Turn(Game game, int pid) {
        this.game = game;
        this.gFrame = game.getGameFrame();
        this.pid = pid;
        turnListener = new TurnListener(this);
        drawListener = new DrawListener(this);
        tileListener = new TileListener(this);
        gFrame.getCommandsPanel().getEndTurnButton().setEnabled(true);
        gFrame.getCommandsPanel().getEndTurnButton().addActionListener(turnListener);
        gFrame.getCommandsPanel().getDrawTilesButton().setEnabled(false);
        gFrame.getCommandsPanel().getDrawTilesButton().addActionListener(drawListener);
        beginTurn();
    }

    private void updateCashAndActions() {
        gFrame.getPlayerPanel().setCash(this.player.getCash());
        gFrame.getPlayerPanel().repaint();
        gFrame.getActionsPanel().setActions(this.actions);
        gFrame.getActionsPanel().repaint();
//        gFrame.getInfoPanel().write(player + " has $" + this.player.getCash() + " and "
//                + actions + (actions == 1 ? " action " : " actions ") + "remaining");
//        gFrame.getInfoPanel().repaint();
    }

    private void updateTiles() {
//        gFrame.getTilesPanel().setTiles(player.getTiles());
//        gFrame.getTilesPanel().update();
    }

    private void updateBoard() {
//        gFrame.getBoardPanel().update();

    }

    private void beginTurn() {
        this.player = game.getPlayer(pid);
        this.actions = 3;
        gFrame.getInfoPanel().writeln("Starting new turn");
        gFrame.getInfoPanel().write("It's " + player + "'s turn");
        updateCashAndActions();
        updateTiles();
        if (gFrame.getTilesPanel().getMouseListeners().length == 0) {
            gFrame.getTilesPanel().addMouseListener(tileListener);
        }
        if ((player.getHandSize() < 5) && (game.getBoard().unassignedTilesRemaining()) >= 5) {
            gFrame.getCommandsPanel().getDrawTilesButton().setEnabled(true);
        }
    }

    public void endTurn() {
        pid++;
        pid = (pid % 4);
        beginTurn();
    }

    private void outOfActions() {
        gFrame.getInfoPanel().write(player + " is out of actions");
        gFrame.getInfoPanel().writeln("Click \"End turn\" to proceed with the game");
        gFrame.getCommandsPanel().getDrawTilesButton().setEnabled(false);
        gFrame.getTilesPanel().removeMouseListener(tileListener);
    }

    public void playTile(int n) {
        if (player.getTile(n) != null) {
            Tile t = player.getTile(n);
            game.getBoard().playTileToBoard(player.playTile(n));
            gFrame.getInfoPanel().write(player + " played tile " + t);
            updateBoard();
            updateTiles();
            checkCompanies(t);
            actions--;
            if (actions <= 0) {
                outOfActions();
            } else {
                updateCashAndActions();
                if (game.getBoard().unassignedTilesRemaining() >= 5) {
                    gFrame.getCommandsPanel().getDrawTilesButton().setEnabled(true);
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
                setUpNewCompany(tile);
            }
        }
        gFrame.getBoardPanel().update();
    }

    private void setUpNewCompany(Tile tile) {
        if (game.allCompaniesActive()) {
            gFrame.getInfoPanel().write("No new companies can be established right now");
        } else {
            Company company = gFrame.createCompany(tile);
            player.buyShare(company, true);
            gFrame.getInfoPanel().write(company + " has been founded at " + tile + " with size " + company.size());
            gFrame.getInfoPanel().write(player + " has received a free " + company + " share");
        }
    }

    public void drawTiles() {
        for (int i = 0; i < 5; i++) {
            if (player.getTile(i) == null) {
                if (!game.getBoard().giveTileToPlayer(player)) {
                    break;
                }
            }
        }
        updateTiles();
        gFrame.getCommandsPanel().getDrawTilesButton().setEnabled(false);
        gFrame.getInfoPanel().write(player + " drew new tiles");
        actions--;
        if (actions <= 0) {
            outOfActions();
        } else {
        }
    }
}
