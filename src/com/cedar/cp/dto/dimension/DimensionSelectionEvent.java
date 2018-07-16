// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyEvent;

public class DimensionSelectionEvent implements HierarchyEvent {

   private DimensionRef mDimensionRef;


   public DimensionSelectionEvent(DimensionRef dimensionRef) {
      this.mDimensionRef = dimensionRef;
   }

   public DimensionRef getDimensionRef() {
      return this.mDimensionRef;
   }
}
