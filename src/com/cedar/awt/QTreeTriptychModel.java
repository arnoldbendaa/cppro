// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.hierarchy.HierarchyModel;
import java.awt.ItemSelectable;
import javax.swing.table.TableModel;

public interface QTreeTriptychModel extends ItemSelectable {

   HierarchyModel getLeftTreeModel();

   TableModel getRightTableModel();

   void addRow(Object var1);

   void removeRow(int var1);

   boolean isAddRowValid(int var1);

   boolean isRemoveRowValid(int var1);
}
