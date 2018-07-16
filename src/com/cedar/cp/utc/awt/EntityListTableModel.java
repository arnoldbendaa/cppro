// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.awt;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import javax.swing.table.AbstractTableModel;

public class EntityListTableModel extends AbstractTableModel {

   private String[] mHeadings;
   private String[] mColumnNames;
   private EntityList mCollection;


   public EntityListTableModel(EntityList collection, String[] columnNames, String[] headings) {
      this.mColumnNames = columnNames;
      this.mHeadings = headings;
      this.mCollection = collection;
   }

   public int getColumnCount() {
      return this.mHeadings.length;
   }

   public Class getColumnClass(int col) {
      for(int i = 0; i < this.getRowCount(); ++i) {
         Object o = this.getValueAt(0, col);
         if(o != null) {
            if(o instanceof EntityRef) {
               return String.class;
            }

            return o.getClass();
         }
      }

      return String.class;
   }

   public int getRowCount() {
      return this.mCollection != null?this.mCollection.getNumRows():0;
   }

   public Object getValueAt(int row, int col) {
      Object ref = this.mCollection.getValueAt(row, this.mColumnNames[col]);
      return ref;
   }

   public String getColumnName(int col) {
      return this.mHeadings[col];
   }

   public void setCollection(EntityList collection) {
      this.mCollection = collection;
      this.fireTableDataChanged();
   }

   public EntityList getCollection() {
      return this.mCollection;
   }
}
