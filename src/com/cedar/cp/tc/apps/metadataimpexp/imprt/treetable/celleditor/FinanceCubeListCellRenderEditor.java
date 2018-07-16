// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.celleditor;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.celleditor.AbstractCellEditor;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.celleditor.FinanceCubeListPanel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class FinanceCubeListCellRenderEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      if(value != null && value instanceof CommonImpExpItem) {
         CommonImpExpItem commonImpExpObj = (CommonImpExpItem)value;
         if(commonImpExpObj.getTreeNodeType() == 2) {
            return new FinanceCubeListPanel(commonImpExpObj);
         }
      }

      return null;
   }

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if(value != null && value instanceof CommonImpExpItem) {
         CommonImpExpItem commonImpExpObj = (CommonImpExpItem)value;
         if(commonImpExpObj.getTreeNodeType() == 2) {
            FinanceCubeListPanel cubeListPanel = new FinanceCubeListPanel(commonImpExpObj);
            return cubeListPanel;
         }
      }

      return null;
   }
}
