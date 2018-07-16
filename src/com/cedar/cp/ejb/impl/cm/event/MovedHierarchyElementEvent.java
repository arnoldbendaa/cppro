// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.event;

import com.cedar.cp.dto.dimension.event.MoveHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.PostUpdateHierarchyElementEvent;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;

public class MovedHierarchyElementEvent extends PostUpdateHierarchyElementEvent {

   private MoveHierarchyElementEvent mEvent;
   private HierarchyElementDAG mOrigParent;
   private int mOrigIndex;


   public MovedHierarchyElementEvent(HierarchyElementDAG element, MoveHierarchyElementEvent event, HierarchyElementDAG origParent, int origIndex) {
      super(element);
      this.mEvent = event;
      this.mOrigParent = origParent;
      this.mOrigIndex = origIndex;
   }

   public MoveHierarchyElementEvent getEvent() {
      return this.mEvent;
   }

   public HierarchyElementDAG getOrigParent() {
      return this.mOrigParent;
   }

   public int getOrigIndex() {
      return this.mOrigIndex;
   }
}
