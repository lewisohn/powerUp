package com.powerup.logic;

import com.powerup.gui.*;

public class Turn {

    private Game game;
    private GameFrame gFrame;
    private Player player;
    private int actions;
    private TileListener tileListener;

    public Turn(Game game, int pid) {
        this.game = game;
        this.gFrame = game.getGameFrame();
        this.player = game.getPlayer(pid);
        this.actions = 3;
        beginTurn();
    }

    private void beginTurn() {
        gFrame.getInfoPanel().writeln("Starting new turn");
        gFrame.getInfoPanel().write("It's " + player + "'s turn");
        gFrame.getInfoPanel().writeln(player + " has $" + player.getCash() + " and " + actions + " actions remaining");
        gFrame.getTilesPanel().setTiles(player.getTiles());
        gFrame.getTilesPanel().update();
        tileListener = new TileListener(this);
        gFrame.getTilesPanel().addMouseListener(tileListener);
    }

    private void endTurn() {
    }

    public void playTile(int n) {
        if (player.getTile(n) != null) {
            Tile t = player.getTile(n);
            game.getBoard().playTileToBoard(player.playTile(n));
            gFrame.getBoardPanel().update();
            gFrame.getTilesPanel().setTiles(player.getTiles());
            gFrame.getTilesPanel().update();
            actions--;
            gFrame.getInfoPanel().write(player + " played tile " + t);
            checkCompanies(t);
            if (actions <= 0) {
                gFrame.getInfoPanel().write(player + " is out of actions");
                gFrame.getInfoPanel().writeln("Click \"End turn\" to proceed with the game");
                gFrame.getTilesPanel().removeMouseListener(tileListener);
            } else {
                gFrame.getInfoPanel().write(player + " has " + actions + (actions == 1 ? " action " : " actions ") + "remaining");
            }
        }
    }

    private void checkCompanies(Tile tile) {
        int comp = 0;
        int neutral = 0;
        // check how many company tiles the new tile connected to
        for (Tile t : game.getBoard().getNeighbours(tile)) {
            if (t.getOwner() != null) {
                comp++;
            } else {
                neutral++;
            }
        }
        if (comp >= 2) {
            // if two or more, time for a merger!    
        } else if (comp == 1) {
            // if one, join it    
        } else {
            // if none, check how many non-company tiles the new tile connected to    
            if ((neutral >= 1) && (!game.allCompaniesActive())) {
                // if one or more, and if there is a company that hasn't been created yet, then create a new one!        
            }
        }
    }
}
