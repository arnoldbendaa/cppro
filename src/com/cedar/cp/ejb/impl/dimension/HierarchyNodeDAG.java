// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;
import java.io.Serializable;
import java.util.List;

public interface HierarchyNodeDAG extends Serializable {

   int getId();

   String getVisId();

   String getDescription();

   List getChildren();

   List getAugChildren();

   void setParent(HierarchyElementDAG var1);

   void setAugParent(HierarchyElementDAG var1);

   HierarchyNodeDAG getChildAtIndex(int var1);

   HierarchyNodeDAG getAugChildAtIndex(int var1);

   void add(HierarchyNodeDAG var1) throws ValidationException;

   void add(int var1, HierarchyNodeDAG var2) throws ValidationException;

   void addAug(int var1, HierarchyNodeDAG var2) throws ValidationException;

   HierarchyElementDAG getParent();

   HierarchyElementDAG getAugParent();

   HierarchyElementDAG getActiveParent();

   int indexOf(HierarchyNodeDAG var1);

   int augIndexOf(HierarchyNodeDAG var1);

   void remove(HierarchyNodeDAG var1);

   boolean isLeaf();

   boolean isAugmentElement();

   boolean isFeeder();

   int getIndex();

   int getAugIndex();

   boolean isNodeAncestor(HierarchyNodeDAG var1);

   EntityRef getEntityRef();

   HierarchyDAG getHierarchy();

   Object getPrimaryKey();

   void validateAugmentModeMove(HierarchyElementDAG var1, int var2) throws ValidationException;

   int getCalElemType();

   int getNumChildren();

   void displayHierarchy(StringBuilder var1, int var2, boolean var3);

   int countElements();
}
