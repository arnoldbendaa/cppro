// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.api.model.ModelRef;
import javax.swing.tree.TreeModel;

public interface Hierarchy {

   Object getPrimaryKey();

   int getDimensionId();

   String getVisId();

   String getDescription();

   DimensionRef getDimensionRef();

   boolean isChangeManagementRequestsPending();

   boolean isSubmitChangeManagementRequest();

   Integer getExternalSystemRef();

   ModelRef getModel();

   HierarchyNode getRoot();

   TreeModel getTreeModel();

   HierarchyNode findElement(Object var1);

   HierarchyNode findElement(String var1);

   boolean isNew();
}
