// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.hierarchy.HierarchyModel;
import java.util.Collection;
import javax.swing.tree.TreeModel;

public interface QElementPickerModel extends HierarchyModel {

   int SELECTION_STATE_TICKED = 1;
   int SELECTION_STATE_CROSSED = 2;
   int SELECTION_STATE_NONE = 3;


   boolean isEditable();

   void toggleNodeSelection(Object var1);

   int queryNodeStatus(Object var1);

   TreeModel getTreeModel();

   String getTreeName();

   Collection getSelectedNodes();

   void loadSelectionIntoTreeModel();
}
