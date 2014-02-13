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
    private ArrayList<JComponent[]> components;
    private JButton done;

    public SharePanel(Game game, JDialog dialog) {
        Font oldFont = UIManager.getFont("Label.font");
        UIManager.put("Label.font", oldFont.deriveFont(Font.PLAIN));
        this.game = game;
        this.window = dialog;
        this.buyListener = new BuyListener(game);
        this.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(8, 10, 8, 10);
        createComponents();
    }

    @Override
    protected void createComponents() {
        components = new ArrayList<>();
        components.add(new JComponent[]{
                    makeHeader("Company"),
                    makeHeader("Available"),
                    makeHeader("You own"),
                    makeHeader("Unit cost"),
                    makeHeader("Buy")
                });
        for (int i = 0; i < 6; i++) {
            components.add(new JComponent[]{
                        makeLabel(game.getMarket().getCompany(i).toString()),
                        makeLabel(""),
                        makeLabel(""),
                        makeLabel(""),
                        makeButton("Buy")
                    });
        }
        c.gridy = 0;
        for (JComponent[] compList : components) {
            c.gridx = 0;
            for (JComponent comp : compList) {
                this.add(comp, c);
                if (comp.getClass() == JButton.class) {
                    ((JButton) comp).addActionListener(buyListener);
                }
                c.gridx++;
            }
            c.gridy++;
        }
        c.gridx = 0;
        c.gridwidth = 5;
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
            ((JLabel) components.get(i + 1)[1]).setText(String.valueOf(seller.getShares().size()));
            ((JLabel) components.get(i + 1)[2]).setText(String.valueOf(player.getNumberOfShares(seller)));
            ((JLabel) components.get(i + 1)[3]).setText("$" + String.valueOf(seller.sellPrice()));
            if ((seller.getActive()) && (!seller.getShares().isEmpty())
                    && (actions > 0) && (player.getCash() > seller.sellPrice())) {
                components.get(i + 1)[4].setEnabled(true);
            } else {
                components.get(i + 1)[4].setEnabled(false);
            }
        }
    }
}
