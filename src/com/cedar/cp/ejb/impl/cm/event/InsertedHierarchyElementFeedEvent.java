// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.event;

import com.cedar.cp.dto.dimension.event.InsertHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.PostUpdateHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAG;

public class InsertedHierarchyElementFeedEvent extends PostUpdateHierarchyElementFeedEvent {

   private InsertHierarchyElementFeedEvent mEvent;


   public InsertedHierarchyElementFeedEvent(HierarchyElementFeedDAG element, InsertHierarchyElementFeedEvent event) {
      super(element);
      this.mEvent = event;
   }

   public InsertHierarchyElementFeedEvent getEvent() {
      return this.mEvent;
   }
}
