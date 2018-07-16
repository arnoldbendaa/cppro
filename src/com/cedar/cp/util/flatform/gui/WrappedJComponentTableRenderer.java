// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class WrappedJComponentTableRenderer implements TableCellRenderer {

   private JComponent mComponent;
   protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
   private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);


   public WrappedJComponentTableRenderer(JComponent component) {
      this.mComponent = component;
      this.mComponent.setBorder(getNoFocusBorder());
   }

   public JComponent getWrappedComponenet() {
      return this.mComponent;
   }

   private static Border getNoFocusBorder() {
      return System.getSecurityManager() != null?SAFE_NO_FOCUS_BORDER:noFocusBorder;
   }

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if(hasFocus) {
         Border border = null;
         if(isSelected) {
            border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
         }

         if(border == null) {
            border = UIManager.getBorder("Table.focusCellHighlightBorder");
         }

         this.mComponent.setBorder(border);
      } else {
         this.mComponent.setBorder(getNoFocusBorder());
      }

      return this.mComponent;
   }

}
