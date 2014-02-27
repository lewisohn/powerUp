package com.powerup.gui.help;

import com.powerup.gui.AbstractPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.StyledDocument;

public final class HelpPanel extends AbstractPanel {

   private JEditorPane info;
   private StyledDocument doc;
   private URL url;
   private final GridBagConstraints c;

   public HelpPanel(URL url) {
      this.url = url;
      this.setLayout(new GridBagLayout());
      c = new GridBagConstraints();
      c.weightx = c.weighty = 1.0;
      c.fill = GridBagConstraints.BOTH;
      createComponents();
      update();
   }

   @Override
   protected void createComponents() {
      try {
         info = new JEditorPane(url);
      } catch (IOException ex) {
         info = new JEditorPane();
      }
      info.setEditable(false);
      info.setOpaque(false);
      JScrollPane scroll = new JScrollPane(info);
      scroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
      scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        DefaultCaret caret = (DefaultCaret) info.getCaret();
//        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
      this.add(scroll, c);
   }

   @Override
   public void update() {
   }
}
