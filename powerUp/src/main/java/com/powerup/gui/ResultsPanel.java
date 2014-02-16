package com.powerup.gui;

import com.powerup.logic.Game;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.UIManager;

public final class ResultsPanel extends AbstractPanel {

    private ArrayList<JLabel[]> labels;
    private int revealStage;

    ResultsPanel(Game game, JDialog dialog) {
        Font oldFont = UIManager.getFont("Label.font");
        UIManager.put("Label.font", oldFont.deriveFont(Font.PLAIN));
        this.game = game;
        this.window = dialog;
        this.revealStage = 0;
        GridLayout gl = new GridLayout(0, 3);
        gl.setVgap(10);
        this.setLayout(gl);
        createComponents();
        final Timer t = new Timer(1500, null);
        ActionListener slowReveal = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                revealStage++;
                if (revealStage <= 4) {
                    update(revealStage);
                } else {
                    t.stop();
                }
            }
        };
        t.addActionListener(slowReveal);
        t.start();
    }

    @Override
    protected void createComponents() {
        labels = new ArrayList<>();
        labels.add(new JLabel[]{
                    makeHeader("Pos."),
                    makeHeader("Player"),
                    makeHeader("Cash")
                });
        for (int i = 0; i < 4; i++) {
            labels.add(new JLabel[]{
                        makeLabel((i + 1) + "."),
                        makeLabel(game.getPlayer(i).toString()),
                        makeLabel("$" + game.getPlayer(i).getCash())
                    });
        }
        for (int i = 0; i < 5; i++) {
            for (JLabel label : labels.get(i)) {
                this.add(label);
                label.setHorizontalAlignment(JLabel.LEFT);
                label.setVisible((i == 0 || String.valueOf(label.getText().charAt(1)).equals(".") ? true : false));
            }
        }
        this.add(new JLabel(""));
        JButton done = makeButton("Done");
        this.add(done);
        done.addActionListener(doneListener);
        ((JDialog) window).getRootPane().setDefaultButton(done);
    }

    @Override
    public void update() {
        for (int i = 0; i < 4; i++) {
            update(i + 1);
        }
    }

    public void update(int revealStage) {
        if ((revealStage > 0) && (revealStage < 5)) {
            for (JComponent comp : labels.get(5 - revealStage)) {
                comp.setVisible(true);
            }
        }
    }
}
