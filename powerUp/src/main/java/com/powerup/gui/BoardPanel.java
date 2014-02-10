package com.powerup.gui;

import com.powerup.logic.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class BoardPanel extends JPanel {

    private final Board board;
    private final Color foreground = new Color(140, 180, 140);
    private final Color background = new Color(220, 220, 220);

    public BoardPanel(Board board) {
        this.board = board;
        addComponents();
    }

    private void addComponents() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(Font.decode(Font.MONOSPACED));
        paintBoardTiles(g);
    }

    private void paintBoardTiles(Graphics g) {
        for (ArrayList<Tile> list : board.getTiles()) {
            for (Tile tile : list) {
                if (tile.getLocation() == Tile.Location.BOARD) {
                    paintTile(g, 34 * (tile.getX()) + 13, 34 * (tile.getY()) + 19, tile.toString(), foreground, true);
                } else {
                    paintTile(g, 34 * (tile.getX()) + 13, 34 * (tile.getY()) + 19, tile.toString(), background, false);
                }
            }
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

    public void update() {
        this.repaint();
    }
}
