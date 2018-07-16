// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.dto.dimension.event.HierarchyElementEvent;

public class RemoveHierarchyElementFeedEvent extends HierarchyElementEvent {

   private DimensionElement mDimensionElement;


   public RemoveHierarchyElementFeedEvent(Object elementKey, String visId) {
      super(elementKey, visId);
   }

   public RemoveHierarchyElementFeedEvent(Object elementKey, DimensionElement de) {
      super(elementKey, de != null?de.getVisId():null);
      this.mDimensionElement = de;
   }

   public DimensionElement getDimensionElement() {
      return this.mDimensionElement;
   }
}
