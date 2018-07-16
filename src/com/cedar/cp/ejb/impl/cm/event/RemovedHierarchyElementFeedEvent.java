// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.event;

import com.cedar.cp.dto.dimension.event.RemoveHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.PostUpdateHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAG;

public class RemovedHierarchyElementFeedEvent extends PostUpdateHierarchyElementFeedEvent {

   private RemoveHierarchyElementFeedEvent mEvent;


   public RemovedHierarchyElementFeedEvent(HierarchyElementFeedDAG element, RemoveHierarchyElementFeedEvent event) {
      super(element);
      this.mEvent = event;
   }

   public RemoveHierarchyElementFeedEvent getEvent() {
      return this.mEvent;
   }
}
