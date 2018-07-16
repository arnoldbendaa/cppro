// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.CalcButtonBorder;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;

class CalcButton extends JButton {

   public CalcButton(String action, ActionListener listener) {
      super(action);
      this.setBorder(new CalcButtonBorder());
      this.setFont((new JTextField()).getFont());
      this.addActionListener(listener);
   }

   public Dimension getPreferredSize() {
      return new Dimension(30, 20);
   }

   public Dimension getMinimumSize() {
      return this.getPreferredSize();
   }

   public Dimension getMaximumSize() {
      return this.getPreferredSize();
   }

   public void paintComponent(Graphics g) {
      this.setBorderPainted(true);
      g.setFont(this.getFont());
      int strLength = this.getFontMetrics(this.getFont()).stringWidth(this.getText());
      int width = this.getSize().width;
      int height = this.getSize().height;
      g.setColor(this.getBackground());
      g.fillRect(0, 0, width, height);
      int xCoord = width / 2 - strLength / 2;
      int yCoord = (int)((double)height * 0.6D);
      g.setColor(this.getForeground());
      g.drawString(this.getText(), xCoord, yCoord);
   }
}
