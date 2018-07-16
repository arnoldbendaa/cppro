// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.HierarchyEvent;
import java.util.List;

public class NewDimensionElementListEvent implements HierarchyEvent {

   private List mDimensionElements;


   public NewDimensionElementListEvent(List<DimensionElement> dimensionElements) {
      this.mDimensionElements = dimensionElements;
   }

   public List<DimensionElement> getDimensionElements() {
      return this.mDimensionElements;
   }
}
