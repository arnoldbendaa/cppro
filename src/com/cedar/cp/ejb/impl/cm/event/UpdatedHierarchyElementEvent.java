// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.event;

import com.cedar.cp.dto.dimension.event.UpdateHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.PostUpdateHierarchyElementEvent;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;

public class UpdatedHierarchyElementEvent extends PostUpdateHierarchyElementEvent {

   private UpdateHierarchyElementEvent mEvent;


   public UpdatedHierarchyElementEvent(HierarchyElementDAG element, UpdateHierarchyElementEvent event) {
      super(element);
      this.mEvent = event;
   }

   public UpdateHierarchyElementEvent getEvent() {
      return this.mEvent;
   }
}
