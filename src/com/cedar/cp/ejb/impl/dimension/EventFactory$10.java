// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.dto.dimension.event.RemoveHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.dimension.DimensionEventConvertor;
import com.cedar.cp.ejb.impl.dimension.EventFactory;

class EventFactory$10 implements DimensionEventConvertor {

   // $FF: synthetic field
   final EventFactory this$0;


   EventFactory$10(EventFactory var1) {
      this.this$0 = var1;
   }

   public Object convert(Object e) throws Exception {
      return EventFactory.accessMethod900(this.this$0, (RemoveHierarchyElementFeedEvent)e);
   }
}