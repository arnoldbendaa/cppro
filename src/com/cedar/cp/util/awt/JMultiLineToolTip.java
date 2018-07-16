// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.MultiLineToolTipUI;
import javax.swing.JComponent;
import javax.swing.JToolTip;

public class JMultiLineToolTip extends JToolTip {

   private static final String uiClassID = "ToolTipUI";
   String tipText;
   JComponent component;
   protected int columns = 0;
   protected int fixedwidth = 0;


   public JMultiLineToolTip() {
      this.updateUI();
   }

   public void updateUI() {
      this.setUI(MultiLineToolTipUI.createUI(this));
   }

   public void setColumns(int columns) {
      this.columns = columns;
      this.fixedwidth = 0;
   }

   public int getColumns() {
      return this.columns;
   }

   public void setFixedWidth(int width) {
      this.fixedwidth = width;
      this.columns = 0;
   }

   public int getFixedWidth() {
      return this.fixedwidth;
   }
}
