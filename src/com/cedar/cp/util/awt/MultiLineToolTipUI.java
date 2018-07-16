// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.JMultiLineToolTip;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolTipUI;

class MultiLineToolTipUI extends BasicToolTipUI {

   static MultiLineToolTipUI sharedInstance = new MultiLineToolTipUI();
   Font smallFont;
   static JToolTip tip;
   protected CellRendererPane rendererPane;
   private static JTextArea textArea;


   public static ComponentUI createUI(JComponent c) {
      return sharedInstance;
   }

   public void installUI(JComponent c) {
      super.installUI(c);
      tip = (JToolTip)c;
      this.rendererPane = new CellRendererPane();
      c.add(this.rendererPane);
   }

   public void uninstallUI(JComponent c) {
      super.uninstallUI(c);
      c.remove(this.rendererPane);
      this.rendererPane = null;
   }

   public void paint(Graphics g, JComponent c) {
      Dimension size = c.getSize();
      textArea.setBackground(UIManager.getColor("ToolTip.background"));
      textArea.setForeground(UIManager.getColor("ToolTip.foreground"));
      this.rendererPane.paintComponent(g, textArea, c, 1, 1, size.width - 1, size.height - 1, true);
   }

   public Dimension getPreferredSize(JComponent c) {
      String tipText = ((JToolTip)c).getTipText();
      if(tipText == null) {
         return new Dimension(0, 0);
      } else {
         textArea = new JTextArea(tipText);
         textArea.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
         textArea.setFont(UIManager.getFont("ToolTip.font"));
         this.rendererPane.removeAll();
         this.rendererPane.add(textArea);
         textArea.setWrapStyleWord(true);
         int width = ((JMultiLineToolTip)c).getFixedWidth();
         int columns = ((JMultiLineToolTip)c).getColumns();
         Dimension dim;
         if(columns > 0) {
            textArea.setColumns(columns);
            textArea.setSize(0, 0);
            textArea.setLineWrap(true);
            textArea.setSize(textArea.getPreferredSize());
         } else if(width > 0) {
            textArea.setLineWrap(true);
            dim = textArea.getPreferredSize();
            dim.width = width;
            ++dim.height;
            textArea.setSize(dim);
         } else {
            textArea.setLineWrap(false);
         }

         dim = textArea.getPreferredSize();
         ++dim.height;
         ++dim.width;
         return dim;
      }
   }

   public Dimension getMinimumSize(JComponent c) {
      return this.getPreferredSize(c);
   }

   public Dimension getMaximumSize(JComponent c) {
      return this.getPreferredSize(c);
   }

}
