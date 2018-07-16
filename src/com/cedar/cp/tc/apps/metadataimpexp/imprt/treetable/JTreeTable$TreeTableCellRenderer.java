// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.JTreeTable;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellRenderer;

public class JTreeTable$TreeTableCellRenderer extends JTree implements TableCellRenderer {

   protected int visibleRow;
   // $FF: synthetic field
   final JTreeTable this$0;


   public JTreeTable$TreeTableCellRenderer(JTreeTable var1) {
      this.this$0 = var1;
   }

   public void setBounds(int x, int y, int w, int h) {
      super.setBounds(x, 0, w, this.this$0.getHeight());
   }

   public void paint(Graphics g) {
      g.translate(0, -this.visibleRow * this.getRowHeight());
      super.paint(g);
   }

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if(isSelected) {
         this.setBackground(table.getSelectionBackground());
      } else {
         this.setBackground(table.getBackground());
      }

      this.visibleRow = row;
      return this;
   }
}
