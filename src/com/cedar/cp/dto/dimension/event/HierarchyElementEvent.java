// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.api.dimension.HierarchyEvent;

public class HierarchyElementEvent implements HierarchyEvent {

   private String mVisId;
   private Object mElementKey;


   public HierarchyElementEvent(Object elementKey, String visId) {
      this.mElementKey = elementKey;
      this.mVisId = visId;
   }

   public Object getElementKey() {
      return this.mElementKey;
   }

   public String getVisId() {
      return this.mVisId;
   }
}
