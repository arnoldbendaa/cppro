// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.TreeTable;

import com.cedar.cp.util.awt.TreeTable.TreeTableModelListener;
import javax.swing.tree.TreeModel;

public interface TreeTableModel extends TreeModel {

   void addTreeTableModelListener(TreeTableModelListener var1);

   void removeTreeTableModelListener(TreeTableModelListener var1);

   int getColumnCount();

   String getColumnName(int var1);

   Class getColumnClass(int var1);

   Object getValueAt(Object var1, int var2);

   boolean isCellEditable(Object var1, int var2);

   void setValueAt(Object var1, Object var2, int var3);

   void fireTableDataChanged();

   int getGradientDepth();
}
