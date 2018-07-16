// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.util.xmlform.XMLWritable;

public interface MappedHierarchy extends XMLWritable {

   Object getKey();

   MappingKey getFinanceHierarchyKey();

   HierarchyRef getHierarchyRef();

   String getHierarchyVisId1();

   String getHierarchyVisId2();

   String getNewHierarchyVisId();

   String getNewHierarchyDescription();

   boolean isResponsibilityAreaHierarchy();
}
