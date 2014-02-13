package com.powerup.listeners;

import com.powerup.logic.Game;
import com.powerup.logic.Tile;
import com.powerup.logic.Turn;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TileListener implements MouseListener {

    private Game game;
    private Turn turn;

    public TileListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.turn = game.getTurn();
        if (turn.getActions() > 0) {
            int i = intersects(e);
            if (i >= 0) {
                Tile t = turn.getActivePlayer().returnTileFromHand(i);
                if (t != null) {
                    game.getBoard().playTileToBoard(t);
                    game.getWindow().writepn(turn.getActivePlayer() + " played tile " + t);
                    turn.boardCheck(t);
                    turn.actionTaken();
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
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
}
