// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class DefaultTriptychTableModel extends DefaultTableModel {

   public DefaultTriptychTableModel(Object[][] data, Object[] columnNames) {
      super(data, columnNames);
   }

   public DefaultTriptychTableModel(Vector data, Vector columnNames) {
      super(data, columnNames);
   }

   public boolean isCellEditable(int row, int column) {
      return false;
   }
}
