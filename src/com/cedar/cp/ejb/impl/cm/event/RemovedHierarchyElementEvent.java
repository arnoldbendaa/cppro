// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.event;

import com.cedar.cp.dto.dimension.event.RemoveHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.PostUpdateHierarchyElementEvent;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;

public class RemovedHierarchyElementEvent extends PostUpdateHierarchyElementEvent {

   private RemoveHierarchyElementEvent mEvent;


   public RemovedHierarchyElementEvent(HierarchyElementDAG element, RemoveHierarchyElementEvent event) {
      super(element);
      this.mEvent = event;
   }

   public RemoveHierarchyElementEvent getEvent() {
      return this.mEvent;
   }
}
