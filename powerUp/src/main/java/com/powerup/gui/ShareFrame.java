package com.powerup.gui;

import com.powerup.logic.Game;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ShareFrame implements Runnable {
    
    private JFrame frame;
    private final Game game;
    private SharePanel sharePanel;
    
    public ShareFrame(Game game) {
        this.game = game;
    }
    
    @Override
    public void run() {
        this.frame = new JFrame("Buy shares");
        createComponents();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //        frame.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                System.out.println("Closed!");
//                e.getWindow().dispose();
//            }
//        });
    public void createComponents() {
        sharePanel = new SharePanel(game);
        frame.add(sharePanel);
    }
}
