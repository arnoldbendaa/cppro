// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.JTreeTable;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.celleditor.AbstractCellEditor;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class JTreeTable$TreeTableCellEditor extends AbstractCellEditor implements TableCellEditor {

   // $FF: synthetic field
   final JTreeTable this$0;


   public JTreeTable$TreeTableCellEditor(JTreeTable var1) {
      this.this$0 = var1;
   }

   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int r, int c) {
      return this.this$0.tree;
   }
}
