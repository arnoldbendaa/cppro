// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.PreImportListPanel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.SummaryTableModel;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

class PreImportListPanel$SummaryJTable extends JTable {

   // $FF: synthetic field
   final PreImportListPanel this$0;


   public PreImportListPanel$SummaryJTable(PreImportListPanel var1, SummaryTableModel tableModel) {
      super(tableModel);
      this.this$0 = var1;
   }

   public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
      Component comp = super.prepareRenderer(renderer, row, col);
      JComponent jcomp = (JComponent)comp;
      if(comp == jcomp) {
         jcomp.setToolTipText((String)this.getValueAt(row, col));
      }

      return comp;
   }
}
