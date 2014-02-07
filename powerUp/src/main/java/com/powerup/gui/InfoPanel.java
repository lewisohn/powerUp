package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.ArrayList;

public class InfoPanel extends JPanel {

    private final Board board;
    private final Color foreground = new Color(140, 180, 140);
    private final Color background = new Color(220, 220, 220);

    public InfoPanel(Board board) {
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
        g.setFont(Font.decode(Font.MONOSPACED));
        paintBox(g, 30, 30, 344, 344, "Playing board");
        paintBoardTiles(g);
        paintBox(g, 404, 30, 344, 276, "Current player");
        paintPlayerBox(g);
        paintBox(g, 404, 336, 344, 38, "Available tiles");
        paintAvailableTiles(g);

//        paintBox(g, 404, 400, 344, 38, "Actions");
//        paintActionBox(g);
    }

    private void paintBox(Graphics g, int x, int y, int width, int height, String text) {
        g.setColor(Color.BLACK);
        g.drawString(text, x + 4, y - 4);
        g.setColor(Color.GRAY);
        g.draw3DRect(x, y, width, height, false);
        g.setColor(background);
        g.fillRect(x + 1, y + 1, width - 1, height - 1);
    }

    private void paintBoardTiles(Graphics g) {
        for (ArrayList<Tile> list : board.getTiles()) {
            for (Tile tile : list) {
                paintTile(g, 34 * (tile.getX() + 1), 34 * (tile.getY() + 1), tile.toString(), background, false);
            }
        }
    }

    private void paintAvailableTiles(Graphics g) {
        int playablePosition = 0;
        for (Tile tile : board.getPlayableTiles()) {
            playablePosition++;
            paintTile(g, 34 * playablePosition + 374, 340, tile.toString(), foreground, true);
        }
    }

    private void paintTile(Graphics g, int x, int y, String ref, Color color, boolean raised) {
        g.setColor(Color.DARK_GRAY);
        g.drawRect(x, y, 30, 30);
        g.setColor(color);
        if (raised) {
            g.fill3DRect(x + 1, y + 1, 29, 29, true);
        } else {
            g.fillRect(x + 1, y + 1, 29, 29);
        }
        g.setColor(Color.BLACK);
        g.drawString(ref, x + 8, y + 21);
    }

    private void paintPlayerBox(Graphics g) {

    }
//
//    private void paintActionBox(Graphics g) {
//        String[] labels = new String[4];
//        labels[0] = "New game";
//        labels[1] = "Buy shares";
//        labels[2] = "Draw tiles";
//        labels[3] = "End turn";
//        for (int i = 0; i < 4; i++) {
//            paintButton(g, 408 + (i * 85), 404, labels[i]);
//        }
//    }
//
//    private void paintButton(Graphics g, int x, int y, String label) {
//        g.setColor(Color.DARK_GRAY);
//        g.drawRect(x, y, 81, 30);
//        g.setColor(Color.LIGHT_GRAY);
//        g.fill3DRect(x + 1, y + 1, 80, 29, true);
//        g.setColor(Color.BLACK);
//        g.drawString(label, x + 8, y + 21);
//    }

    public void update() {
        this.repaint();
    }
}
