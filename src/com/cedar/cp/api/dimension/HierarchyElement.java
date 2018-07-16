// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.dimension.Hierarchy;
import com.cedar.cp.api.dimension.HierarchyNode;

public interface HierarchyElement extends HierarchyNode {

   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   int getCreditDebit();

   int getAugCreditDebit();

   Hierarchy getHierarchy();

   EntityRef getEntityRef();
}
