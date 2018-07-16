// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.event;

import com.cedar.cp.dto.dimension.DimensionElementEvent;

public class RemoveDimensionElementEvent extends DimensionElementEvent {

   private String mVisId;


   public RemoveDimensionElementEvent(Object elementKey, String visId) {
      super(elementKey);
      this.mVisId = visId;
   }

   public String getVisId() {
      return this.mVisId;
   }
}
