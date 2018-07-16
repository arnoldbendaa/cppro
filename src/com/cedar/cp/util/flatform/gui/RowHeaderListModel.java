// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import javax.swing.AbstractListModel;
import javax.swing.table.TableModel;

public class RowHeaderListModel extends AbstractListModel {

   private TableModel mTableModel;


   public RowHeaderListModel(TableModel tableModel) {
      this.mTableModel = tableModel;
   }

   public int getSize() {
      return this.mTableModel.getRowCount();
   }

   public Object getElementAt(int index) {
      return String.valueOf(index + 1);
   }
}
