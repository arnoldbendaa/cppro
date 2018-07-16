// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.ListEditModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class DefaultListEditModel implements ListEditModel {

   private TableModel mTableModel = new DefaultTableModel(new Object[][]{{"Initialising..."}}, new String[]{""});


   public TableModel getTableModel() {
      return this.mTableModel;
   }

   public boolean isAddAllowed() {
      return true;
   }

   public boolean isEditable(int row) {
      return true;
   }

   public boolean isDeletable(int row) {
      return true;
   }
}
