package com.powerup.gui.help;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class HelpFrame implements Runnable {

   private JFrame frame;
   private JTabbedPane tabbedPane;
   private GridBagConstraints c;

   public HelpFrame() {
      frame = new JFrame("powerUp Help");
      frame.setLayout(new GridBagLayout());
      frame.setPreferredSize(new Dimension(600, 450));
//        frame.setResizable(false);
      frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        ((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(7, 10, 13, 10));
      frame.setLayout(new GridBagLayout());
      c = new GridBagConstraints();
      c.weightx = c.weighty = 1.0;
//        c.insets = new Insets(0, 0, 0, 0);
      c.fill = GridBagConstraints.BOTH;
      createComponents(frame.getContentPane());
      ImageIcon icon = new ImageIcon("icons/window.png");
      frame.setIconImage(icon.getImage());
      frame.pack();
      frame.setLocationRelativeTo(null);
   }

   @Override
   public void run() {
      frame.setVisible(true);
   }

   private void createComponents(Container contentPane) {
      this.tabbedPane = new JTabbedPane();
      tabbedPane.addTab("Overview", new HelpPanel(this.getClass().getClassLoader().getResource("overview.html")));
      tabbedPane.addTab("Playing a turn", new HelpPanel(this.getClass().getClassLoader().getResource("turn.html")));
      tabbedPane.addTab("Mergers", new HelpPanel(this.getClass().getClassLoader().getResource("mergers.html")));
      tabbedPane.addTab("End of the game", new HelpPanel(this.getClass().getClassLoader().getResource("endgame.html")));
      tabbedPane.addTab("Tips", new HelpPanel(this.getClass().getClassLoader().getResource("tips.html")));
      tabbedPane.addTab("About", new HelpPanel(this.getClass().getClassLoader().getResource("about.html")));
      contentPane.add(tabbedPane, c);
   }
}
