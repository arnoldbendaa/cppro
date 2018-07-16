// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.RowHeaderTable.InternalTableModel;
import com.cedar.cp.util.awt.TreeTable.TreeTableModel;
import java.awt.Color;

public interface RowHeaderTableModel {

   TreeTableModel getRowTreeTableModel();

   String getCornerName();

   Class getColumnClass(int var1);

   String getColumnName(int var1);

   int getColumnCount();

   boolean isCellEditable(Object var1, int var2);

   Object getValueAt(Object var1, int var2);

   void setValueAt(Object var1, Object var2, int var3);

   void setInternalTableModel(InternalTableModel var1);

   boolean isDataValidForCell(Object var1, Object var2, int var3);

   int getGradientDepth();

   Color getGradientColor();
}
