// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.dimension.StructureElementRef;

public interface StructureElementNode extends StructureElementKey {

   String getVisId();

   String getDescription();

   StructureElementRef getStructureElementRef();

   Object getPrimaryKey();

   boolean isIsCredit();

   boolean isLeaf();

   int getPosition();

   int getDepth();

   int getStructureId();

   int getStructureElementId();

   int getCalendarType();
}
