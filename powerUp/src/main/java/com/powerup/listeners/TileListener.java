package com.powerup.listeners;

import com.powerup.logic.Game;
import com.powerup.logic.Tile;
import com.powerup.logic.Turn;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TileListener implements MouseListener, MouseMotionListener {

    private final Game game;
    private Turn turn;

    public TileListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Tile t = tileCheck(e, true);
        if (t != null) {
            game.getBoard().playTileToBoard(t);
            game.getBoard().setHighlightedTile(null);
            game.getWindow().writepn(turn.getActivePlayer() + " played tile " + t);
            turn.boardCheck(t);
            turn.actionTaken();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    private Tile tileCheck(MouseEvent e, boolean take) {
        Tile t = null;
        this.turn = game.getTurn();
        if (turn.getActions() > 0) {
            int i = intersects(e);
            if (i >= 0) {
                if (take) {
                    t = turn.getActivePlayer().returnTileFromHand(i);
                } else {
                    t = turn.getActivePlayer().getTile(i);
                }
            }
        }
        return t;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        game.getBoard().setHighlightedTile(null);
        game.getWindow().updateBoard();
    }

    private int intersects(MouseEvent e) {
        int x = e.getPoint().x;
        int y = e.getPoint().y;
        if ((y >= 19) && (y <= 49)) {
            for (int i = 0; i < 5; i++) {
                if ((x >= (14 + (i * 34))) && (x <= (44 + (i * 34)))) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Tile t = tileCheck(e, false);
        if (t != null) {
            if (game.getBoard().getHighlightedTile() != t) {
                game.getBoard().setHighlightedTile(t);
            }
        } else {
            game.getBoard().setHighlightedTile(null);
        }
        game.getWindow().updateBoard();
    }
}
