// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.event;

import com.cedar.cp.dto.dimension.event.RemoveDimensionElementEvent;
import com.cedar.cp.ejb.impl.cm.event.PostUpdateDimensionElementEvent;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAG;

public class RemovedDimensionElementEvent extends PostUpdateDimensionElementEvent {

   private RemoveDimensionElementEvent mEvent;


   public RemovedDimensionElementEvent(DimensionElementDAG element, RemoveDimensionElementEvent event) {
      super(element);
      this.mEvent = event;
   }

   public RemoveDimensionElementEvent getEvent() {
      return this.mEvent;
   }
}
