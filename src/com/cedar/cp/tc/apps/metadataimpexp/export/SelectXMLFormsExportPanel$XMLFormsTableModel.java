// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.export.SelectXMLFormsExportPanel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

class SelectXMLFormsExportPanel$XMLFormsTableModel extends DefaultTableModel implements TableModelListener {

   private ArrayList mXMLFormList;
   // $FF: synthetic field
   final SelectXMLFormsExportPanel this$0;


   public SelectXMLFormsExportPanel$XMLFormsTableModel(SelectXMLFormsExportPanel var1) {
      this.this$0 = var1;
      this.mXMLFormList = null;
   }

   public Class getColumnClass(int c) {
      return this.getValueAt(0, c).getClass();
   }

   public boolean isCellEditable(int row, int col) {
      return col == 0;
   }

   public void setValueAt(Object value, int row, int column) {
      super.setValueAt(value, row, column);
   }

   public void setModelData(Collection itemList) {
      this.mXMLFormList = (ArrayList)itemList;
      this.removeTableModelListener(this);
      Vector columnIdentifiers = new Vector();
      columnIdentifiers.add("Select");
      columnIdentifiers.add("XMLForms");
      columnIdentifiers.add("Description");
      Vector dataVector = new Vector();
      Iterator iterator = itemList.iterator();

      while(iterator.hasNext()) {
         CommonImpExpItem xmlForm = (CommonImpExpItem)iterator.next();
         Vector rowVector = new Vector();
         rowVector.add(Boolean.valueOf(xmlForm.isSelected()));
         rowVector.add(xmlForm.getItemName());
         rowVector.add(xmlForm.getDescription());
         dataVector.add(rowVector);
      }

      this.setDataVector(dataVector, columnIdentifiers);
      this.addTableModelListener(this);
   }

   public void tableChanged(TableModelEvent e) {
      if(this.mXMLFormList != null) {
         int row = e.getFirstRow();
         int column = e.getColumn();
         Object data = this.getValueAt(row, column);
         if(data instanceof Boolean) {
            ((CommonImpExpItem)this.mXMLFormList.get(row)).setSelected(((Boolean)data).booleanValue());
         }

      }
   }
}
