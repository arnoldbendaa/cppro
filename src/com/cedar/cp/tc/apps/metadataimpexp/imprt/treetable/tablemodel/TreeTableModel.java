// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel;

import javax.swing.tree.TreeModel;

public interface TreeTableModel extends TreeModel {

   int getColumnCount();

   String getColumnName(int var1);

   Class getColumnClass(int var1);

   Object getValueAt(Object var1, int var2);

   boolean isCellEditable(Object var1, int var2);

   void setValueAt(Object var1, Object var2, int var3);
}
