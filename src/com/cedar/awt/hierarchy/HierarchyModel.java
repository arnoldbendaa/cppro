// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt.hierarchy;

import com.cedar.awt.hierarchy.HierarchyModelListener;
import javax.swing.tree.TreeModel;

public interface HierarchyModel {

   TreeModel getTreeModel();

   void searchTree(String var1);

   void addHierarchyModelListener(HierarchyModelListener var1);

   void removeHierarchyModelListener(HierarchyModelListener var1);
}
