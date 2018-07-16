// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

class RowHeaderTableCorner extends JPanel {

   private String mLeftName;


   RowHeaderTableCorner(String leftName) {
      super(new FlowLayout(0, 5, 0));
      this.mLeftName = leftName;
      ToolTipManager.sharedInstance().registerComponent(this);
   }

   void setHeaderBackground(Color headerColor) {
      this.setBackground(headerColor);
   }

   void setLeftName(String leftName) {
      this.mLeftName = leftName;
   }

   public String getToolTipText(MouseEvent event) {
      return this.mLeftName;
   }
}
