package com.powerup.gui;

import com.powerup.listeners.BuyListener;
import com.powerup.logic.Company;
import com.powerup.logic.Game;
import com.powerup.logic.Player;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.UIManager;

public final class SharePanel extends AbstractPanel {

    private GridBagConstraints c;
    private BuyListener buyListener;
    private ArrayList<ArrayList<JComponent>> rows;
    private JButton done;

    public SharePanel(Game game, JDialog dialog) {
        Font oldFont = UIManager.getFont("Label.font");
        UIManager.put("Label.font", oldFont.deriveFont(Font.PLAIN));
        this.game = game;
        this.window = dialog;
        this.buyListener = new BuyListener(game, this);
        this.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(8, 10, 8, 10);
        createComponents();
    }

    @Override
    protected void createComponents() {
        rows = new ArrayList<>();
        ArrayList<JComponent> headers = new ArrayList<>();
        headers.add(makeHeader("Company"));
        headers.add(makeHeader("Available"));
        headers.add(makeHeader("You own"));
        headers.add(makeHeader((game.getPlayers().length > 2 ? "Rivals own" : "Rival owns")));
        if (game.getDoe() != null) {
            headers.add(makeHeader("DoE owns"));
        }
        headers.add(makeHeader("Unit cost"));
        headers.add(makeHeader("Buy"));
        rows.add(headers);
        for (int i = 0; i < 6; i++) {
            ArrayList<JComponent> comp = new ArrayList<>();
            comp.add(makeLabel(game.getMarket().getCompany(i).toString()));
            for (int j = 0; j < (headers.size() - 2); j++) {
                comp.add(makeLabel(""));
            }
            comp.add(makeButton("Buy"));
            rows.add(comp);
        }
        c.gridy = 0;
        for (ArrayList<JComponent> row : rows) {
            c.gridx = 0;
            for (JComponent cell : row) {
                this.add(cell, c);
                if (cell.getClass() == JButton.class) {
                    ((JButton) cell).addActionListener(buyListener);
                }
                c.gridx++;
            }
            c.gridy++;
        }
        c.gridx = 0;
        c.gridwidth = headers.size();
        done = new JButton("Done");
        this.add(done, c);
        done.addActionListener(doneListener);
        ((JDialog) window).getRootPane().setDefaultButton(done);
        update();
    }

    @Override
    public void update() {
        Player player = game.getTurn().getActivePlayer();
        int actions = game.getTurn().getActions();
        for (int i = 0; i < 6; i++) {
            Company seller = game.getMarket().getCompany(i);
            ((JLabel) rows.get(i + 1).get(1)).setText(String.valueOf(seller.getShares().size()));
            ((JLabel) rows.get(i + 1).get(2)).setText(String.valueOf(player.getNumberOfShares(seller)));
            ((JLabel) rows.get(i + 1).get(3)).setText(String.valueOf((seller.getActive() ? 25
                    - ((game.getDoe() != null ? game.getDoe().getNumberOfShares(seller) : 0)
                    + seller.getShares().size() + player.getNumberOfShares(seller)) : 0)));
            int next;
            if (game.getDoe() != null) {
                ((JLabel) rows.get(i + 1).get(4)).setText(String.valueOf(game.getDoe().getNumberOfShares(seller)));
                next = 5;
            } else {
                next = 4;
            }
            ((JLabel) rows.get(i + 1).get(next)).setText("$" + String.valueOf(seller.sellPrice()));
            if ((seller.getActive()) && (!seller.getShares().isEmpty())
                    && (actions > 0) && (player.getCash() >= seller.sellPrice())
                    && (game.getTurn().getTilePlayed())) {
                rows.get(i + 1).get(next + 1).setEnabled(true);
            } else {
                rows.get(i + 1).get(next + 1).setEnabled(false);
            }
        }
    }

    public JButton[] getBuyButtons() {
        JButton[] buttons = new JButton[6];
        for (int i = 0; i < 6; i++) {
            buttons[i] = (JButton) rows.get(i + 1).get(rows.get(i + 1).size() - 1);
        }
        return buttons;
    }
}
