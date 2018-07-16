// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.event;

import com.cedar.cp.dto.dimension.event.InsertHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.PostUpdateHierarchyElementEvent;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;

public class InsertedHierarchyElementEvent extends PostUpdateHierarchyElementEvent {

   private InsertHierarchyElementEvent mEvent;


   public InsertedHierarchyElementEvent(HierarchyElementDAG element, InsertHierarchyElementEvent event) {
      super(element);
      this.mEvent = event;
   }

   public InsertHierarchyElementEvent getEvent() {
      return this.mEvent;
   }
}
